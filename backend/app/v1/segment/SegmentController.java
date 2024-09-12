package v1.segment;

import action.EntityAction;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.*;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@With(EntityAction.class)
public class SegmentController extends Controller{

    private ClassLoaderExecutionContext ec;
    private SegmentResourceHandler handler;

    @Inject
    public SegmentController(ClassLoaderExecutionContext ec, SegmentResourceHandler handler) {
        this.ec = ec;
        this.handler = handler;
    }

    public CompletionStage<Result> list(Http.Request request) {
        return handler.find(request).thenApplyAsync(posts -> {
            final List<SegmentResource> postList = posts.collect(Collectors.toList());
            return ok(Json.toJson(postList));
        }, ec.current());
    }

    public CompletionStage<Result> show(Http.Request request, String id) {
        return handler.lookup(request, id).thenApplyAsync(optionalResource -> {
            return optionalResource.map(resource ->
                    ok(Json.toJson(resource))
            ).orElseGet(Results::notFound);
        }, ec.current());
    }

    public CompletionStage<Result> update(Http.Request request, String id) {
        JsonNode json = request.body().asJson();
        SegmentResource resource = Json.fromJson(json, SegmentResource.class);
        return handler.update(request, id, resource).thenApplyAsync(optionalResource -> {
            return optionalResource.map(r ->
                    ok(Json.toJson(r))
            ).orElseGet(Results::notFound
            );
        }, ec.current());
    }

    public CompletionStage<Result> create(Http.Request request) {
        JsonNode json = request.body().asJson();
        final SegmentResource resource = Json.fromJson(json, SegmentResource.class);
        return handler.create(request, resource).thenApplyAsync(savedResource -> {
            return created(Json.toJson(savedResource));
        }, ec.current());
    }

    public CompletionStage<Result> delete(Http.Request request, String id) {
        return handler.delete(request, id).thenApplyAsync(optionalResource -> {
            return optionalResource.map(resource ->
                    ok(Json.toJson(resource)) 
            ).orElseGet(Results::notFound);
        }, ec.current());
    }
}
