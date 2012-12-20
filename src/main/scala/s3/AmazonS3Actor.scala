package com.pongr.bangarang.s3

import com.amazonaws.services.s3._
import com.amazonaws.services.s3.model._
import com.amazonaws.services.s3.model.CannedAccessControlList._
import akka.actor._
import java.io._

object AmazonS3Actor {
  sealed trait PutRequest {
    def toPutObjectRequest: PutObjectRequest
  }

  case class PutJpeg(bytes: Array[Byte], bucket: String, key: String, publicRead: Boolean = false) extends PutRequest {
    def toPutObjectRequest: PutObjectRequest = {
      val metadata = new ObjectMetadata() //TODO would be nice to have a case class version of ObjectMetadata, along with implicit conversions
      metadata.setContentType("image/jpeg")
      metadata.setContentLength(bytes.size)
      var req = new PutObjectRequest(bucket, key, new ByteArrayInputStream(bytes), metadata)
      if (publicRead) req = req.withCannedAcl(PublicRead)
      req
    }
  }
}

/** PinnedDispatcher or BalancingDispatcher? */
class AmazonS3Actor(s3: AmazonS3) extends Actor {
  import AmazonS3Actor._

  def receive = {
    case request: PutObjectRequest => sender ! s3.putObject(request)
    case request: PutRequest => sender ! s3.putObject(request.toPutObjectRequest)
  }
}
