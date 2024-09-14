package v1.segment;

import v1.curb.CurbData;
import v1.roadway.RoadWayData;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public interface SegmentRepository {

    CompletionStage<Stream<SegmentData>> listSegments();

    CompletionStage<SegmentData> create(SegmentData segmentData);

    CompletionStage<Optional<SegmentData>> get(Long id);

    CompletionStage<Optional<SegmentData>> update(Long id, SegmentData segmentData);

    CompletionStage<Optional<SegmentData>> delete(Long id);

    CompletionStage<List<RoadWayData>> getRoadways(Long segmentId);

    CompletionStage<List<CurbData>> getCurbs(Long segmentId);
}
