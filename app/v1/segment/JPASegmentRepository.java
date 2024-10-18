package v1.segment;

import jakarta.persistence.Query;
import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import v1.curb.CurbData;
import v1.roadway.RoadWayData;
import v1.roadway.RoadWayExecutionContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Singleton
public class JPASegmentRepository implements SegmentRepository{

    private final JPAApi jpaApi;
    private final SegmentExecutionContext ec;
    private final CircuitBreaker<Optional<SegmentData>> circuitBreaker = new CircuitBreaker<Optional<SegmentData>>().withFailureThreshold(1).withSuccessThreshold(3);

    @Inject
    public JPASegmentRepository(JPAApi api, SegmentExecutionContext ec, RoadWayExecutionContext ec2) {
        this.jpaApi = api;
        this.ec = ec;
    }

    @Override
    public CompletionStage<Stream<SegmentData>> listSegments() {
        return supplyAsync(() -> wrap(em -> select(em)), ec);
    }

    @Override
    public CompletionStage<SegmentData> create(SegmentData segmentData) {
        return supplyAsync(() -> wrap(em -> insert(em, segmentData)), ec);
    }

    @Override
    public CompletionStage<Optional<SegmentData>> get(Long id) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> lookup(em, id))), ec);
    }

    @Override
    public CompletionStage<Optional<SegmentData>> update(Long id, SegmentData segmentData) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> modify(em, id, segmentData))), ec);
    }

    @Override
    public CompletionStage<Optional<SegmentData>> delete(Long id) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> remove(em, id))), ec);
    }

    @Override
    public CompletionStage<List<RoadWayData>> getRoadways(Long segmentId) {
        return supplyAsync(() -> wrap(em -> {
            SegmentData segment = em.find(SegmentData.class, segmentId);
            if (segment != null) {
                segment.getRoadways().size();
                return segment.getRoadways();
            }
            return List.of();
        }), ec);
    }

    @Override
    public CompletionStage<List<CurbData>> getCurbs(Long segmentId) {
        return supplyAsync(() -> wrap(em -> {
            SegmentData segment = em.find(SegmentData.class, segmentId);
            if (segment != null) {
                segment.getCurbs().size();
                return segment.getCurbs();
            }
            return List.of();
        }), ec);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Optional<SegmentData> lookup(EntityManager em, Long id) throws SQLException {
        return Optional.ofNullable(em.find(SegmentData.class, id));
    }

    private Stream<SegmentData> select(EntityManager em) {
        TypedQuery<SegmentData> query = em.createQuery("SELECT p FROM SegmentData p", SegmentData.class);
        return query.getResultList().stream();
    }

    private Optional<SegmentData> modify(EntityManager em, Long id, SegmentData segmentData) throws InterruptedException {
        final SegmentData data = em.find(SegmentData.class, id);
        if (data != null) {
            data.setSegmentNumber(segmentData.getSegmentNumber());
            data.setLength(segmentData.getLength());
            data.setNomenclature(segmentData.getNomenclature());
        }
        return Optional.ofNullable(data);
    }

    private SegmentData insert(EntityManager em, SegmentData segmentData) {
        return em.merge(segmentData);
    }

    private Optional<SegmentData> remove(EntityManager em, Long id) {
        SegmentData data = em.find(SegmentData.class, id);
        if (data != null) {
            em.remove(data);
            return Optional.ofNullable(data);
        }
        return Optional.empty();
    }
}
