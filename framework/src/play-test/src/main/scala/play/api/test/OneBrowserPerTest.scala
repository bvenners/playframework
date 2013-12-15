
package play.api.test

import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile

trait OneBrowserPerTest extends WebBrowser with SuiteMixin with Eventually with IntegrationPatience with Driver { this: Suite =>

  private var privateApp: FakeApplication = _
  implicit def app: FakeApplication = synchronized { privateApp }
  val port: Int = Helpers.testServerPort
  private var privateWebDriver: WebDriver = _
  implicit def webDriver: WebDriver = synchronized { privateWebDriver }
  def createNewDriver: WebDriver = {
    val htmlUnitDriver = new HtmlUnitDriver()
    htmlUnitDriver.setJavascriptEnabled(true)
    htmlUnitDriver
  }

  abstract override def withFixture(test: NoArgTest) = {
    synchronized {
      privateApp = new FakeApplication()
      privateWebDriver = createNewDriver
    }
    try {
      Helpers.running(TestServer(port, app)) {
        super.withFixture(test)
      }
    }
    finally {
      privateWebDriver.close()
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

