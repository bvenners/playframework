/*
 * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */
package play.api.test

import org.scalatest._
import play.api.{Play, Application}

class ScalaTestSpec extends UnitSpec {

  def fakeApp[A](elems: (String, String)*) = FakeApplication(additionalConfiguration = Map(elems:_*))
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
      val url = new URL("http://localhost:9000/boum")
      val con = url.openConnection().asInstanceOf[HttpURLConnection]
      try con.getResponseCode shouldBe 404
      finally con.disconnect()
    }
  }
}

