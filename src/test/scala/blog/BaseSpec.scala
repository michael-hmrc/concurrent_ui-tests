package blog

import blog.BrowserModeConfiguration.headlessOrNonHeadless
import blog.EnvironmentConfiguration.environment
import cats.effect.{IO, Resource}
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.scalatest.matchers.should.Matchers

import java.net.URL
import java.time.Duration.{ofMillis, ofSeconds}
import scala.concurrent.duration.{DurationInt, FiniteDuration}

trait BaseSpec extends Matchers {

  val sleepTime: FiniteDuration = 1000.seconds

  val configReader: ConfigReader[IO] = ConfigReader[IO]

  def wait(driver: WebDriver): WebDriverWait = new WebDriverWait(driver, ofSeconds(5), ofMillis(500))

  val chromedriverPath: IO[String] =
    environment() match {
      case GithubActions => configReader.loadChromedriverConfig.map(_.chromedriver.path.github)
      case _ => configReader.loadChromedriverConfig.map(_.chromedriver.path.local)
      case _ => configReader.loadChromedriverConfig.map(_.chromedriver.path.local)
    }

  def headlessMode(chromeOptions: ChromeOptions): IO[Unit] = {
    headlessOrNonHeadless() match {
      case Headless => IO(chromeOptions.addArguments("--headless"))
      case NonHeadless => IO.unit
    }
  }

  def withWebDriver[A](test: WebDriver => IO[A]): IO[A] = {
    val chromeOptions = new ChromeOptions()
    val acquire: IO[WebDriver] = {
      for {
        driverProperty <- configReader.loadChromedriverConfig.map(_.chromedriver.path.driver)
        driverLocationPath <- chromedriverPath
        _ <- environment() match {
          case GithubActions => IO.unit
          case _ => IO(System.setProperty(driverProperty, driverLocationPath))
        }
        configOptions <- configReader.loadChromedriverConfig.map(config => config.chromedriver.options)
        _ <- IO(configOptions.map(chromeOptions.addArguments(_)))
        _ <- headlessMode(chromeOptions)
      } yield {
        environment() match {
          case GithubActions => new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), chromeOptions)
          case _ => new ChromeDriver(chromeOptions)
        }
      }
    }

    def release(driver: WebDriver): IO[Unit] = IO(driver.quit())

    Resource.make(acquire)(release).use(test)
  }

}
