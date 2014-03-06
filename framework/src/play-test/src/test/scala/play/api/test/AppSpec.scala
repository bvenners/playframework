package play.api.test

import org.scalatest._

abstract class AppSpec extends WordSpec with Matchers with OptionValues with Inside with OneAppPerTest

