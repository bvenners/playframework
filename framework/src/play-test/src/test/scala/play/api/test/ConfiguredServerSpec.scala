package play.api.test

import org.scalatest._
import events._
import play.api.{Play, Application}
import scala.collection.mutable.ListBuffer

class ConfiguredServerSpec extends UnitSpec with SequentialNestedSuiteExecution with OneServerPerSuite {

  override def nestedSuites = Vector(new ConfiguredServerNestedSuite)

  implicit override val app: FakeApplication = FakeApplication(additionalConfiguration = Map("foo" -> "bar", "ehcacheplugin" -> "disabled"))
  def getConfig(key: String)(implicit app: Application) = app.configuration.getString(key)
}
