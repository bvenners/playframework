package play.api.test

import org.scalatest._

/*
 * Play-Test super-suite that provides a new fake Server for each test.
*/
abstract class ServerSpec extends WordSpec with Matchers with OptionValues with Inside with OneServerPerTest

