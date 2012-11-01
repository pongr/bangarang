organization := "com.pongr"

name := "bangarang"

version := "0.1-SNAPSHOT"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-java-sdk" % "1.3.22",
  "org.specs2" %% "specs2" % "1.12.2" % "test"
)

//http://www.scala-sbt.org/using_sonatype.html
//https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide
publishTo <<= version { v: String =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots/")
  else                             Some("releases" at nexus + "service/local/staging/deploy/maven2/")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

licenses := Seq("Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0"))

homepage := Some(url("http://github.com/pongr/bangarang"))

organizationName := "Pongr"

organizationHomepage := Some(url("http://pongr.com"))

description := "Not called aws-scala-sdk so we don't get sued"

pomExtra := (
  <scm>
    <url>git@github.com:pongr/bangarang.git</url>
    <connection>scm:git:git@github.com:pongr/bangarang.git</connection>
  </scm>
  <developers>
    <developer>
      <id>zcox</id>
      <name>Zach Cox</name>
      <url>http://theza.ch</url>
    </developer>
  </developers>
)
