package play.api.test

import play.api.mvc.{Handler, Action, Results}

object TestRoute extends PartialFunction[(String, String), Handler] {

  def apply(v1: (String, String)): Handler = v1 match {
    case ("GET", "/testing") => 
      Action(
        Results.Ok(
          "<html>" + 
          "<head><title>Test Page</title></head>" + 
          "<body>" + 
          "<input type='button' name='b' value='Click Me' onclick='document.title=\"scalatest\"' />" + 
          "</body>" + 
          "</html>"
        ).as("text/html")
      )
  }

  def isDefinedAt(x: (String, String)): Boolean = x._1 == "GET" && x._2 == "/testing"

}
