/*
 * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */
package play.api.test

import org.scalatest._
import fixture.NoArg

abstract class App(val app: FakeApplication = FakeApplication()) extends NoArg {
  implicit val implicitApp = app
  override def apply() {
    Helpers.running(app)(super.apply())
  }
}
