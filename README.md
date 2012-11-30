# Bangarang

Not called aws-scala-sdk so we don't get sued. Makes [aws-java-sdk](http://aws.amazon.com/sdkforjava/) more fun to work with in [Scala](http://scala-lang.org) through language features and [Akka](http://akka.io).

Bangarang is at a very early stage, and we're adding more functionality to it as we need it. That said, what is here should be very stable, as we use it in production. Please send us a pull request if you have anything to add.

# sbt

```scala
resolvers ++= Seq(
  "Typesafe" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
)

libraryDependencies ++= Seq(
  "com.pongr" %% "bangarang" % "0.1.1-SNAPSHOT"
)
```

# DynamoDB

TODO Describe the implicits...

# S3

TODO Describe AmazonS3ClientAsync and AmazonS3ClientActor...
