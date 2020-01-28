package io.sherpair.authtalk

import java.nio.file.Paths

import cats.effect.{Blocker, Resource, ExitCode, IOApp, IO}
import cats.syntax.functor._
import org.http4s.server.Server
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.staticcontent.{FileService, fileService}
import org.http4s.syntax.kleisli._

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val cwd = System.getProperty("user.dir")
    val parent = Paths.get(cwd).getParent.toAbsolutePath.toString
    app(parent).use(_ => IO.never).as(ExitCode.Success)
  }

  def app(parent: String): Resource[IO, Server[IO]] =
    for {
      blocker <- Blocker[IO]
      server <- BlazeServerBuilder[IO]
        .bindHttp(8080)
        .withHttpApp(fileService[IO](
          FileService.Config(parent, blocker)
        ).orNotFound)
        .resource
    } yield server
}
