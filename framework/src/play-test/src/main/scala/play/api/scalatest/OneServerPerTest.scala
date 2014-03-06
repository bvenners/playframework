
package play.api.scalatest

import play.api.test._
import org.scalatest._

/**
 * Trait that provides a new running <code>TestServer</code> instance for each test executed in a ScalaTest <code>Suite</code>.
 * 
 * It overrides ScalaTest's <code>withFixture</code> method to create new <code>FakeApplication</code> instance,
 * make it available from method <code>app</code>, create a new <code>TestServer</code> instance, and execute the
 * test surrounded by a call to <code>Helpers.running(TestServer(port, app))</code>.
 * In the tests you can access the <code>FakeApplication</code> using the <code>app</code> parameterless method and
 * the port used by the <code>TestServer</code> via the <code>port</code>field.
 */
trait OneServerPerTest extends SuiteMixin { this: Suite =>

  private var privateApp: FakeApplication = _

  /**
   * Implicit method that returns the <code>FakeApplication</code> instance for the current test.
   */
  implicit def app: FakeApplication = synchronized { privateApp }

  /**
   * The port used by the <code>TestServer</code>.  By default this will be set to the result return from 
   * <code>Helpers.testServerPort</code>, user can override this to provide their own port number.
   */
  val port: Int = Helpers.testServerPort

  /**
   * Overriden to create new <code>TestServer</code> instance and run it before executing each test.
   *
   * @param test the no-arg test function to run with a fixture
   * @return the <code>Outcome</code> of the test execution
   */
  abstract override def withFixture(test: NoArgTest) = {
    synchronized { privateApp = new FakeApplication() }
    Helpers.running(TestServer(port, app)) {
      super.withFixture(test)
    }
  }
}

