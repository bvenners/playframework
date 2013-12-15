package play.api.test

import org.scalatest._
import concurrent.Eventually
import concurrent.IntegrationPatience

abstract class MixedSpec extends fixture.WordSpec with Matchers with OptionValues with Inside with MixedFixtures with
    Eventually with IntegrationPatience

