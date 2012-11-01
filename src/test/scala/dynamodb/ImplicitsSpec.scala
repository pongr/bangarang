package com.pongr.bangarang.dynamodb

import org.specs2.mutable._
import com.amazonaws.services.dynamodb.model.AttributeValue
import java.util.Date
import java.nio.ByteBuffer

class ImplicitsSpec extends Specification {
  "The package implicits" should {
    "convert a String to an AttributeValue" in {
      ("a": AttributeValue) must_== new AttributeValue().withS("a")
    }

    "convert numbers to AttributeValues" in {
      (1: AttributeValue) must_== new AttributeValue().withN("1")
      (2.toLong: AttributeValue) must_== new AttributeValue().withN("2")
      (3.toShort: AttributeValue) must_== new AttributeValue().withN("3")
      (0.4f: AttributeValue) must_== new AttributeValue().withN("0.4")
      (0.5d: AttributeValue) must_== new AttributeValue().withN("0.5")

      ((1: java.lang.Integer): AttributeValue) must_== new AttributeValue().withN("1")
      ((2: java.lang.Long): AttributeValue) must_== new AttributeValue().withN("2")
      ((new java.lang.Short(3: Short)): AttributeValue) must_== new AttributeValue().withN("3")
      ((0.4f: java.lang.Float): AttributeValue) must_== new AttributeValue().withN("0.4")
      ((0.5d: java.lang.Double): AttributeValue) must_== new AttributeValue().withN("0.5")
    }

    "convert Boolean to AttributeValue" in {
      (true: AttributeValue) must_== new AttributeValue().withS("true")
      (false: AttributeValue) must_== new AttributeValue().withS("false")

      ((true: java.lang.Boolean): AttributeValue) must_== new AttributeValue().withS("true")
      ((false: java.lang.Boolean): AttributeValue) must_== new AttributeValue().withS("false")
    }

    "convert dates to AttributeValues" in {
      val d = new Date()
      (d: AttributeValue) must_== new AttributeValue().withN(d.getTime.toString)
    }

    "convert a byte array to AttributeValue" in {
      val bs = Array[Byte](0, 1, 2, 3, 4, 5)
      (bs: AttributeValue) must_== new AttributeValue().withB(ByteBuffer.wrap(bs))
    }
  }
}