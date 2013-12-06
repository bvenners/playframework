
package play.api.test

import org.scalatest._

trait OneAppPerTest extends SuiteMixin { this: Suite => 

  def newApp: FakeApplication = new FakeApplication()
  private var appPerTest: FakeApplication = _
  implicit final def app: FakeApplication = synchronized { appPerTest }

  abstract override def withFixture(test: NoArgTest) = {
    synchronized { appPerTest = newApp }
    Helpers.running(app) {
      super.withFixture(test)
    } 
  } 
}   

