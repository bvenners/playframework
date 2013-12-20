
package play.api.test

import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver
import org.openqa.selenium.safari.SafariDriver

/**
 * Implementation of <code>BrowserDriver</code> that provides <code>SafariDriver</code>.
 */
trait SafariBrowser extends BrowserDriver {

  /**
   * Create an new instance of <code>SafariDriver</code>.
   */
  def createNewDriver: WebDriver = new SafariDriver()
}

