
package play.api.scalatest

import play.api.test._
import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver
import org.openqa.selenium.safari.SafariDriver

/**
 * Trait providing a <code>createNewDriver</code> method that creates a new Selenium <code>SafariDriver</code>.
 */
trait SafariBrowser extends BrowserDriver {

  /**
   * Creates a new instance of <code>SafariDriver</code>.
   */
  def createNewDriver: WebDriver = new SafariDriver()
}

