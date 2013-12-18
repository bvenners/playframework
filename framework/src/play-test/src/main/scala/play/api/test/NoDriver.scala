package play.api.test

import org.openqa.selenium._

object NoDriver extends WebDriver {
  def close() {
    throw new UnsupportedOperationException("close not supported")
  }

  def findElement(by: By): WebElement = 
    throw new UnsupportedOperationException("findElement not supported")

  def findElements(by: By): java.util.List[WebElement] = 
    throw new UnsupportedOperationException("findElements not supported")

  def get(url: String) {
    throw new UnsupportedOperationException("get not supported")
  }

  def getCurrentUrl(): String = 
    throw new UnsupportedOperationException("getCurrentUrl not supported")

  def getPageSource(): String = 
    throw new UnsupportedOperationException("getCurrentUrl not supported")

  def getTitle(): String = 
    throw new UnsupportedOperationException("getCurrentUrl not supported")

  def getWindowHandle(): String = 
    throw new UnsupportedOperationException("getWindowHandle not supported")

  def getWindowHandles(): java.util.Set[java.lang.String] = 
    throw new UnsupportedOperationException("getWindowHandles not supported")

  def manage(): WebDriver.Options = 
    throw new UnsupportedOperationException("manage not supported")

  def navigate(): WebDriver.Navigation = 
    throw new UnsupportedOperationException("navigate not supported")

  def quit() {
    throw new UnsupportedOperationException("quit not supported")
  }

  def switchTo(): WebDriver.TargetLocator = 
    throw new UnsupportedOperationException("switchTo not supported")

}
