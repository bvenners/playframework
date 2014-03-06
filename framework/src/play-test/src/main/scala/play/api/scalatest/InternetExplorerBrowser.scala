
package play.api.scalatest

import play.api.test._
import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver
import org.openqa.selenium.ie.InternetExplorerDriver

/**
 * Trait providing a <code>createNewDriver</code> method that creates a new Selenium <code>InternetExplorerDriver</code>.
 */
trait InternetExplorerBrowser extends BrowserDriver {

  /**
   * Create a new instance of <code>InternetExplorerDriver</code>.
   */
  def createNewDriver: WebDriver = new InternetExplorerDriver()
}

