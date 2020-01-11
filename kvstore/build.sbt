import sbt.Keys.excludeDependencies

scalacOptions ++= Seq(
  "-encoding", "UTF-8",    // source files are in UTF-8
  "-deprecation",          // warn about use of deprecated APIs
  "-unchecked",            // warn about unchecked type parameters
  "-feature",              // warn about misused language features
  "-language:existentials",
  "-language:higherKinds", // allow higher kinded types without `import scala.language.higherKinds`
  "-language:postfixOps",
  "-Xlint",                // enable handy linter warnings
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfatal-warnings"       // turn compiler warnings into errors
)

lazy val root = (project in file("."))
  .settings(
    scalaVersion     := "2.13.1",
    version          := "0.1.0-SNAPSHOT",
    organization     := "com.sherpair",
    organizationName := "Sherpair",
    name := "kvstore",
    libraryDependencies ++= Dependencies.root
      .map(_.withSources)
      .map(_.withJavadoc),
    excludeDependencies ++= Seq(
      "org.typelevel" % "scala-library",
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3"),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
  )
