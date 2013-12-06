
package play.api.test

import org.scalatest._

trait OneServerPerSuite extends SuiteMixin { this: Suite =>

  implicit val app: FakeApplication = new FakeApplication()
  val port: Int = Helpers.testServerPort

  abstract override def run(testName: Option[String], args: Args): Status = {
    val testServer = TestServer(port, app)
    try {
      testServer.start()
      val newConfigMap = args.configMap + ("app" -> app) + ("port" -> port)
      val newArgs = args.copy(configMap = newConfigMap)
      super.run(testName, newArgs)
    } finally testServer.stop()
  }
}

