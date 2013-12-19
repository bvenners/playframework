package play.api.test

import org.scalatest._
import play.api.Play

trait ConfiguredServer extends SuiteMixin { this: Suite => 

  private var configuredApp: FakeApplication = _
  implicit final def app: FakeApplication = synchronized { configuredApp }

  private var configuredPort: Int = -1
  def port: Int = synchronized { configuredPort } 

  abstract override def run(testName: Option[String], args: Args): Status = {
    args.configMap.getOptional[FakeApplication]("app") match {
      case Some(ca) => synchronized { configuredApp = ca }
      case None => throw new Exception("ConfiguredServer needs a FakeApplication value associated with key \"app\" in the config map")
    }
    args.configMap.getOptional[Int]("port") match {
      case Some(cp) => synchronized { configuredPort = cp }
      case None => throw new Exception("ConfiguredServer needs an Int value associated with key \"port\" in the config map")
    }
    super.run(testName, args)
  }
}

