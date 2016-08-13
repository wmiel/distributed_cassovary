import NativePackagerHelper._

name := "akka-sample-main-scala"

version := "2.4.4"

scalaVersion := "2.11.7"

resolvers += "Twitter" at "http://maven.twttr.com"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.4",
  "com.typesafe.akka" %% "akka-remote" % "2.4.4"
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

libraryDependencies += "it.unimi.dsi" % "fastutil" % "7.0.7"

libraryDependencies += "com.twitter" %% "cassovary-core" % "6.3.0"

enablePlugins(JavaServerAppPackaging)

mainClass in Compile := Some("dist_casso.GraphProcessor")

mappings in Universal ++= {
  // optional example illustrating how to copy additional directory
  directory("scripts") ++
  // copy configuration files to config directory
  contentOf("src/main/resources").toMap.mapValues("config/" + _)
}

// add 'config' directory first in the classpath of the start script,
// an alternative is to set the config file locations via CLI parameters
// when starting the application
scriptClasspath := Seq("../config/") ++ scriptClasspath.value

licenses := Seq(("CC0", url("http://creativecommons.org/publicdomain/zero/1.0")))
