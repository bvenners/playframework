
package play.api.test

import org.scalatest._

trait OneAppPerTest extends SuiteMixin { this: Suite => 

  private var privateApp: FakeApplication = _
  implicit def app: FakeApplication = synchronized { privateApp }
  
  abstract override def withFixture(test: NoArgTest) = {
    synchronized { privateApp = new FakeApplication() }
    Helpers.running(app) {
      super.withFixture(test)
    } 
  } 
}   

