package play.api.scalatest

import play.api.test._
import org.scalatest._
import play.api.{Play, Application}

class OneServerPerSuiteSpec extends UnitSpec with OneServerPerSuite {

  implicit override val app: FakeApplication = FakeApplication(additionalConfiguration = Map("foo" -> "bar", "ehcacheplugin" -> "disabled"))
  def getConfig(key: String)(implicit app: Application) = app.configuration.getString(key)

  // Doesn't need synchronization because set by withFixture and checked by the test
  // invoked inside same withFixture with super.withFixture(test)
  var configMap: ConfigMap = _

  override def withFixture(test: NoArgTest): Outcome = {
    configMap = test.configMap
    super.withFixture(test)
  }

  "The OneServerPerSuite trait" should {
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
  }
}


