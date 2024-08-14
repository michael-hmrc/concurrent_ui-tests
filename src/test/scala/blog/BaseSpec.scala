package blog

import blog.BrowserModeConfiguration.headlessOrNonHeadless
import blog.EnvironmentConfiguration.environment
import cats.effect.{IO, Resource}
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.support.ui.WebDriverWait

import java.time.Duration.{ofMillis, ofSeconds}
import scala.concurrent.duration.{DurationInt, FiniteDuration}

trait BaseSpec {

  val sleepTime: FiniteDuration = 1.seconds

  val configReader: ConfigReader[IO] = ConfigReader[IO]

  def wait(driver: WebDriver): WebDriverWait = new WebDriverWait(driver, ofSeconds(20), ofMillis(500))

  val chromedriverPath: IO[String] =
    environment() match {
      case GithubActions => configReader.loadChromedriverConfig.map(_.chromedriver.path.nix)
      case _ => configReader.loadChromedriverConfig.map(_.chromedriver.path.local)
    }

  def headlessMode(chromeOptions: ChromeOptions): IO[Unit] = {
    headlessOrNonHeadless() match {
      case Headless => IO(chromeOptions.addArguments("--headless"))
      case NonHeadless => IO.unit
    }
  }

  val baseUrl =
    environment() match {
      case ProxyEnv => "http://localhost:6060"
      case GithubActions => "http://frontend:3000"
      case _ => "http://localhost:3000"
    }

  def withWebDriver[A](test: WebDriver => IO[A]): IO[A] = {
    val chromeOptions = new ChromeOptions()
    val acquire: IO[WebDriver] = {
      for {
        driverProperty <- configReader.loadChromedriverConfig.map(_.chromedriver.path.driver)
        driverLocationPath <- chromedriverPath
        _ <- IO(System.setProperty(driverProperty, driverLocationPath))
        configOptions <- configReader.loadChromedriverConfig.map(config => config.chromedriver.options)
        _ <- IO(configOptions.map(chromeOptions.addArguments(_)))
        _ <- headlessMode(chromeOptions)
      } yield {
        new ChromeDriver(chromeOptions)
      }
    }

    def release(driver: WebDriver): IO[Unit] = IO(driver.quit())

    Resource.make(acquire)(release).use(test)
  }

}
