package v1.roadway;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public interface RoadWayRepository {

    CompletionStage<Stream<RoadWayData>> list();

    CompletionStage<RoadWayData> create(RoadWayData roadWayData);

    CompletionStage<Optional<RoadWayData>> get(Long id);

    CompletionStage<Optional<RoadWayData>> update(Long id, RoadWayData roadWayData);

    CompletionStage<Optional<RoadWayData> > delete(Long id);
}
