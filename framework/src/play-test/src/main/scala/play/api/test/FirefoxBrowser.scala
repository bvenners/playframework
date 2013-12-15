
package play.api.test

import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.{FirefoxDriver => SeleniumFirefoxDriver}
import org.openqa.selenium.firefox.FirefoxProfile

trait FirefoxBrowser extends BrowserDriver {
  val firefoxProfile = new FirefoxProfile()
  def createNewDriver: WebDriver = new SeleniumFirefoxDriver(firefoxProfile)
}

