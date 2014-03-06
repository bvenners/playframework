package play.api.test

import org.scalatest._

/*
 * Play-Test super-suite for basic unit tests.
*/
abstract class UnitSpec extends WordSpec with Matchers with OptionValues with Inside

