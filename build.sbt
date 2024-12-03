ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"

name := "concurrent-ui-tests"

version := "0.1"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.18" % Test,
  "org.seleniumhq.selenium" % "selenium-java" % "4.22.0",

  "com.github.pureconfig" %% "pureconfig" % "0.17.7" % Test,

  "com.disneystreaming" %% "weaver-cats" % "0.8.4" % Test,
  "org.typelevel" %% "cats-effect" % "3.4.10"
)

testFrameworks += new TestFramework("org.scalatest.tools.Framework")

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "utf8",
  "-feature",
  "-unchecked",
  "-Xlint",
  "-Ywarn-dead-code",
  "-Ywarn-unused",
  //  "-Ywarn-unused-import"
)
