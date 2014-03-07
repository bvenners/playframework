package play.api.scalatest

import play.api.test._
import org.scalatest._
import play.api.{Play, Application}

class OneHtmlUnitBrowserPerTestSpec extends UnitSpec with OneBrowserPerTest with HtmlUnitBrowser {

  implicit override def app: FakeApplication = 
    FakeApplication(
      additionalConfiguration = Map("foo" -> "bar", "ehcacheplugin" -> "disabled"), 
      withRoutes = TestRoute
    )

  def getConfig(key: String)(implicit app: Application) = app.configuration.getString(key)

  "The OneBrowserPerTest trait" must {
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
    "provide a web driver" in {
      go to ("http://localhost:" + port + "/testing")
      pageTitle mustBe "Test Page"
      click on find(name("b")).value
      eventually { pageTitle mustBe "scalatest" }
    }
  }
}

