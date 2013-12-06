
package play.api.test

import org.scalatest._

trait OneServerPerSuite extends SuiteMixin { this: Suite =>

  private var privateApp: FakeApplication = _
  implicit def app: FakeApplication = synchronized { privateApp }
  val port: Int = Helpers.testServerPort

  abstract override def run(testName: Option[String], args: Args): Status = {
    synchronized { privateApp = new FakeApplication() }
    val testServer = TestServer(port, app)
    try {
      testServer.start()
      super.run(testName, args)
    } finally testServer.stop()
  }
}

