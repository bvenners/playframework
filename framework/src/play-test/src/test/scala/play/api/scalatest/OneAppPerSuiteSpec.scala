package play.api.scalatest

import play.api.test._
import org.scalatest._
import play.api.{Play, Application}

class OneAppPerSuiteSpec extends UnitSpec with OneAppPerSuite {

  implicit override val app: FakeApplication = FakeApplication(additionalConfiguration = Map("foo" -> "bar", "ehcacheplugin" -> "disabled"))
  def getConfig(key: String)(implicit app: Application) = app.configuration.getString(key)

  // Doesn't need synchronization because set by withFixture and checked by the test
  // invoked inside same withFixture with super.withFixture(test)
  var configMap: ConfigMap = _

  override def withFixture(test: NoArgTest): Outcome = {
    configMap = test.configMap
    super.withFixture(test)
  }

  "The OneAppPerSuite trait" must {
    "provide a FakeApplication" in {
      app.configuration.getString("foo") mustBe Some("bar")
    }
    "make the FakeApplication available implicitly" in {
      getConfig("foo") mustBe Some("bar")
    }
    "start the FakeApplication" in {
      Play.maybeApplication mustBe Some(app)
    }
    "put the app in the configMap" in {
      val configuredApp = configMap.getOptional[FakeApplication]("app")
      configuredApp.value must be theSameInstanceAs app
    }
  }
}

