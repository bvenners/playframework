
package play.api.test

import org.openqa.selenium.WebDriver

trait Driver {
  def createNewDriver: WebDriver
}

