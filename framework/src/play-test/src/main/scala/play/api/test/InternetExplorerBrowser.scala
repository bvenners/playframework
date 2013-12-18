
package play.api.test

import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver
import org.openqa.selenium.ie.InternetExplorerDriver

/**
 * Implementation of <code>BrowserDriver</code> that provides <code>InternetExplorerDriver</code>.
 */
trait InternetExplorerBrowser extends BrowserDriver {

  /**
   * Create an new instance of <code>InternetExplorerDriver</code>.
   */
  def createNewDriver: WebDriver = new InternetExplorerDriver()
}

