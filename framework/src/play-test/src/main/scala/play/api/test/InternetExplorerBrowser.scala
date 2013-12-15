
package play.api.test

import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver
import org.openqa.selenium.ie.InternetExplorerDriver

trait InternetExplorerBrowser extends BrowserDriver {
  def createNewDriver: WebDriver = new InternetExplorerDriver()
}

