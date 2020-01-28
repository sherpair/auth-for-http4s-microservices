val Http4sVersion = "0.21.0-M6"
val CirceVersion = "0.12.3"
val Scalacheck = "1.14.3"
val Scalatest = "3.1.0"
val LogbackVersion = "1.2.3"

lazy val root = (project in file("."))
  .settings(
    organization := "io.sherpair",
    name := "slides-to-pdf",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "io.circe"        %% "circe-generic"       % CirceVersion,
      "org.scalacheck"  %% "scalacheck"          % Scalacheck % "test",
      "org.scalatest"   %% "scalatest"           % Scalatest % "test",
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.10.3"),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1")
  )

scalacOptions ++= Seq(
  "-deprecation",              // warn about use of deprecated APIs
  "-encoding", "UTF-8",        // source files are in UTF-8
  "-feature",                  // warn about misused language features
  "-language:existentials",
  "-language:higherKinds",
  "-language:postfixOps",
  "-unchecked",                // warn about unchecked type parameters
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfatal-warnings"
)
