package org.mikel.tests.akkaload

import akka.actor.{ActorLogging, Actor}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by mikelsanvicente on 31/03/16.
 */
abstract class Sender[T] extends Actor  with ActorLogging {

  def receive = {
    case SendMessage =>
      val executor = sender()

      val responseFuture:Future[T] = send
      responseFuture.onComplete(response => {
        if (response.isFailure) {
          log.warning(response.failed.get.toString)
          executor ! SendError
        } else
          executor ! SendSuccess(executionTime(response.get))
      })
  }

  def send: Future[T]

  def executionTime(response:T):Long = {
    -1
  }
}
