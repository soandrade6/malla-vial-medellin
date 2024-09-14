package v1.roadway;

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
public class RoadWayController extends Controller{

    private ClassLoaderExecutionContext ec;
    private RoadWayResourceHandler handler;

    @Inject
    public RoadWayController(ClassLoaderExecutionContext ec, RoadWayResourceHandler handler) {
        this.ec = ec;
        this.handler = handler;
    }

    public CompletionStage<Result> list() {
        return handler.find().thenApplyAsync(posts -> {
            final List<RoadWayResource> postList = posts.collect(Collectors.toList());
            return ok(Json.toJson(postList));
        }, ec.current());
    }

    public CompletionStage<Result> show(String id) {
        return handler.lookup(id).thenApplyAsync(optionalResource -> {
            return optionalResource.map(resource ->
                    ok(Json.toJson(resource))
            ).orElseGet(Results::notFound);
        }, ec.current());
    }

    public CompletionStage<Result> update(Http.Request request, String id) {
        JsonNode json = request.body().asJson();
        RoadWayResource resource = Json.fromJson(json, RoadWayResource.class);
        return handler.update(id, resource).thenApplyAsync(optionalResource -> {
            return optionalResource.map(r ->
                    ok(Json.toJson(r))
            ).orElseGet(Results::notFound
            );
        }, ec.current());
    }

    public CompletionStage<Result> create(Http.Request request) {
        JsonNode json = request.body().asJson();
        final RoadWayResource resource = Json.fromJson(json, RoadWayResource.class);
        return handler.create(resource).thenApplyAsync(savedResource -> {
            return created(Json.toJson(savedResource));
        }, ec.current());
    }

    public CompletionStage<Result> delete(String id) {
        return handler.delete(id).thenApplyAsync(optionalResource -> {
            return optionalResource.map(resource ->
                    ok(Json.toJson(resource))
            ).orElseGet(Results::notFound);
        }, ec.current());
    }
}
