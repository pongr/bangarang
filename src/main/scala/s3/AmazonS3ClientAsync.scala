package com.pongr.bangarang.s3

import com.amazonaws.services.s3._
import com.amazonaws.services.s3.model._
import com.amazonaws.services.s3.model.CannedAccessControlList._
import akka.dispatch._
import java.io.{File, InputStream, ByteArrayInputStream}
import grizzled.slf4j.Logging

trait AmazonS3Async {
  def putObjectAsync(request: PutObjectRequest): Future[PutObjectResult]

  def putObjectAsync(bucket: String, key: String, file: File): Future[PutObjectResult]

  def putObjectAsync(bucket: String, key: String, input: InputStream, metadata: ObjectMetadata, publicRead: Boolean = false): Future[PutObjectResult]

  def putBytesAsync(bucket: String, key: String, bytes: Array[Byte], contentType: String, publicRead: Boolean = false): Future[PutObjectResult]

  def putJpegAsync(bucket: String, key: String, bytes: Array[Byte], publicRead: Boolean = false): Future[PutObjectResult]
}

class AmazonS3AsyncClient(s3: AmazonS3Client, implicit val context: ExecutionContext) extends AmazonS3Async with Logging {
  def putObjectAsync(request: PutObjectRequest): Future[PutObjectResult] = 
    Future { 
      debug("Starting put object bucket=%s, key=%s" format (request.getBucketName, request.getKey))
      val result = s3.putObject(request)
      debug("Finished put object bucket=%s, key=%s" format (request.getBucketName, request.getKey))
      result
    }

  def putObjectAsync(bucket: String, key: String, file: File): Future[PutObjectResult] = 
    putObjectAsync(new PutObjectRequest(bucket, key, file))

  def putObjectAsync(bucket: String, key: String, input: InputStream, metadata: ObjectMetadata, publicRead: Boolean = false): Future[PutObjectResult] = { 
    var request = new PutObjectRequest(bucket, key, input, metadata)
    if (publicRead) request = request.withCannedAcl(PublicRead)
    s3.putObjectAsync(request)
  }

  def putBytesAsync(bucket: String, key: String, bytes: Array[Byte], contentType: String, publicRead: Boolean = false): Future[PutObjectResult] = {
    val metadata = new ObjectMetadata() //TODO would be nice to have a case class version of ObjectMetadata, along with implicit conversions
    metadata.setContentType("image/jpeg")
    metadata.setContentLength(bytes.size)
    putObjectAsync(bucket, key, new ByteArrayInputStream(bytes), metadata, publicRead)
  }

  def putJpegAsync(bucket: String, key: String, bytes: Array[Byte], publicRead: Boolean = false): Future[PutObjectResult] = 
    putBytesAsync(bucket, key, bytes, "image/jpeg", publicRead)
}
