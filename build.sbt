name := "scio-refined"
organization := "me.shameera"
version := "0.1"
scalaVersion := "2.12.10"

scalacOptions in ThisBuild ++= Seq()

developers := List(
  Developer("syodage", "Shameera Rathnayaka Yodage", "", url("https://github.com/syodage"))
)

libraryDependencies ++= Seq(
  "eu.timepit" %% "refined" % "0.9.10",
  "com.spotify" %% "scio-core" % "0.8.1",
  "com.spotify" %% "scio-bigquery" % "0.8.1"
//        , "com.chuusai" %% "shapeless" % "2.3.3"
)