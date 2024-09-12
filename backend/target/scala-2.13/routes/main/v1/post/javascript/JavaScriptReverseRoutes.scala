// @GENERATOR:play-routes-compiler
// @SOURCE:conf/posts.routes

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:2
package v1.post.javascript {

  // @LINE:2
  class ReversePostController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:5
    def show: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "v1.post.PostController.show",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:7
    def delete: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "v1.post.PostController.delete",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:2
    def list: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "v1.post.PostController.list",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
    // @LINE:6
    def update: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "v1.post.PostController.update",
      """
        function(id0) {
          return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:3
    def create: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "v1.post.PostController.create",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + """"})
        }
      """
    )
  
  }


}
