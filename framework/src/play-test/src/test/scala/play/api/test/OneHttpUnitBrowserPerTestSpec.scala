package play.api.test

import org.scalatest._
import play.api.{Play, Application}

class OneHttpUnitBrowserPerTestSpec extends UnitSpec with OneBrowserPerTest with HttpUnitBrowser {

  implicit override def app: FakeApplication = FakeApplication(additionalConfiguration = Map("foo" -> "bar", "ehcacheplugin" -> "disabled"))
  def getConfig(key: String)(implicit app: Application) = app.configuration.getString(key)

  "The OneBrowserPerTest trait" should {
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
    "provide a web driver" ignore {
      go to "http://www.google.com/"
      click on "q"
      enter("scalatest")
      eventually { pageTitle should (startWith ("scalatest") and endWith ("Search")) }
    }
  }
}

