
package play.api.test

import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver

/**
 * Trait that provides new browser instance for each test executed in a ScalaTest <code>Suite</code>.
 * 
 * It overrides ScalaTest's <code>withFixture</code> method to create new <code>WebDriver</code> instance 
 * before executing each test.
 */
trait OneBrowserPerTest extends SuiteMixin with WebBrowser with Eventually with IntegrationPatience with BrowserDriver { this: Suite =>

  private var privateApp: FakeApplication = _

  /**
   * Method to create new instance of <code>FakeApplication</code>
   */
  implicit def app: FakeApplication = synchronized { privateApp }

  /**
   * The port used by the <code>TestServer</code>.  By default this will be set to the result return from 
   * <code>Helpers.testServerPort</code>, user can override this to provide their own port number.
   */
  val port: Int = Helpers.testServerPort

  private var privateWebDriver: WebDriver = _

  /**
   * Implicit method to get the <code>WebDriver</code> for the current test.
   */
  implicit def webDriver: WebDriver = synchronized { privateWebDriver }

  /**
   * Override <code>withFixture</code> to create new instance of <code>WebDriver</code> before 
   * running each test.  If there is error when creating <code>WebDriver</code>, <code>NoDriver</code> 
   * will be used and all tests will be canceled automatically.  If <code>WebDirver</code> creation 
   * is successful, a new instance of <code>TestServer</code> will be started for each test before they 
   * are executed.
   *
   * @param test the no-arg test function to run with a fixture
   * @return the <code>Outcome</code> of the test execution
   */
  abstract override def withFixture(test: NoArgTest) = {
    synchronized {
      privateApp = new FakeApplication()
      privateWebDriver = 
        try {
          createNewDriver
        }
        catch {
          case _: Throwable => NoDriver
        }
    }
    try {
      privateWebDriver match {
        case NoDriver => cancel
        case _ =>
          Helpers.running(TestServer(port, app)) {
            super.withFixture(test)
          }
      }
    }
    finally {
      privateWebDriver match {
        case NoDriver => // do nothing
        case _ => privateWebDriver.close()
      }
    }
  }
}

/*
Could I have OneBrowserPerTest that has a def webDriver?
Think so. Just like implicit def webDriver and privateWebDriver,
same kind of thing. So each test gets its own. Then what we need
is a factory that produces the web driver. And that's how they pick.
It would be nice if that was called Firefox, Chrome, etc. What I'd
kind of like is that ... yes, interestingly, seems like I wonder
if I could use the same for all 3. 

private lazy val sharedWebDriver = new Firefox
implicit def webDriver: WebDriver = sharedWebDriver

By default this just returns the val, but you can override it
in a subclass so that it returns a different one for each 
test. Then you can mix Firefox into any class right, so long as it
is to the left? OneFirefoxPerTest
OneChromePerTest. OneChromePerSuite is just Chrome.

And the mixed fixture one is jut named new Firefox {

Could I do OneBrowserPerTest and OneBrowserPerSuite and
have it fill in the browser type via a mixin?

OneBrowserPerTest with FirefoxBrowser

OK. Then how do we do the all browsers thing?

OneBrowserPerTest with AllBrowsers would be nice
withFixture needs to run the same tests for each browser that is available.
*/

