scalacOptions in ThisBuild ++= Seq()

val commonSettings = Seq(
  name := "scio-refined",
  organization := "me.shameera",
  version := "0.1",
  scalaVersion := "2.12.10",
  developers := List(
    Developer(
      "syodage",
      "Shameera Rathnayaka Yodage",
      "",
      url("https://github.com/syodage")
    )
  ),
  initialize ~= { _ =>
    System.setProperty(
      "override.type.provider",
      "com.spotify.scio.refined.RefinedTypeProvider"
    )
  }
)

lazy val root = project
  .in(file("."))
  .settings(
    // aggregate in assembly := false
  )
  .aggregate(
    core,
    macros
  )

lazy val macros: Project = project
  .in(file("macros"))
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      compilerPlugin(
        "org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full
      )
    )
  )

lazy val core: Project = project
  .in(file("core"))
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "eu.timepit" %% "refined" % "0.9.10",
      "com.spotify" %% "scio-core" % "0.8.1",
      "com.spotify" %% "scio-bigquery" % "0.8.1",
      // "org.scala-lang" % "scala-reflect" % scalaVersion.value,
//    "com.chuusai" %% "shapeless" % "2.3.3",
      "org.apache.beam" % "beam-runners-google-cloud-dataflow-java" % "2.18.0",
      compilerPlugin(
        "org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full
      )
    )
  )
  .dependsOn(
    macros
  )
