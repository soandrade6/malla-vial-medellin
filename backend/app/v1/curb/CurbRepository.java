package v1.curb;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public interface CurbRepository {

    CompletionStage<Stream<CurbData>> list();

    CompletionStage<CurbData> create(CurbData curbData);

    CompletionStage<Optional<CurbData>> get(Long id);

    CompletionStage<Optional<CurbData>> update(Long id, CurbData curbData);

    CompletionStage<Optional<CurbData>> delete(Long id);
}
