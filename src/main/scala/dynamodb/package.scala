package com.pongr.bangarang

import com.amazonaws.services.dynamodb.model.AttributeValue
import java.util.Date
import java.nio.ByteBuffer

/** Scala utilities for using DynamoDB. */
package object dynamodb {
  implicit def stringToAttributeValue(s: String): AttributeValue = new AttributeValue().withS(s)

  //AnyVal types - what about Byte and Char? Could use Byte=>ByteBuffer, Char=>???
  implicit def intToAttributeValue(n: Int): AttributeValue = new AttributeValue().withN(n.toString)
  implicit def longToAttributeValue(n: Long): AttributeValue = new AttributeValue().withN(n.toString)
  implicit def shortToAttributeValue(n: Short): AttributeValue = new AttributeValue().withN(n.toString)
  implicit def floatToAttributeValue(n: Float): AttributeValue = new AttributeValue().withN(n.toString)
  implicit def doubleToAttributeValue(n: Double): AttributeValue = new AttributeValue().withN(n.toString)
  implicit def booleanToAttributeValue(b: Boolean): AttributeValue = new AttributeValue().withS(b.toString)

  implicit def jintToAttributeValue(n: java.lang.Integer): AttributeValue = (n: Int)
  implicit def jlongToAttributeValue(n: java.lang.Long): AttributeValue = (n: Long)
  implicit def jshortToAttributeValue(n: java.lang.Short): AttributeValue = (n: Short)
  implicit def jfloatToAttributeValue(n: java.lang.Float): AttributeValue = (n: Float)
  implicit def jdoubleToAttributeValue(n: java.lang.Double): AttributeValue = (n: Double)
  implicit def jbooleanToAttributeValue(b: java.lang.Boolean): AttributeValue = (b: Boolean)

  /** Stores the date as its underlying long time value. */
  implicit def dateToAttributeValue(d: Date): AttributeValue = d.getTime

  /** Stores the byte array as a ByteBuffer. */
  implicit def byteArrayToAttributeValue(bs: Array[Byte]): AttributeValue = new AttributeValue().withB(ByteBuffer.wrap(bs))

  /*
  TODO repeat an operation repeatedly until lastEvaluationKey is null, accumulating results
   - ScanRequest
   - QueryRequest

  TODO repeat an operation repeatedly until unprocessed is null, accumulating results
   - BatchGetItem - unprocessedKeys to get
   - BatchWriteItem - unprocessedItems to write

  TODO much more concise API/DSL for building Request objects
  */
}