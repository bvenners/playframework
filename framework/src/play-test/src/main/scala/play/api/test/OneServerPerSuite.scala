
package play.api.test

import org.scalatest._

/**
 * Trait that provides one <code>TestServer</code> and <code>FakeApplication</code> instance per ScalaTest <code>Suite</code>.
 * 
 * It overrides ScalaTest's <code>Suite.run</code> method to start <code>TestServer</code> before tests execution, 
 * and stop <code>TestServer</code> automatically after tests execution completed.  User can access the <code>FakeApplication</code>
 * in <code>args.configMap</code> using "app" key, and port used by <code>TestServer</code> using "port" key.
 */
trait OneServerPerSuite extends SuiteMixin { this: Suite =>

  /**
   * An implicit instance of <code>FakeApplication</code>.
   */
  implicit val app: FakeApplication = new FakeApplication()

  /**
   * The port used by the <code>TestServer</code>.  By default this will be set to the result return from 
   * <code>Helpers.testServerPort</code>, user can override this to provide their own port number.
   */
  val port: Int = Helpers.testServerPort

  /**
   * Overriden to start <code>TestServer</code> before running the tests, pass a <code>FakeApplication</code> into the tests in 
   * <code>args.configMap</code> via "app" key and port used by the started <code>TestServer</code> via "port"key.  It then calls 
   * <code>super.run</code> to execute the tests and stop <code>TestServer</code> automatically after test executions.
   *
   * @param testName an optional name of one test to run. If <code>None</code>, all relevant tests should be run.
   *                 I.e., <code>None</code> acts like a wildcard that means run all relevant tests in this <code>Suite</code>.
   * @param args the <code>Args</code> for this run
   * @return a <code>Status</code> object that indicates when all tests and nested suites started by this method have completed, and whether or not a failure occurred.
   */
  abstract override def run(testName: Option[String], args: Args): Status = {
    val testServer = TestServer(port, app)
    try {
      testServer.start()
      val newConfigMap = args.configMap + ("app" -> app) + ("port" -> port)
      val newArgs = args.copy(configMap = newConfigMap)
      super.run(testName, newArgs)
    } finally testServer.stop()
  }
}

