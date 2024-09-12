// @GENERATOR:play-routes-compiler
// @SOURCE:conf/segment.routes

package segment

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:1
  SegmentController_0: v1.segment.SegmentController,
  val prefix: String
) extends GeneratedRouter {

  @javax.inject.Inject()
  def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:1
    SegmentController_0: v1.segment.SegmentController
  ) = this(errorHandler, SegmentController_0, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    segment.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, SegmentController_0, prefix)
  }

  private val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """v1.segment.SegmentController.list(request:Request)"""),
    ("""POST""", this.prefix, """v1.segment.SegmentController.create(request:Request)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """""" + "$" + """id<[^/]+>""", """v1.segment.SegmentController.show(request:Request, id:String)"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """""" + "$" + """id<[^/]+>""", """v1.segment.SegmentController.update(request:Request, id:String)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """""" + "$" + """id<[^/]+>""", """v1.segment.SegmentController.delete(request:Request, id:String)"""),
    Nil
  ).foldLeft(Seq.empty[(String, String, String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String, String, String)]
    case l => s ++ l.asInstanceOf[List[(String, String, String)]]
  }}


  // @LINE:1
  private lazy val v1_segment_SegmentController_list0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private lazy val v1_segment_SegmentController_list0_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      SegmentController_0.list(fakeValue[play.mvc.Http.Request]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "segment",
      "v1.segment.SegmentController",
      "list",
      Seq(classOf[play.mvc.Http.Request]),
      "GET",
      this.prefix + """""",
      """""",
      Seq()
    )
  )

  // @LINE:2
  private lazy val v1_segment_SegmentController_create1_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private lazy val v1_segment_SegmentController_create1_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      SegmentController_0.create(fakeValue[play.mvc.Http.Request]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "segment",
      "v1.segment.SegmentController",
      "create",
      Seq(classOf[play.mvc.Http.Request]),
      "POST",
      this.prefix + """""",
      """""",
      Seq()
    )
  )

  // @LINE:4
  private lazy val v1_segment_SegmentController_show2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), DynamicPart("id", """[^/]+""", encodeable=true)))
  )
  private lazy val v1_segment_SegmentController_show2_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      SegmentController_0.show(fakeValue[play.mvc.Http.Request], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "segment",
      "v1.segment.SegmentController",
      "show",
      Seq(classOf[play.mvc.Http.Request], classOf[String]),
      "GET",
      this.prefix + """""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:5
  private lazy val v1_segment_SegmentController_update3_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), DynamicPart("id", """[^/]+""", encodeable=true)))
  )
  private lazy val v1_segment_SegmentController_update3_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      SegmentController_0.update(fakeValue[play.mvc.Http.Request], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "segment",
      "v1.segment.SegmentController",
      "update",
      Seq(classOf[play.mvc.Http.Request], classOf[String]),
      "PUT",
      this.prefix + """""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:6
  private lazy val v1_segment_SegmentController_delete4_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), DynamicPart("id", """[^/]+""", encodeable=true)))
  )
  private lazy val v1_segment_SegmentController_delete4_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      SegmentController_0.delete(fakeValue[play.mvc.Http.Request], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "segment",
      "v1.segment.SegmentController",
      "delete",
      Seq(classOf[play.mvc.Http.Request], classOf[String]),
      "DELETE",
      this.prefix + """""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:1
    case v1_segment_SegmentController_list0_route(params@_) =>
      call { 
        v1_segment_SegmentController_list0_invoker.call(
          req => SegmentController_0.list(req))
      }
  
    // @LINE:2
    case v1_segment_SegmentController_create1_route(params@_) =>
      call { 
        v1_segment_SegmentController_create1_invoker.call(
          req => SegmentController_0.create(req))
      }
  
    // @LINE:4
    case v1_segment_SegmentController_show2_route(params@_) =>
      call(params.fromPath[String]("id", None)) { (id) =>
        v1_segment_SegmentController_show2_invoker.call(
          req => SegmentController_0.show(req, id))
      }
  
    // @LINE:5
    case v1_segment_SegmentController_update3_route(params@_) =>
      call(params.fromPath[String]("id", None)) { (id) =>
        v1_segment_SegmentController_update3_invoker.call(
          req => SegmentController_0.update(req, id))
      }
  
    // @LINE:6
    case v1_segment_SegmentController_delete4_route(params@_) =>
      call(params.fromPath[String]("id", None)) { (id) =>
        v1_segment_SegmentController_delete4_invoker.call(
          req => SegmentController_0.delete(req, id))
      }
  }
}
