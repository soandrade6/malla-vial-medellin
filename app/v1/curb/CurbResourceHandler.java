package v1.curb;

import com.palominolabs.http.url.UrlBuilder;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.Http;
import v1.segment.SegmentData;
import v1.segment.SegmentRepository;

import javax.inject.Inject;
import java.nio.charset.CharacterCodingException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public class CurbResourceHandler {

    private final CurbRepository curbRepository;
    private final SegmentRepository segmentRepository;
    private final ClassLoaderExecutionContext ec;

    @Inject
    public CurbResourceHandler(CurbRepository curbRepository, SegmentRepository segmentRepository, ClassLoaderExecutionContext ec) {
        this.curbRepository = curbRepository;
        this.segmentRepository = segmentRepository;
        this.ec = ec;
    }

    public CompletionStage<Stream<CurbResource>> find(Http.Request request) {
        return curbRepository.list().thenApplyAsync(curbDataStream -> {
            return curbDataStream.map(data -> new CurbResource(data, link(request, data)));
        }, ec.current());
    }

    public CompletionStage<CurbResource> create(Http.Request request, CurbResource resource) {
        return segmentRepository.get(resource.getSegment_id())
                .thenComposeAsync(optionalSegment -> {
                    if (optionalSegment.isPresent()) {
                        SegmentData segment = optionalSegment.get();
                        CurbData data = new CurbData(resource.getHeight(), segment);
                        return curbRepository.create(data)
                                .thenApplyAsync(savedData -> {
                                    return new CurbResource(savedData, link(request, savedData));
                                }, ec.current());
                    } else {
                        throw new RuntimeException("Segment not found for ID: " + resource.getSegment_id());
                    }
                }, ec.current());
    }

    public CompletionStage<Optional<CurbResource>> lookup(Http.Request request, String id) {
        return curbRepository.get(Long.parseLong(id)).thenApplyAsync(optionalData -> {
            return optionalData.map(data -> new CurbResource(data, link(request, data)));
        }, ec.current());
    }

    public CompletionStage<Optional<CurbResource>> update(Http.Request request, String id, CurbResource resource) {
        return curbRepository.get(Long.parseLong(id)).thenComposeAsync(optionalData -> {
            if (optionalData.isPresent()) {
                CurbData existingData = optionalData.get();
                existingData.setHeight(resource.getHeight());

                return segmentRepository.get(resource.getSegment_id()).thenComposeAsync(optionalSegment -> {
                    if (optionalSegment.isPresent()) {
                        SegmentData segment = optionalSegment.get();
                        existingData.setSegment(segment);
                    }

                    return curbRepository.update(Long.parseLong(id), existingData)
                            .thenApplyAsync(optionalCurbData -> {
                                return optionalCurbData.map(op -> new CurbResource(op, link(request, op)));
                            }, ec.current());
                }, ec.current());
            } else {
                return CompletableFuture.completedFuture(Optional.empty());
            }
        }, ec.current());
    }


    public CompletionStage<Optional<CurbResource>> delete(Http.Request request, String id) {
        return curbRepository.delete(Long.parseLong(id)).thenApplyAsync(optionalData -> {
            return optionalData.map(data -> new CurbResource(data, link(request, data)));
        }, ec.current());
    }

    private String link(Http.Request request, CurbData data) {
        final String[] hostPort = request.host().split(":");
        String host = hostPort[0];
        int port = (hostPort.length == 2) ? Integer.parseInt(hostPort[1]) : -1;
        final String scheme = request.secure() ? "https" : "http";
        try {
            return UrlBuilder.forHost(scheme, host, port)
                    .pathSegments("v1", "curb", data.getId().toString())
                    .toUrlString();
        } catch (CharacterCodingException e) {
            throw new IllegalStateException(e);
        }
    }
}
