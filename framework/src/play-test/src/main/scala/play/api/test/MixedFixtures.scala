
package play.api.test

import org.scalatest._
import fixture._
import selenium.WebBrowser
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.safari.SafariDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.ie.InternetExplorerDriver

/**
 * Trait that provides the fixture for <code>TestServer</code>, <code>FakeApplication</code> and different kinds 
 * of <code>WebBrowser</code> implementations.
 */
trait MixedFixtures extends SuiteMixin with UnitFixture { this: fixture.Suite =>

  /**
   * Class that provides fixture for <code>FakeApplication</code>, it will run the passed in <code>FakeApplication</code> 
   * before running the test.
   */
  abstract class App(val app: FakeApplication = FakeApplication()) extends NoArg {
    /**
     * Make the passed in <code>FakeApplication</code> implicit.
     */
    implicit def implicitApp: FakeApplication = app

    /**
     * Override to run the passed in <code>FakeApplication</code> first before running the test.
     */
    override def apply() {
      Helpers.running(app)(super.apply())
    }
  }

  /**
   * Class that provides fixture for <code>TestServer</code> and <code>FakeApplication</code>, 
   * it will run the passed in <code>TestServer</code> before running the test.
   */
  abstract class Server(val app: FakeApplication = FakeApplication(), val port: Int = Helpers.testServerPort) extends NoArg {
    /**
     * Make the passed in <code>FakeApplication</code> implicit.
     */
    implicit def implicitApp: FakeApplication = app

    /**
     * Override to run a <code>TestServer</code> using the passed in <code>port</code> before running the test.
     */
    override def apply() {
      Helpers.running(TestServer(port, app))(super.apply())
    }
  }

  /**
   * Class that provides fixture for <code>HtmlUnit</code> browser.
   */
  abstract class HtmlUnit(val app: FakeApplication = FakeApplication(), val port: Int = Helpers.testServerPort) extends WebBrowser with NoArg {
    /**
     * A lazy implicit instance of <code>HtmlUnitDriver</code>, it will hold <code>NoDriver</code> if <code>HtmlUnitDriver</code> 
     * is not available in the running machine.
     */
    implicit lazy val webDriver: WebDriver = 
      try {
        val htmlUnitDriver = new HtmlUnitDriver()
        htmlUnitDriver.setJavascriptEnabled(true)
        htmlUnitDriver
      }
      catch {
        case _: Throwable => NoDriver
      }

    /**
     * Make the passed in <code>FakeApplication</code> implicit.
     */
    implicit def implicitApp: FakeApplication = app

    /**
     * Override to run a <code>TestServer</code> using the passed in <code>port</code> before running the test, 
     * and close the <code>HtmlUnitDriver</code> automatically after test execution.
     */
    override def apply() {
      webDriver match {
        case NoDriver => cancel
        case _ => 
          try Helpers.running(TestServer(port, app))(super.apply())
          finally webDriver.close()
      }
    }
  }

  /**
   * Class that provides fixture for <code>Firefox</code> browser.
   */
  abstract class Firefox(val app: FakeApplication = FakeApplication(), val port: Int = Helpers.testServerPort) extends WebBrowser with NoArg {
    /**
     * A default <code>FirefoxProfile</code> used to create <code>FirefoxDriver</code>.  User can override this 
     * to use a different <code>FirefoxProfile</code>.
     */
    val firefoxProfile = new FirefoxProfile()

    /**
     * A lazy implicit instance of <code>FirefoxDriver</code>, it will hold <code>NoDriver</code> if <code>FirefoxDriver</code> 
     * is not available in the running machine.
     */
    implicit lazy val webDriver: WebDriver = 
      try {
        new FirefoxDriver(firefoxProfile)
      }
      catch {
        case _: Throwable => NoDriver
      }

    /**
     * Make the passed in <code>FakeApplication</code> implicit.
     */
    implicit def implicitApp: FakeApplication = app

    /**
     * Override to run a <code>TestServer</code> using the passed in <code>port</code> before running the test, 
     * and close the <code>FirefoxDriver</code> automatically after test execution.
     */
    override def apply() {
      try Helpers.running(TestServer(port, app))(super.apply())
      finally webDriver.close()
    }
  }

  /**
   * Class that provides fixture for <code>Safari</code> browser.
   */
  abstract class Safari(val app: FakeApplication = FakeApplication(), val port: Int = Helpers.testServerPort) extends WebBrowser with NoArg {
    /**
     * A lazy implicit instance of <code>SafariDriver</code>, it will hold <code>NoDriver</code> if <code>SafariDriver</code> 
     * is not available in the running machine.
     */
    implicit lazy val webDriver: WebDriver = 
      try {
        new SafariDriver()
      }
      catch {
        case _: Throwable => NoDriver
      }

    /**
     * Make the passed in <code>FakeApplication</code> implicit.
     */
    implicit def implicitApp: FakeApplication = app

    /**
     * Override to run a <code>TestServer</code> using the passed in <code>port</code> before running the test, 
     * and close the <code>SafariDriver</code> automatically after test execution.
     */
    override def apply() {
      webDriver match {
        case NoDriver => cancel
        case _ => 
          try Helpers.running(TestServer(port, app))(super.apply())
          finally webDriver.close()
      }
    }
  }

  /**
   * Class that provides fixture for <code>Chrome</code> browser.
   */
  abstract class Chrome(val app: FakeApplication = FakeApplication(), val port: Int = Helpers.testServerPort) extends WebBrowser with NoArg {
    /**
     * A lazy implicit instance of <code>ChromeDriver</code>, it will hold <code>NoDriver</code> if <code>ChromeDriver</code> 
     * is not available in the running machine.
     */
    implicit lazy val webDriver: WebDriver = 
      try { 
        new ChromeDriver()
      }
      catch {
        case _: Throwable => cancel
      }

    /**
     * Make the passed in <code>FakeApplication</code> implicit.
     */
    implicit def implicitApp: FakeApplication = app

    /**
     * Override to run a <code>TestServer</code> using the passed in <code>port</code> before running the test, 
     * and close the <code>ChromeDriver</code> automatically after test execution.
     */
    override def apply() {
      webDriver match {
        case NoDriver => cancel
        case _ => 
          try Helpers.running(TestServer(port, app))(super.apply())
          finally webDriver.close()
      }
    }
  }

  /**
   * Class that provides fixture for <code>InternetExplorer</code> browser.
   */
  abstract class InternetExplorer(val app: FakeApplication = FakeApplication(), val port: Int = Helpers.testServerPort) extends WebBrowser with NoArg {
    /**
     * A lazy implicit instance of <code>InternetExplorerDriver</code>, it will hold <code>NoDriver</code> if <code>InternetExplorerDriver</code> 
     * is not available in the running machine.
     */
    implicit lazy val webDriver: WebDriver = 
      try {
        new InternetExplorerDriver()
      }
      catch {
        case _: Throwable => cancel
      }

    /**
     * Make the passed in <code>FakeApplication</code> implicit.
     */
    implicit def implicitApp: FakeApplication = app

    /**
     * Override to run a <code>TestServer</code> using the passed in <code>port</code> before running the test, 
     * and close the <code>InternetExplorerDriver</code> automatically after test execution.
     */
    override def apply() {
      webDriver match {
        case NoDriver => cancel
        case _ => 
          try Helpers.running(TestServer(port, app))(super.apply())
          finally webDriver.close()
      }
    }
  }
}

