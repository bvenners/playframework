
package play.api.test

import org.scalatest._
import play.api.Play

trait OneAppPerSuite extends SuiteMixin { this: Suite => 

  private var privateApp: FakeApplication = _
  implicit def app: FakeApplication = synchronized { privateApp }
  
  abstract override def run(testName: Option[String], args: Args): Status = {
    synchronized { privateApp = new FakeApplication() }
    try {
      Play.start(app)
      super.run(testName, args)
    } finally Play.stop()
  }
}   

