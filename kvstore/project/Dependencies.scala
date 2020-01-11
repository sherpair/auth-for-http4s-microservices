import sbt._

object Dependencies {

  object version {
    val cats = "2.1.0"
    val catsEffect = "2.0.0"
    val http4s = "0.21.0-M6"
    val logback = "1.2.3"
  }

  lazy val root = Seq(
    "org.typelevel"         %% "cats-core"                 % version.cats,
    "org.typelevel"         %% "cats-effect"               % version.catsEffect,
    "org.http4s"            %% "http4s-blaze-server"       % version.http4s,
    "org.http4s"            %% "http4s-circe"              % version.http4s,
    "org.http4s"            %% "http4s-dsl"                % version.http4s,
    "ch.qos.logback"        %  "logback-classic"           % version.logback
  )
}
