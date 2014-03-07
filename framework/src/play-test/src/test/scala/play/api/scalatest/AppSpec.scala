package play.api.scalatest

import play.api.test._
import org.scalatest._

/*
 * Play-Test super-suite that provides a new fake App to each test.
*/
abstract class AppSpec extends WordSpec with MustMatchers with OptionValues with Inside with OneAppPerTest

