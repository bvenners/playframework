package play.api.test

import org.scalatest._

class MixedSpec extends fixture.WordSpec with Matchers with OptionValues with Inside with MixedFixtures
