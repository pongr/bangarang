package com.pongr.bangarang.s3

import com.amazonaws.services.s3._
import com.amazonaws.services.s3.model._
import akka.actor._

/** PinnedDispatcher or BalancingDispatcher? */
class AmazonS3ClientActor(s3: AmazonS3Client) extends Actor {
  def receive = {
    case request: PutObjectRequest => sender ! s3.putObject(request)
  }
}