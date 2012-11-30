package com.pongr.bangarang

import com.amazonaws.services.s3.AmazonS3Client
import akka.dispatch.ExecutionContext

package object s3 {
  implicit def syncToAsync(s3: AmazonS3Client)(implicit context: ExecutionContext): AmazonS3ClientAsync = new AmazonS3ClientAsync(s3, context)
}