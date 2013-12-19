
package play.api.test

import org.scalatest._

/**
 * Trait that provides new running <code>FakeApplication</code> instance for each test executed in a ScalaTest <code>Suite</code>.
 * 
 * It overrides ScalaTest's <code>withFixture</code> method to create new <code>FakeApplication</code> instance and  
 * run it before executing the test.
 */
trait OneAppPerTest extends SuiteMixin { this: Suite => 

  /**
   * Method to create new instance of <code>FakeApplication</code>
   */
  def newApp: FakeApplication = new FakeApplication()
  private var appPerTest: FakeApplication = _

  /**
   * Implicit method that returns the <code>FakeApplication</code> instance for the current test.
   */
  implicit final def app: FakeApplication = synchronized { appPerTest }

  /**
   * Overriden to create new <code>FakeApplication</code> instance and run it before executing each test.
   *
   * @param test the no-arg test function to run with a fixture
   * @return the <code>Outcome</code> of the test execution
   */
  abstract override def withFixture(test: NoArgTest) = {
    synchronized { appPerTest = newApp }
    Helpers.running(app) {
      super.withFixture(test)
    } 
  } 
}   

