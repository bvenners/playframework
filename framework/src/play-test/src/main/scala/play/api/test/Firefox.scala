
package play.api.test

import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile

trait Firefox extends Driver {
  val firefoxProfile = new FirefoxProfile()
  abstract override def createNewDriver: WebDriver = new FirefoxDriver(firefoxProfile)
}

