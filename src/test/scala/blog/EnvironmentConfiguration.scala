package blog

import scala.util.Try


sealed trait Environment

case object Local extends Environment

case object QA extends Environment

case object Staging extends Environment

case object Production extends Environment

object EnvironmentConfiguration {

  lazy val environmentProperty: Either[Throwable, String] =
    Try(System.getProperty("environment")).toEither

  def environment(): Environment =
    environmentProperty match {
      case Left(e) => throw new RuntimeException(s"[EnvironmentConfiguration][environment] $e is not a valid Env")
      case Right(s) if s.toLowerCase == "local" => Local
      case Right(s) if s.toLowerCase == "qa" => QA
      case Right(s) if s.toLowerCase == "staging" => Staging
      case Right(s) if s.toLowerCase == "production" => Production
    }
}

sealed trait BrowserMode

case object Headless extends BrowserMode

case object NonHeadless extends BrowserMode

object BrowserModeConfiguration {

  lazy val headlessProperty: Either[Throwable, String] =
    Try(System.getProperty("headless")).toEither

  def headlessOrNonHeadless(): BrowserMode =
    headlessProperty match {
      case Left(e) => throw new RuntimeException(s"[BrowserModeConfiguration][headlessOrNonHeadless] $e is not a valid mode")
      case Right(s) if s.toLowerCase == "true" => Headless
      case Right(s) if s.toLowerCase == "false" => NonHeadless
    }
}