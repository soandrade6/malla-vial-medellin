// @GENERATOR:play-routes-compiler
// @SOURCE:conf/segment.routes

package v1.segment;

import segment.RoutesPrefix;

public class routes {
  
  public static final v1.segment.ReverseSegmentController SegmentController = new v1.segment.ReverseSegmentController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final v1.segment.javascript.ReverseSegmentController SegmentController = new v1.segment.javascript.ReverseSegmentController(RoutesPrefix.byNamePrefix());
  }

}
