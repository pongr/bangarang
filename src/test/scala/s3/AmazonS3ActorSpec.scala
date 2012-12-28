package com.pongr.bangarang.s3

import org.specs2.mutable.Specification
import org.specs2.time.NoTimeConversions
import org.specs2.mock._
import org.mockito.Matchers._
import akka.actor.{ActorSystem, Props}
import akka.util.duration._
import akka.testkit.{TestKit, ImplicitSender}
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.{PutObjectRequest, PutObjectResult}
import AmazonS3Actor._

class AmazonS3ActorSpec extends TestKit(ActorSystem("test")) with Specification with NoTimeConversions with ImplicitSender with Mockito {
  sequential

  "The S3 actor" should {
    "call putObject on AmazonS3 object when it receives a PutJpeg message" in {
      val putJpeg = PutJpeg(Array[Byte](1), "bucket", "key")
      val result = new PutObjectResult
      val s3 = mock[AmazonS3]
      //s3.putObject(putJpeg.toPutObjectRequest) returns result <==== equals() returns false for PutObjectRequest so it never matches
      s3.putObject(anyObject()) returns result
      
      val a = system.actorOf(Props(new AmazonS3Actor(s3)))
      a ! putJpeg
      receiveOne(1 second) must_== result
    }
  }

  step(system.shutdown())
}
