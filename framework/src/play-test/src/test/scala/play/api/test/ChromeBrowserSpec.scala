package play.api.test

import org.scalatest._
import play.api.{Play, Application}
import org.openqa.selenium.WebDriver


// Can't get this one to work either on my Mac, even with the system property set
class ChromeBrowserSpec extends UnitSpec with OneBrowserPerSuite with ChromeBrowser {

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

  "The ChromeBrowser trait" should {
    "provide a FakeApplication" in {
      app.configuration.getString("foo") shouldBe Some("bar")
    }
    "make the FakeApplication available implicitly" in {
      getConfig("foo") shouldBe Some("bar")
    }
    "start the FakeApplication" in {
      Play.maybeApplication shouldBe Some(app)
    }
    "provide the port" in {
      port shouldBe Helpers.testServerPort
    }
    import Helpers._
    "send 404 on a bad request" in {
      import java.net._
      val url = new URL("http://localhost:" + port + "/boum")
      val con = url.openConnection().asInstanceOf[HttpURLConnection]
      try con.getResponseCode shouldBe 404
      finally con.disconnect()
    }
    "put the app in the configMap" in {
      val configuredApp = configMap.getOptional[FakeApplication]("app")
      configuredApp.value should be theSameInstanceAs app
    }
    "put the port in the configMap" in {
      val configuredPort = configMap.getOptional[Int]("port")
      configuredPort.value shouldEqual port
    }
    "put the webDriver in the configMap" in {
      val configuredApp = configMap.getOptional[WebDriver]("webDriver")
      configuredApp shouldBe defined
    }
    "provide a web driver" in {
      go to ("http://localhost:" + port + "/testing")
      pageTitle shouldBe "Test Page"
      click on find(name("b")).value
      eventually { pageTitle shouldBe "scalatest" }
    }
  }
}

