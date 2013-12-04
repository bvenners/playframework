/*
 * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */
package play.api.test

import org.scalatest._
import fixture.NoArg

abstract class Server(val app: FakeApplication = FakeApplication(), val port: Int = Helpers.testServerPort) extends NoArg {
  implicit val implicitApp: FakeApplication = app
  override def apply() {
    Helpers.running(TestServer(port, app))(super.apply())
  }
}

