package play.api.test

import org.scalatest._

abstract class MixedSpec extends fixture.WordSpec with Matchers with OptionValues with Inside with MixedFixtures
