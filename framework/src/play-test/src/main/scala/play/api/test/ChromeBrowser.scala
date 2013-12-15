
package play.api.test

import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

trait ChromeBrowser extends BrowserDriver {
  def createNewDriver: WebDriver = new ChromeDriver()
}

