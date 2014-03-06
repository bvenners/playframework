package play.api.test

import org.scalatest._
import concurrent.Eventually
import concurrent.IntegrationPatience

/*
 * Play-Test super-suite for test classes that need different kinds of fixtures (App, Server,
 * Browser) in different tests.
*/
abstract class MixedSpec extends fixture.WordSpec with Matchers with OptionValues with Inside with MixedFixtures with
    Eventually with IntegrationPatience

