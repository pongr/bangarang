package com.pongr.bangarang.s3

import com.amazonaws.services.s3._
import com.amazonaws.services.s3.model._
import akka.dispatch._
import java.io.{File, InputStream}

class AmazonS3ClientAsync(s3: AmazonS3Client, implicit val context: ExecutionContext) {
  def putObjectAsync(request: PutObjectRequest): Future[PutObjectResult] = 
    Future { s3.putObject(request) }

  def putObjectAsync(bucket: String, key: String, file: File): Future[PutObjectResult] = 
    Future { s3.putObject(bucket, key, file) }

  def putObjectAsync(bucket: String, key: String, input: InputStream, metadata: ObjectMetadata): Future[PutObjectResult] = 
    Future { s3.putObject(bucket, key, input, metadata) }
}

object AmazonS3ClientAsync {
  implicit def syncToAsync(s3: AmazonS3Client)(implicit context: ExecutionContext): AmazonS3ClientAsync = new AmazonS3ClientAsync(s3, context)
}