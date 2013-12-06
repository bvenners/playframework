package play.api.test

import org.scalatest._
import play.api.{Play, Application}

class OneAppPerSuiteSpec extends UnitSpec with OneAppPerSuite {

  implicit override val app: FakeApplication = FakeApplication(additionalConfiguration = Map("foo" -> "bar", "ehcacheplugin" -> "disabled"))
  def getConfig(key: String)(implicit app: Application) = app.configuration.getString(key)

  var configMap: ConfigMap = _

  override def withFixture(test: NoArgTest): Outcome = {
    configMap = test.configMap
    super.withFixture(test)
  }

  "The OneAppPerSuite trait" should {
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
  }
}

