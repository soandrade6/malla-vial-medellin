package v1.segment;

import com.palominolabs.http.url.UrlBuilder;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.Http;
import v1.roadway.RoadWayResource;

import javax.inject.Inject;
import java.nio.charset.CharacterCodingException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SegmentResourceHandler {

    private final SegmentRepository segmentRepository;
    private final ClassLoaderExecutionContext ec;

    @Inject
    public SegmentResourceHandler(SegmentRepository segmentRepository, ClassLoaderExecutionContext ec) {
        this.segmentRepository = segmentRepository;
        this.ec = ec;
    }

    public CompletionStage<Stream<SegmentResource>> find(Http.Request request) {
        return segmentRepository.list().thenApplyAsync(segmentDataStream -> {
            return segmentDataStream.map(data -> new SegmentResource(data, link(request, data)));
        }, ec.current());
    }

    public CompletionStage<SegmentResource> create(Http.Request request, SegmentResource resource) {
        final SegmentData data = new SegmentData(resource.getSegmentNumber(), resource.getLength(), resource.getNomenclature());
        return segmentRepository.create(data).thenApplyAsync(savedData -> {
            return new SegmentResource(savedData, link(request, savedData));
        }, ec.current());
    }

    public CompletionStage<Optional<SegmentResource>> lookup(Http.Request request,String id) {
        return segmentRepository.get(Long.parseLong(id)).thenApplyAsync(optionalData -> {
            return optionalData.map(data -> new SegmentResource(data, link(request, data)));
        }, ec.current());
    }

    public CompletionStage<Optional<SegmentResource>> update(Http.Request request,String id, SegmentResource resource) {
        final SegmentData data = new SegmentData(resource.getSegmentNumber(), resource.getLength(), resource.getNomenclature());
        return segmentRepository.update(Long.parseLong(id), data).thenApplyAsync(optionalData -> {
            return optionalData.map(op -> new SegmentResource(op, link(request, op)));
        }, ec.current());
    }

    public CompletionStage<Optional<SegmentResource>> delete(Http.Request request, String id) {
        return segmentRepository.delete(Long.parseLong(id)).thenApplyAsync(optionalData -> {
            return optionalData.map(data -> new SegmentResource(data, link(request, data)));
        }, ec.current());
    }

    public CompletionStage<List<RoadWayResource>> getRoadways(Http.Request request, String segmentId) {
        return segmentRepository.getRoadways(Long.parseLong(segmentId)).thenApplyAsync(roadways -> {
            return roadways.stream()
                    .map(roadway -> new RoadWayResource(roadway)) // Asegúrate de tener un constructor adecuado en RoadWayResource
                    .collect(Collectors.toList());
        }, ec.current());
    }

    private String link(Http.Request request, SegmentData data) {
        final String[] hostPort = request.host().split(":");
        String host = hostPort[0];
        int port = (hostPort.length == 2) ? Integer.parseInt(hostPort[1]) : -1;
        final String scheme = request.secure() ? "https" : "http";
        try {
            return UrlBuilder.forHost(scheme, host, port)
                    .pathSegments("v1", "segment", data.id.toString())
                    .toUrlString();
        } catch (CharacterCodingException e) {
            throw new IllegalStateException(e);
        }
    }

}
