package v1.curb;

import org.apache.pekko.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

public class CurbExecutionContext extends CustomExecutionContext {

    @Inject
    public CurbExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "curb.repository");
    }



}
