
package play.api.scalatest

import play.api.test._
import org.openqa.selenium.WebDriver

/**
 * Trait that defines an abstract <code>createNewDriver</code>  method for creating a new Selenium <code>WebDriver</code>.
 */
trait BrowserDriver {

  /**
   * Create an new instance of Selenium <code>WebDriver</code>.
   *
   * @return an new instance of Selenium <code>WebDriver</code>
   */
  def createNewDriver: WebDriver
}

