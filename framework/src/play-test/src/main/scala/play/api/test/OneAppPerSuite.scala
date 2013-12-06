
package play.api.test

import org.scalatest._
import play.api.Play

trait OneAppPerSuite extends SuiteMixin { this: Suite => 

  implicit val app: FakeApplication = new FakeApplication()
  
  abstract override def run(testName: Option[String], args: Args): Status = {
    try {
      Play.start(app)
      val newConfigMap = args.configMap + ("app" -> app)
      val newArgs = args.copy(configMap = newConfigMap)
      super.run(testName, newArgs)
    } finally Play.stop()
  }
}   

