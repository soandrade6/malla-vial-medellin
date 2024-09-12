package v1.roadway;

import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import v1.segment.SegmentExecutionContext;

import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Singleton
public class JPARoadWayRepository implements RoadWayRepository{

    private final JPAApi jpaApi;
    private final SegmentExecutionContext ec;
    private final CircuitBreaker<Optional<RoadWayData>> circuitBreaker = new CircuitBreaker<Optional<RoadWayData>>().withFailureThreshold(1).withSuccessThreshold(3);

    @Inject
    public JPARoadWayRepository(JPAApi api, SegmentExecutionContext ec) {
        this.jpaApi = api;
        this.ec = ec;
    }

    @Override
    public CompletionStage<Stream<RoadWayData>> list() {
        return supplyAsync(() -> wrap(em -> select(em)), ec);
    }

    @Override
    public CompletionStage<RoadWayData> create(RoadWayData roadWayData) {
        return supplyAsync(() -> wrap(em -> insert(em, roadWayData)), ec);
    }

    @Override
    public CompletionStage<Optional<RoadWayData>> get(Long id) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> lookup(em, id))), ec);
    }

    @Override
    public CompletionStage<Optional<RoadWayData>> update(Long id, RoadWayData roadWayData) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> modify(em, id, roadWayData))), ec);
    }

    @Override
    public CompletionStage<Optional<RoadWayData>> delete(Long id) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> remove(em, id))), ec);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Optional<RoadWayData> lookup(EntityManager em, Long id) throws SQLException {
        return Optional.ofNullable(em.find(RoadWayData.class, id));
    }


    private Stream<RoadWayData> select(EntityManager em) {
        TypedQuery<RoadWayData> query = em.createQuery("SELECT p FROM RoadWayData p", RoadWayData.class);
        return query.getResultList().stream();
    }

    private Optional<RoadWayData> modify(EntityManager em, Long id, RoadWayData roadWayData) throws InterruptedException {
        final RoadWayData data = em.find(RoadWayData.class, id);
        if (data != null) {
            data.width = roadWayData.width;
        }
        return Optional.ofNullable(data);
    }

    private RoadWayData insert(EntityManager em, RoadWayData roadWayData) {
        return em.merge(roadWayData);
    }

    private Optional<RoadWayData> remove(EntityManager em, Long id) {
        RoadWayData data = em.find(RoadWayData.class, id);
        if (data != null) {
            em.remove(data);
            return Optional.ofNullable(data);
        }
        return Optional.empty();
    }
}
