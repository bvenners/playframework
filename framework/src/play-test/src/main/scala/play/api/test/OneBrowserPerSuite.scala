
package play.api.test

import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

trait OneBrowserPerSuite extends SuiteMixin with WebBrowser with Eventually with IntegrationPatience with Driver { this: Suite =>

  implicit val app: FakeApplication = new FakeApplication()
  val port: Int = Helpers.testServerPort
  implicit val webDriver: WebDriver = createNewDriver
  def createNewDriver: WebDriver = {
    val htmlUnitDriver = new HtmlUnitDriver()
    htmlUnitDriver.setJavascriptEnabled(true)
    htmlUnitDriver
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
      webDriver.close()
    }
  }
}

