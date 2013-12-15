
package play.api.test

import org.scalatest._
import fixture._
import selenium.WebBrowser
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.safari.SafariDriver

trait MixedFixtures extends SuiteMixin with UnitFixture { this: fixture.Suite =>

  abstract class App(val app: FakeApplication = FakeApplication()) extends NoArg {
    implicit def implicitApp: FakeApplication = app
    override def apply() {
      Helpers.running(app)(super.apply())
    }
  }

  abstract class Server(val app: FakeApplication = FakeApplication(), val port: Int = Helpers.testServerPort) extends NoArg {
    implicit def implicitApp: FakeApplication = app
    override def apply() {
      Helpers.running(TestServer(port, app))(super.apply())
    }
  }

  abstract class HttpUnit(val app: FakeApplication = FakeApplication(), val port: Int = Helpers.testServerPort) extends WebBrowser with NoArg {
    implicit lazy val webDriver: WebDriver = {
      val htmlUnitDriver = new HtmlUnitDriver()
      htmlUnitDriver.setJavascriptEnabled(true)
      htmlUnitDriver
    }
    implicit def implicitApp: FakeApplication = app
    override def apply() {
      try Helpers.running(TestServer(port, app))(super.apply())
      finally webDriver.close()
    }
  }

  abstract class Firefox(val app: FakeApplication = FakeApplication(), val port: Int = Helpers.testServerPort) extends WebBrowser with NoArg {
    val firefoxProfile = new FirefoxProfile()
    implicit lazy val webDriver: WebDriver = new FirefoxDriver(firefoxProfile)
    implicit def implicitApp: FakeApplication = app
    override def apply() {
      try Helpers.running(TestServer(port, app))(super.apply())
      finally webDriver.close()
    }
  }

  abstract class Safari(val app: FakeApplication = FakeApplication(), val port: Int = Helpers.testServerPort) extends WebBrowser with NoArg {
    implicit lazy val webDriver: WebDriver = new SafariDriver()
    implicit def implicitApp: FakeApplication = app
    override def apply() {
      try Helpers.running(TestServer(port, app))(super.apply())
      finally webDriver.close()
    }
  }
}

