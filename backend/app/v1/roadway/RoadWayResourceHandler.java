package v1.roadway;

import play.libs.concurrent.ClassLoaderExecutionContext;
import v1.segment.SegmentData;
import v1.segment.SegmentRepository;

import javax.inject.Inject;
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

    public CompletionStage<Stream<RoadWayResource>> find() {
        return roadWayRepository.list().thenApplyAsync(roadWayDataStream -> {
            return roadWayDataStream.map(RoadWayResource::new);
        }, ec.current());
    }

    public CompletionStage<RoadWayResource> create(RoadWayResource resource) {
        return segmentRepository.get(resource.getSegment_id())
                .thenComposeAsync(optionalSegment -> {
                    if (optionalSegment.isPresent()) {
                        SegmentData segment = optionalSegment.get();
                        RoadWayData data = new RoadWayData(resource.getWidth(), segment);
                        return roadWayRepository.create(data)
                                .thenApplyAsync(RoadWayResource::new, ec.current());
                    } else {
                        throw new RuntimeException("Segment not found for ID: " + resource.getSegment_id());
                    }
                }, ec.current());
    }

    public CompletionStage<Optional<RoadWayResource>> lookup(String id) {
        return roadWayRepository.get(Long.parseLong(id)).thenApplyAsync(optionalData -> {
            return optionalData.map(RoadWayResource::new);
        }, ec.current());
    }

    public CompletionStage<Optional<RoadWayResource>> update(String id, RoadWayResource resource) {
        return roadWayRepository.get(Long.parseLong(id)).thenComposeAsync(optionalData -> {
            if (optionalData.isPresent()) {
                RoadWayData existingData = optionalData.get();
                existingData.setWidth(resource.getWidth());
                return segmentRepository.get(resource.getSegment_id()).thenComposeAsync(optionalSegment -> {
                    if (optionalSegment.isPresent()) {
                        SegmentData segment = optionalSegment.get();
                        existingData.setSegment(segment);
                    }
                    return roadWayRepository.update(Long.parseLong(id), existingData)
                            .thenApplyAsync(updatedOptionalData -> updatedOptionalData
                                    .map(RoadWayResource::new), ec.current());
                }, ec.current());
            } else {
                return CompletableFuture.completedFuture(Optional.empty());
            }
        }, ec.current());
    }

    public CompletionStage<Optional<RoadWayResource>> delete(String id) {
        return roadWayRepository.delete(Long.parseLong(id)).thenApplyAsync(optionalData -> {
            return optionalData.map(RoadWayResource::new);
        }, ec.current());
    }
}
