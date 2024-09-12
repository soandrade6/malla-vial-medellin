package v1.roadway;

import com.palominolabs.http.url.UrlBuilder;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.Http;

import javax.inject.Inject;
import java.nio.charset.CharacterCodingException;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public class RoadWayResourceHandler {

    private final RoadWayRepository roadWayRepository;
    private final ClassLoaderExecutionContext ec;

    @Inject
    public RoadWayResourceHandler(RoadWayRepository roadWayRepository, ClassLoaderExecutionContext ec) {
        this.roadWayRepository = roadWayRepository;
        this.ec = ec;
    }

    public CompletionStage<Stream<RoadWayResource>> find(Http.Request request) {
        return roadWayRepository.list().thenApplyAsync(roadWayDataStream -> {
            return roadWayDataStream.map(data -> new RoadWayResource(data, link(request, data)));
        }, ec.current());
    }

    public CompletionStage<RoadWayResource> create(Http.Request request, RoadWayResource resource) {
        final RoadWayData data = new RoadWayData(resource.getWidth());
        return roadWayRepository.create(data).thenApplyAsync(savedData -> {
            return new RoadWayResource(savedData, link(request, savedData));
        }, ec.current());
    }

    public CompletionStage<Optional<RoadWayResource>> lookup(Http.Request request, String id) {
        return roadWayRepository.get(Long.parseLong(id)).thenApplyAsync(optionalData -> {
            return optionalData.map(data -> new RoadWayResource(data, link(request, data)));
        }, ec.current());
    }

    public CompletionStage<Optional<RoadWayResource>> update(Http.Request request,String id, RoadWayResource resource) {
        final RoadWayData data = new RoadWayData(resource.getWidth());
        return roadWayRepository.update(Long.parseLong(id), data).thenApplyAsync(optionalData -> {
            return optionalData.map(op -> new RoadWayResource(op, link(request, op)));
        }, ec.current());
    }

    public CompletionStage<Optional<RoadWayResource>> delete(Http.Request request, String id) {
        return roadWayRepository.delete(Long.parseLong(id)).thenApplyAsync(optionalData -> {
            return optionalData.map(data -> new RoadWayResource(data, link(request, data)));
        }, ec.current());
    }

    private String link(Http.Request request, RoadWayData data) {
        final String[] hostPort = request.host().split(":");
        String host = hostPort[0];
        int port = (hostPort.length == 2) ? Integer.parseInt(hostPort[1]) : -1;
        final String scheme = request.secure() ? "https" : "http";
        try {
            return UrlBuilder.forHost(scheme, host, port)
                    .pathSegments("v1", "roadway", data.id.toString())
                    .toUrlString();
        } catch (CharacterCodingException e) {
            throw new IllegalStateException(e);
        }
    }
}
