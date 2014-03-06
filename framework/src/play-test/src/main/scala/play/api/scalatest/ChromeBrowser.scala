
package play.api.scalatest

import play.api.test._
import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

/**
 * Implementation of <code>BrowserDriver</code> that provides <code>ChromeDriver</code>.
 */
trait ChromeBrowser extends BrowserDriver {

  /**
   * Create an new instance of <code>ChromeDriver</code>.
   */
  def createNewDriver: WebDriver = new ChromeDriver()
}

