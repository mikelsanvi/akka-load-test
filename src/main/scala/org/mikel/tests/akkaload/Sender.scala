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
        } else{
          if(isError(response.get)) {
            executor ! SendError
          } else {
            executor ! SendSuccess(executionTime(response.get))
          }
        }
      })
    case Finished =>
      close
  }

  def isError(response:T):Boolean = false

  def send: Future[T]

  def close: Unit

  def executionTime(response:T):Long = {
    -1
  }
}
