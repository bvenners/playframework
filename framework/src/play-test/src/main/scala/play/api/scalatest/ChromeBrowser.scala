
package play.api.scalatest

import play.api.test._
import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

/**
 * Trait providing a <code>createNewDriver</code> method that creates a new Selenium <code>ChromeDriver</code>.
 */
trait ChromeBrowser extends BrowserDriver {

  /**
   * Creates a new instance of <code>ChromeDriver</code>.
   */
  def createNewDriver: WebDriver = new ChromeDriver()
}

