package v1.segment;
import org.apache.pekko.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;
public class SegmentExecutionContext extends CustomExecutionContext{

    @Inject
    public SegmentExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "segment.repository");
    }
}
