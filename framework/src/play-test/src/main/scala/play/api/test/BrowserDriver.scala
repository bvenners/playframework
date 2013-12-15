
package play.api.test

import org.openqa.selenium.WebDriver

trait BrowserDriver {
  def createNewDriver: WebDriver
}

