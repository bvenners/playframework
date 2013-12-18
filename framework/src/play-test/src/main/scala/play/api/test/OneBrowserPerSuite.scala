
package play.api.test

import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver

trait OneBrowserPerSuite extends SuiteMixin with WebBrowser with Eventually with IntegrationPatience with BrowserDriver { this: Suite =>

  implicit val app: FakeApplication = new FakeApplication()
  val port: Int = Helpers.testServerPort
  implicit val webDriver: WebDriver = try { createNewDriver } catch { case t: Throwable => NoDriver }

  abstract override def withFixture(test: NoArgTest): Outcome = {
    webDriver match {
      case NoDriver => cancel
      case _ => super.withFixture(test)
    }
  }

  abstract override def run(testName: Option[String], args: Args): Status = {
    val testServer = TestServer(port, app)
    try {
      testServer.start()
      val newConfigMap = args.configMap + ("app" -> app) + ("port" -> port) + ("webDriver" -> webDriver)
      val newArgs = args.copy(configMap = newConfigMap)
      super.run(testName, newArgs)
    } finally {
      testServer.stop()
      webDriver match {
        case NoDriver => // do nothing for NoDriver
        case _ => webDriver.close()
      }
    }
  }
}

