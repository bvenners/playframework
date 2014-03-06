
package play.api.scalatest

import play.api.test._
import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile

/**
 * Implementation of <code>BrowserDriver</code> that provides <code>FirefoxDriver</code>.
 */
trait FirefoxBrowser extends BrowserDriver {
  /**
   * <code>FirefoxProfile</code> that is used to create new instance of <code>FirefoxDriver</code>.
   */
  val firefoxProfile = new FirefoxProfile()

  /**
   * Create an new instance of <code>FirefoxDriver</code>.
   */
  def createNewDriver: WebDriver = new FirefoxDriver(firefoxProfile)
}

