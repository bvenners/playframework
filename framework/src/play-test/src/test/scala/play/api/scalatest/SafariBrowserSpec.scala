package play.api.scalatest

import play.api.test._
import org.scalatest._
import play.api.{Play, Application}
import org.openqa.selenium.WebDriver

class SafariBrowserSpec extends UnitSpec with OneBrowserPerSuite with SafariBrowser {

  implicit override val app: FakeApplication = 
    FakeApplication(
      additionalConfiguration = Map("foo" -> "bar", "ehcacheplugin" -> "disabled"), 
      withRoutes = TestRoute
    )
  def getConfig(key: String)(implicit app: Application) = app.configuration.getString(key)

  // Doesn't need synchronization because set by withFixture and checked by the test
  // invoked inside same withFixture with super.withFixture(test)
  var configMap: ConfigMap = _

  override def withFixture(test: NoArgTest): Outcome = {
    configMap = test.configMap
    super.withFixture(test)
  }

  "The SafariBrowser trait" must {
    "provide a FakeApplication" in {
      app.configuration.getString("foo") mustBe Some("bar")
    }
    "make the FakeApplication available implicitly" in {
      getConfig("foo") mustBe Some("bar")
    }
    "start the FakeApplication" in {
      Play.maybeApplication mustBe Some(app)
    }
    "provide the port" in {
      port mustBe Helpers.testServerPort
    }
    import Helpers._
    "send 404 on a bad request" in {
      import java.net._
      val url = new URL("http://localhost:" + port + "/boum")
      val con = url.openConnection().asInstanceOf[HttpURLConnection]
      try con.getResponseCode mustBe 404
      finally con.disconnect()
    }
    "put the app in the configMap" in {
      val configuredApp = configMap.getOptional[FakeApplication]("app")
      configuredApp.value must be theSameInstanceAs app
    }
    "put the port in the configMap" in {
      val configuredPort = configMap.getOptional[Int]("port")
      configuredPort.value mustEqual port
    }
    "put the webDriver in the configMap" in {
      val configuredApp = configMap.getOptional[WebDriver]("webDriver")
      configuredApp mustBe defined
    }
    "provide a web driver" in {
      go to ("http://localhost:" + port + "/testing")
      pageTitle mustBe "Test Page"
      click on find(name("b")).value
      eventually { pageTitle mustBe "scalatest" }
    }
  }
}


