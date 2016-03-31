package org.mikel.tests.akkaload

/**
 * Created by mikelsanvicente on 17/03/16.
 */
sealed trait TestMessage
case object Execute extends TestMessage
case object SendMessage extends TestMessage
case class SendSuccess(took:Long) extends TestMessage
case object SendError extends TestMessage
case object Finished extends TestMessage
