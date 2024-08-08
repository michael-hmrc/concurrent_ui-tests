package blog

// src/test/scala/ConfigReader.scala

import blog.configuration.{AppConfig, ChromeDriverConfiguration}
import cats.effect.Sync
import pureconfig.ConfigSource
import pureconfig.generic.auto._

trait ConfigReader[F[_]] {
  def loadAppConfig: F[AppConfig]

  def loadChromedriverConfig: F[ChromeDriverConfiguration]
}

object ConfigReader {
  def apply[F[_]](implicit ev: ConfigReader[F]): ConfigReader[F] = ev

  implicit def syncConfigReader[F[_] : Sync]: ConfigReader[F] = new ConfigReader[F] {

    override def loadAppConfig: F[AppConfig] = Sync[F].delay {
      ConfigSource.resources("test.conf").loadOrThrow[AppConfig]
    }

    override def loadChromedriverConfig: F[ChromeDriverConfiguration] = Sync[F].delay {
      ConfigSource.resources("chromedriver.conf").loadOrThrow[ChromeDriverConfiguration]
    }
  }
}