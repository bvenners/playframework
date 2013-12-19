package play.api.test

import org.scalatest._
import events._
import play.api.{Play, Application}
import scala.collection.mutable.ListBuffer

@DoNotDiscover
class ConfiguredServerNestedSuite extends UnitSpec with ConfiguredServer {

  def getConfig(key: String)(implicit app: Application) = app.configuration.getString(key)

  // Doesn't need synchronization because set by withFixture and checked by the test
  // invoked inside same withFixture with super.withFixture(test)
  var configMap: ConfigMap = _

  override def withFixture(test: NoArgTest): Outcome = {
    configMap = test.configMap
    super.withFixture(test)
  }

  "The ConfiguredServer trait" should {
    "provide a FakeApplication" in {
      app.configuration.getString("foo") shouldBe Some("bar")
    }
    "make the FakeApplication available implicitly" in {
      getConfig("foo") shouldBe Some("bar")
    }
    "start the FakeApplication" in {
      Play.maybeApplication shouldBe Some(app)
    }
    "put the app in the configMap" in {
      val configuredApp = configMap.getOptional[FakeApplication]("app")
      configuredApp.value should be theSameInstanceAs app
    }
    "put the port in the configMap" in {
      val configuredPort = configMap.getOptional[Int]("port")
      configuredPort.value shouldEqual port
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
  }
}

