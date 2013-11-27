/*
 * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */
package play.api.test

import org.scalatest._
import play.api.{Play, Application}

class ScalaTestSpec extends UnitSpec {

  def fakeApp[A](elems: (String, String)*) = FakeApplication(additionalConfiguration = Map(elems:_*))
  def getConfig(key: String)(implicit app: Application) = app.configuration.getString(key)

  "WithApplication context" should {
    "provide an app" in new App(fakeApp("foo" -> "bar", "ehcacheplugin" -> "disabled")) {
      app.configuration.getString("foo") shouldBe Some("bar")
    }
    "make the app available implicitly" in new App(fakeApp("foo" -> "bar",  "ehcacheplugin" -> "disabled")) {
      getConfig("foo") shouldBe Some("bar")
    }
    "start the application" in new App(fakeApp("foo" -> "bar",  "ehcacheplugin" -> "disabled")) {
      Play.maybeApplication shouldBe Some(app)
    }
  }
}

