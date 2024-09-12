// @GENERATOR:play-routes-compiler
// @SOURCE:conf/posts.routes

package v1.post;

import posts.RoutesPrefix;

public class routes {
  
  public static final v1.post.ReversePostController PostController = new v1.post.ReversePostController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final v1.post.javascript.ReversePostController PostController = new v1.post.javascript.ReversePostController(RoutesPrefix.byNamePrefix());
  }

}
