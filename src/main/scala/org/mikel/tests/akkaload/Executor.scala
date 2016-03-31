package org.mikel.tests.akkaload

import akka.actor.{Props, ActorLogging, Actor}

/**
 * Created by mikelsanvicente on 17/03/16.
 */

class Executor(numberOfMessages:Long, concurrency:Int,sender: Props)
  extends Actor with ActorLogging {

  val worker = context.actorOf(sender, name = "sender")

  val startedTime = System.currentTimeMillis()

  var nrOfTasks: Long = _
  var nrOfResults: Long = _
  var nrOfErrors: Long = _
  var totalTook: Long = _

  def receive = {
    case Execute =>
      if(numberOfMessages > concurrency)
        nrOfTasks = concurrency
      else
        nrOfTasks = numberOfMessages
      1L to nrOfTasks foreach { _ =>
        worker ! SendMessage
      }
    case SendSuccess(took) =>
      totalTook += took
      nrOfResults += 1
      checkIfFinished()
    case SendError =>
      nrOfErrors += 1
      checkIfFinished()
  }

  def checkIfFinished(): Unit = {
    if(numberOfMessages > nrOfTasks) {
      nrOfTasks += 1
      worker ! SendMessage
    }
    if (nrOfResults + nrOfErrors == numberOfMessages) {
      log.warning(nrOfErrors + " errors")
      log.warning("Execution time: " + (System.currentTimeMillis() - startedTime).toString )
      log.warning("Avg time: " + (totalTook / numberOfMessages).toString )
      Thread.sleep(500)
      context.stop(self)
      context.system.shutdown()
      System.exit(0)
    }
  }
}