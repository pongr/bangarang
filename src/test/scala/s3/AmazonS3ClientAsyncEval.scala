package com.pongr.bangarang.s3

import java.io.File
import java.util.concurrent.Executors
import scala.collection.JavaConversions._
import com.amazonaws.auth._
import com.amazonaws.services.s3._
import akka.dispatch._
import akka.util.Timeout
import akka.util.duration._

object AmazonS3ClientAsyncEval {
  def run(dir: String, s3: AmazonS3Client, bucket: String) {
    implicit val context = ExecutionContext.fromExecutorService(Executors.newSingleThreadExecutor())
    val files = new File(dir).listFiles.toSeq
    println("Uploading %d files..." format files.size)
    val t1 = System.currentTimeMillis
    val futures = files map { file => s3.putObjectAsync(bucket, file.getName, file) }
    val future = Future.sequence(futures)
    Await.result(future, 1000 seconds)
    val t2 = System.currentTimeMillis
    println("Uploading %d files took %d msec" format (files.size, (t2 - t1)))
  }
}