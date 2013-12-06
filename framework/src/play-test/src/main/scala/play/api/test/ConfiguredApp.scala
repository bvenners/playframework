
package play.api.test

import org.scalatest._
import play.api.Play

trait ConfiguredApp extends SuiteMixin { this: Suite => 

  private var configuredApp: FakeApplication = _
  implicit final def app: FakeApplication = synchronized { configuredApp }

  abstract override def run(testName: Option[String], args: Args): Status = {
    args.configMap.getOptional[FakeApplication]("app") match {
      case Some(ca) => synchronized { configuredApp = ca }
      case None => throw new Exception("ConfiguredApp needs a FakeApplication value associated with key \"app\" in the config map")
    }
    super.run(testName, args)
  }
}   

