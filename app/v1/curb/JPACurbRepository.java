package v1.curb;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Singleton
public class JPACurbRepository implements CurbRepository{

    private final JPAApi jpaApi;
    private final CurbExecutionContext ec;
    private final CircuitBreaker<Optional<CurbData>> circuitBreaker = new CircuitBreaker<Optional<CurbData>>().withFailureThreshold(1).withSuccessThreshold(3);

    @Inject
    public JPACurbRepository(JPAApi api, CurbExecutionContext ec) {
        this.jpaApi = api;
        this.ec = ec;
    }

    @Override
    public CompletionStage<Stream<CurbData>> list() {
        return supplyAsync(() -> wrap(em -> select(em)), ec);
    }

    @Override
    public CompletionStage<CurbData> create(CurbData curbData) {
        return supplyAsync(() -> wrap(em -> insert(em, curbData)), ec);
    }

    @Override
    public CompletionStage<Optional<CurbData>> get(Long id) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> lookup(em, id))), ec);
    }

    @Override
    public CompletionStage<Optional<CurbData>> update(Long id, CurbData curbData) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> modify(em, id, curbData))), ec);
    }

    @Override
    public CompletionStage<Optional<CurbData>> delete(Long id) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> remove(em, id))), ec);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Optional<CurbData> lookup(EntityManager em, Long id) throws SQLException {
        return Optional.ofNullable(em.find(CurbData.class, id));
    }


    private Stream<CurbData> select(EntityManager em) {
        TypedQuery<CurbData> query = em.createQuery("SELECT p FROM CurbData p", CurbData.class);
        return query.getResultList().stream();
    }

    private Optional<CurbData> modify(EntityManager em, Long id, CurbData curbData) throws InterruptedException {
        final CurbData data = em.find(CurbData.class, id);
        if (data != null) {
            data.setHeight(curbData.getHeight());
        }
        return Optional.ofNullable(data);
    }

    private CurbData insert(EntityManager em, CurbData curbData) {
        return em.merge(curbData);
    }

    private Optional<CurbData> remove(EntityManager em, Long id) {
        CurbData data = em.find(CurbData.class, id);
        if (data != null) {
            em.remove(data);
            return Optional.ofNullable(data);
        }
        return Optional.empty();
    }
}
