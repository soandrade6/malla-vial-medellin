package v1.segment;

import play.libs.concurrent.ClassLoaderExecutionContext;
import v1.roadway.RoadWayResource;

import javax.inject.Inject;
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

    public CompletionStage<Stream<SegmentResource>> find() {
        return segmentRepository.listSegments().thenApplyAsync(segmentDataStream -> {
            return segmentDataStream.map(SegmentResource::new);
        }, ec.current());
    }

    public CompletionStage<SegmentResource> create(SegmentResource resource) {
        final SegmentData data = new SegmentData(resource.getSegmentNumber(), resource.getLength(), resource.getNomenclature());
        return segmentRepository.create(data).thenApplyAsync(SegmentResource::new, ec.current());
    }

    public CompletionStage<Optional<SegmentResource>> lookup(String id) {
        return segmentRepository.get(Long.parseLong(id)).thenApplyAsync(optionalData -> {
            return optionalData.map(SegmentResource::new);
        }, ec.current());
    }

    public CompletionStage<Optional<SegmentResource>> update(String id, SegmentResource resource) {
        final SegmentData data = new SegmentData(resource.getSegmentNumber(), resource.getLength(), resource.getNomenclature());
        return segmentRepository.update(Long.parseLong(id), data).thenApplyAsync(optionalData -> {
            return optionalData.map(SegmentResource::new);
        }, ec.current());
    }

    public CompletionStage<Optional<SegmentResource>> delete(String id) {
        return segmentRepository.delete(Long.parseLong(id)).thenApplyAsync(optionalData -> {
            return optionalData.map(SegmentResource::new);
        }, ec.current());
    }

    public CompletionStage<List<RoadWayResource>> getRoadways(String segmentId) {
        return segmentRepository.getRoadways(Long.parseLong(segmentId)).thenApplyAsync(roadways -> {
            return roadways.stream()
                    .map(RoadWayResource::new)
                    .collect(Collectors.toList());
        }, ec.current());
    }
}
