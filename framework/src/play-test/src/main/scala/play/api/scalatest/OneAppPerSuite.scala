
package play.api.scalatest

import play.api.test._
import org.scalatest._
import play.api.Play

/**
 * Trait that provides one <code>FakeApplication</code> instance per ScalaTest <code>Suite</code>.
 * 
 * It overrides ScalaTest's <code>Suite.run</code> method to call <code>Play.start()</code> before tests execution, 
 * and call <code>Play.stop()</code> after tests execution completed.  User can access the <code>FakeApplication</code>
 * in <code>args.configMap</code> using "app" key.
 */
trait OneAppPerSuite extends SuiteMixin { this: Suite => 

  /**
   * An implicit instance of <code>FakeApplication</code>.
   */
  implicit val app: FakeApplication = new FakeApplication()
  
  /**
   * Overriden to start <code>Play</code> before running the tests, pass a <code>FakeApplication</code> into the tests in 
   * <code>args.configMap</code> via "app" key, call <code>super.run</code> and stop <code>Play</code> after test executions.
   *
   * @param testName an optional name of one test to run. If <code>None</code>, all relevant tests should be run.
   *                 I.e., <code>None</code> acts like a wildcard that means run all relevant tests in this <code>Suite</code>.
   * @param args the <code>Args</code> for this run
   * @return a <code>Status</code> object that indicates when all tests and nested suites started by this method have completed, and whether or not a failure occurred.
   */
  abstract override def run(testName: Option[String], args: Args): Status = {
    try {
      Play.start(app)
      val newConfigMap = args.configMap + ("app" -> app)
      val newArgs = args.copy(configMap = newConfigMap)
      super.run(testName, newArgs)
    } finally Play.stop()
  }
}   

