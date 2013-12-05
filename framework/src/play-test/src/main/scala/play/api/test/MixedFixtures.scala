
package play.api.test

import org.scalatest._
import fixture._

trait MixedFixtures extends SuiteMixin with UnitFixture { this: fixture.Suite =>

  abstract class App(val app: FakeApplication = FakeApplication()) extends NoArg {
    implicit def implicitApp: FakeApplication = app
    override def apply() {
      Helpers.running(app)(super.apply())
    }
  }

  abstract class Server(val app: FakeApplication = FakeApplication(), val port: Int = Helpers.testServerPort) extends NoArg {
    implicit def implicitApp: FakeApplication = app
    override def apply() {
      Helpers.running(TestServer(port, app))(super.apply())
    }
  }

  // implicit def bustBoilerplate(u: Unit): Function0[Unit] = () => ()
}

