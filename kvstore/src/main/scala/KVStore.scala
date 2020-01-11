import cats.effect.{ConcurrentEffect => CE, ExitCode, IO, IOApp, Timer}
import cats.effect.concurrent.Ref
import cats.syntax.apply._
import cats.syntax.flatMap._
import cats.syntax.semigroupk._
import org.http4s.{HttpRoutes, Response}
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.kleisli._

object KVStore extends IOApp {
  def run(args: List[String]): IO[ExitCode] = program[IO]

  def program[F[_]: CE: Timer]: F[ExitCode] =
    Ref.of[F, Map[String, String]](Map.empty) >>= { ref =>
      val dsl = new Http4sDsl[F] {}; import dsl._

      def add(k: String, v: String): F[Response[F]] =
        ref.update(_.updated(k, v)) *> NoContent()

      def delete(k: String): F[Response[F]] =
        ref.update(_.removed(k)) *> NoContent()

      def find(k: String): F[Response[F]] =
        ref.get >>= { _.get(k).fold(NotFound())(Ok(_)) }

      val getRoute = HttpRoutes.of[F] {
        case GET -> Root / "kv" / k => find(k) }

      val modRoutes = HttpRoutes.of[F] {
        case DELETE -> Root / "kv" / k => delete(k)
        case    PUT -> Root / "kv" / k / v => add(k, v) }

      httpServer(getRoute <+> modRoutes)
    }

  def httpServer[F[_]: CE: Timer](routes: HttpRoutes[F]): F[ExitCode] =
    BlazeServerBuilder[F]
      .withHttpApp(routes.orNotFound)
      .serve.compile.lastOrError
}
