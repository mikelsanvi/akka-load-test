package org.mikel.tests.akkaload

import akka.actor.{Props, ActorSystem, Actor}

/**
 * Created by mikelsanvicente on 31/03/16.
 */
class AkkaLoadTest(numberOfMessages:Long, concurrency:Int,sender : => Actor) {
  val system = ActorSystem("test-system")

  val master = system.actorOf(Props(new Executor(numberOfMessages, concurrency, sender)),
    name = "master")

  def execute: Unit = {
    master ! Execute
  }
}
