package v1.roadway;

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

public class RoadWayResourceHandler {

    private final RoadWayRepository roadWayRepository;
    private final SegmentRepository segmentRepository;
    private final ClassLoaderExecutionContext ec;

    @Inject
    public RoadWayResourceHandler(RoadWayRepository roadWayRepository, SegmentRepository segmentRepository, ClassLoaderExecutionContext ec) {
        this.roadWayRepository = roadWayRepository;
        this.segmentRepository = segmentRepository;
        this.ec = ec;
    }

    public CompletionStage<Stream<RoadWayResource>> find(Http.Request request) {
        return roadWayRepository.list().thenApplyAsync(roadWayDataStream -> {
            return roadWayDataStream.map(data -> new RoadWayResource(data, link(request, data)));
        }, ec.current());
    }

    public CompletionStage<RoadWayResource> create(Http.Request request, RoadWayResource resource) {
        // Obtener el SegmentData por el ID del recurso
        return segmentRepository.get(resource.getSegment_id())
                .thenComposeAsync(optionalSegment -> {
                    // Si el segmento existe
                    if (optionalSegment.isPresent()) {
                        SegmentData segment = optionalSegment.get();
                        // Crear el RoadWayData y asignar el SegmentData en el constructor
                        RoadWayData data = new RoadWayData(resource.getWidth(), segment);

                        // Guardar el RoadWayData en el repositorio
                        return roadWayRepository.create(data)
                                .thenApplyAsync(savedData -> {
                                    // Crear y devolver el RoadWayResource con el savedData
                                    return new RoadWayResource(savedData, link(request, savedData));
                                }, ec.current());
                    } else {
                        // Si no se encuentra el SegmentData, lanzar una excepción o manejar el error
                        throw new RuntimeException("Segment not found for ID: " + resource.getSegment_id());
                    }
                }, ec.current());
    }

    public CompletionStage<Optional<RoadWayResource>> lookup(Http.Request request, String id) {
        return roadWayRepository.get(Long.parseLong(id)).thenApplyAsync(optionalData -> {
            return optionalData.map(data -> new RoadWayResource(data, link(request, data)));
        }, ec.current());
    }

    public CompletionStage<Optional<RoadWayResource>> update(Http.Request request,String id, RoadWayResource resource) {
        // Buscar el RoadWayData existente por su ID
        return roadWayRepository.get(Long.parseLong(id)).thenComposeAsync(optionalData -> {
            if (optionalData.isPresent()) {
                RoadWayData existingData = optionalData.get();

                // Actualizar solo los campos necesarios
                existingData.setWidth(resource.getWidth());

                // Si también se necesita actualizar el SegmentData, obtén el SegmentData y actualízalo
                return segmentRepository.get(resource.getSegment_id()).thenComposeAsync(optionalSegment -> {
                    if (optionalSegment.isPresent()) {
                        SegmentData segment = optionalSegment.get();
                        existingData.setSegment(segment); // Asignar el nuevo SegmentData si es necesario
                    }

                    // Guardar los cambios en el RoadWayData actualizado
                    return roadWayRepository.update(Long.parseLong(id), existingData)
                            .thenApplyAsync(updatedOptionalData -> updatedOptionalData
                                    .map(updatedData -> new RoadWayResource(updatedData, link(request, updatedData))), ec.current());
                }, ec.current());
            } else {
                // Si no se encuentra el RoadWayData, devolver un Optional vacío
                return CompletableFuture.completedFuture(Optional.empty());
            }
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
