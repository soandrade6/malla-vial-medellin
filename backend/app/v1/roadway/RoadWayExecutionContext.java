package v1.roadway;

import org.apache.pekko.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

public class RoadWayExecutionContext extends CustomExecutionContext {

    @Inject
    public RoadWayExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "roadway.repository");
    }
}
