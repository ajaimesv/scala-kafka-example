name := """example"""
organization := "com.codingscala"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.0"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test

// https://mvnrepository.com/artifact/org.apache.kafka/kafka
// kafka uses scala 2.12, hence we had to downgrade scalaVersion to "2.12.0".
// if we don't downgrade, sbt keeps showing an error saying that the library cannot be found.
libraryDependencies += "org.apache.kafka" %% "kafka" % "2.3.0"
