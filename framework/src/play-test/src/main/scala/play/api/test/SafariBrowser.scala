
package play.api.test

import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver
import org.openqa.selenium.safari.SafariDriver

trait SafariBrowser extends BrowserDriver {
  def createNewDriver: WebDriver = new SafariDriver()
}

