
package play.api.test

import org.openqa.selenium.WebDriver

/**
 * A trait that defines a function to create new Selenium <code>WebDriver</code>.
 */
trait BrowserDriver {

  /**
   * Create an new instance of Selenium <code>WebDriver</code>.
   *
   * @return an new instance of Selenium <code>WebDriver</code>
   */
  def createNewDriver: WebDriver
}

