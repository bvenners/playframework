
package play.api.test

import org.scalatest._

trait OneServerPerTest extends SuiteMixin { this: Suite =>

  private var privateApp: FakeApplication = _
  implicit def app: FakeApplication = synchronized { privateApp }
  val port: Int = Helpers.testServerPort
  
  abstract override def withFixture(test: NoArgTest) = {
    synchronized { privateApp = new FakeApplication() }
    Helpers.running(TestServer(port, app)) {
      super.withFixture(test)
    }
  }
}

