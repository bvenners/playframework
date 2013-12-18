
package play.api.test

import org.scalatest._
import selenium.WebBrowser
import concurrent.Eventually
import concurrent.IntegrationPatience
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

/**
 * Implementation of <code>BrowserDriver</code> that provides <code>HtmlUnitDriver</code>.
 */
trait HttpUnitBrowser extends BrowserDriver {

  /**
   * Create an new instance of <code>HtmlUnitDriver</code>.
   */
  def createNewDriver: WebDriver = {
    val htmlUnitDriver = new HtmlUnitDriver()
    htmlUnitDriver.setJavascriptEnabled(true)
    htmlUnitDriver
  }
}

