package play.api.test

import org.scalatest._
import play.api.{Play, Application}

class MixedFixtureSpec extends MixedSpec {

  def fakeApp[A](elems: (String, String)*) = FakeApplication(additionalConfiguration = Map(elems:_*), withRoutes = TestRoute)
  def getConfig(key: String)(implicit app: Application) = app.configuration.getString(key)

  "The App function" should {
    "provide a FakeApplication" in new App(fakeApp("foo" -> "bar", "ehcacheplugin" -> "disabled")) {
      app.configuration.getString("foo") shouldBe Some("bar")
    }
    "make the FakeApplication available implicitly" in new App(fakeApp("foo" -> "bar",  "ehcacheplugin" -> "disabled")) {
      getConfig("foo") shouldBe Some("bar")
    }
    "start the FakeApplication" in new App(fakeApp("foo" -> "bar",  "ehcacheplugin" -> "disabled")) {
      Play.maybeApplication shouldBe Some(app)
    }
  }
  "The Server function" should {
    "provide a FakeApplication" in new Server(fakeApp("foo" -> "bar", "ehcacheplugin" -> "disabled")) {
      app.configuration.getString("foo") shouldBe Some("bar")
    }
    "make the FakeApplication available implicitly" in new Server(fakeApp("foo" -> "bar",  "ehcacheplugin" -> "disabled")) {
      getConfig("foo") shouldBe Some("bar")
    }
    "start the FakeApplication" in new Server(fakeApp("foo" -> "bar",  "ehcacheplugin" -> "disabled")) {
      Play.maybeApplication shouldBe Some(app)
    }
    import Helpers._
    "send 404 on a bad request" in new Server {
      import java.net._
      val url = new URL("http://localhost:" + port + "/boom")
      val con = url.openConnection().asInstanceOf[HttpURLConnection]
      try con.getResponseCode shouldBe 404
      finally con.disconnect()
    }
  }
  "The HtmlUnit function" should {
    "provide a FakeApplication" in new HtmlUnit(fakeApp("foo" -> "bar", "ehcacheplugin" -> "disabled")) {
      app.configuration.getString("foo") shouldBe Some("bar")
    }
    "make the FakeApplication available implicitly" in new HtmlUnit(fakeApp("foo" -> "bar",  "ehcacheplugin" -> "disabled")) {
      getConfig("foo") shouldBe Some("bar")
    }
    "start the FakeApplication" in new HtmlUnit(fakeApp("foo" -> "bar",  "ehcacheplugin" -> "disabled")) {
      Play.maybeApplication shouldBe Some(app)
    }
    import Helpers._
    "send 404 on a bad request" in new HtmlUnit {
      import java.net._
      val url = new URL("http://localhost:" + port + "/boom")
      val con = url.openConnection().asInstanceOf[HttpURLConnection]
      try con.getResponseCode shouldBe 404
      finally con.disconnect()
    }
    "provide a web driver" in new HtmlUnit(fakeApp()) {
      go to ("http://localhost:" + port + "/testing")
      pageTitle shouldBe "Test Page"
      click on find(name("b")).value
      eventually { pageTitle shouldBe "scalatest" }
    }
  }
  "The Firefox function" should {
    "provide a FakeApplication" in new Firefox(fakeApp("foo" -> "bar", "ehcacheplugin" -> "disabled")) {
      app.configuration.getString("foo") shouldBe Some("bar")
    }
    "make the FakeApplication available implicitly" in new Firefox(fakeApp("foo" -> "bar",  "ehcacheplugin" -> "disabled")) {
      getConfig("foo") shouldBe Some("bar")
    }
    "start the FakeApplication" in new Firefox(fakeApp("foo" -> "bar",  "ehcacheplugin" -> "disabled")) {
      Play.maybeApplication shouldBe Some(app)
    }
    import Helpers._
    "send 404 on a bad request" in new Firefox {
      import java.net._
      val url = new URL("http://localhost:" + port + "/boom")
      val con = url.openConnection().asInstanceOf[HttpURLConnection]
      try con.getResponseCode shouldBe 404
      finally con.disconnect()
    }
    "provide a web driver" in new Firefox(fakeApp()) {
      go to ("http://localhost:" + port + "/testing")
      pageTitle shouldBe "Test Page"
      click on find(name("b")).value
      eventually { pageTitle shouldBe "scalatest" }
    }
  }
  "The Safari function" should {
    "provide a FakeApplication" in new Safari(fakeApp("foo" -> "bar", "ehcacheplugin" -> "disabled")) {
      app.configuration.getString("foo") shouldBe Some("bar")
    }
    "make the FakeApplication available implicitly" in new Safari(fakeApp("foo" -> "bar",  "ehcacheplugin" -> "disabled")) {
      getConfig("foo") shouldBe Some("bar")
    }
    "start the FakeApplication" in new Safari(fakeApp("foo" -> "bar",  "ehcacheplugin" -> "disabled")) {
      Play.maybeApplication shouldBe Some(app)
    }
    import Helpers._
    "send 404 on a bad request" in new Safari {
      import java.net._
      val url = new URL("http://localhost:" + port + "/boom")
      val con = url.openConnection().asInstanceOf[HttpURLConnection]
      try con.getResponseCode shouldBe 404
      finally con.disconnect()
    }
    "provide a web driver" in new Safari(fakeApp()) {
      go to ("http://localhost:" + port + "/testing")
      pageTitle shouldBe "Test Page"
      click on find(name("b")).value
      eventually { pageTitle shouldBe "scalatest" }
    }
  }
  "The Chrome function" should {
    "provide a FakeApplication" in new Chrome(fakeApp("foo" -> "bar", "ehcacheplugin" -> "disabled")) {
      app.configuration.getString("foo") shouldBe Some("bar")
    }
    "make the FakeApplication available implicitly" in new Chrome(fakeApp("foo" -> "bar",  "ehcacheplugin" -> "disabled")) {
      getConfig("foo") shouldBe Some("bar")
    }
    "start the FakeApplication" in new Chrome(fakeApp("foo" -> "bar",  "ehcacheplugin" -> "disabled")) {
      Play.maybeApplication shouldBe Some(app)
    }
    import Helpers._
    "send 404 on a bad request" in new Chrome {
      import java.net._
      val url = new URL("http://localhost:" + port + "/boom")
      val con = url.openConnection().asInstanceOf[HttpURLConnection]
      try con.getResponseCode shouldBe 404
      finally con.disconnect()
    }
    "provide a web driver" in new Chrome(fakeApp()) {
      go to ("http://localhost:" + port + "/testing")
      pageTitle shouldBe "Test Page"
      click on find(name("b")).value
      eventually { pageTitle shouldBe "scalatest" }
    }
  }
  "The InternetExplorer function" should {
    "provide a FakeApplication" in new InternetExplorer(fakeApp("foo" -> "bar", "ehcacheplugin" -> "disabled")) {
      app.configuration.getString("foo") shouldBe Some("bar")
    }
    "make the FakeApplication available implicitly" ignore new InternetExplorer(fakeApp("foo" -> "bar",  "ehcacheplugin" -> "disabled")) {
      getConfig("foo") shouldBe Some("bar")
    }
    "start the FakeApplication" in new InternetExplorer(fakeApp("foo" -> "bar",  "ehcacheplugin" -> "disabled")) {
      Play.maybeApplication shouldBe Some(app)
    }
    import Helpers._
    "send 404 on a bad request" in new InternetExplorer {
      import java.net._
      val url = new URL("http://localhost:" + port + "/boom")
      val con = url.openConnection().asInstanceOf[HttpURLConnection]
      try con.getResponseCode shouldBe 404
      finally con.disconnect()
    }
    "provide a web driver" in new InternetExplorer(fakeApp()) {
      go to ("http://localhost:" + port + "/testing")
      pageTitle shouldBe "Test Page"
      click on find(name("b")).value
      eventually { pageTitle shouldBe "scalatest" }
    }
  }
  "Any old thing" should {
    "be doable without much boilerplate" in { () =>
       1 + 1 shouldEqual 2
     }
  }
}

