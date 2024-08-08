package blog.navbar

import blog.BrowserModeConfiguration.headlessOrNonHeadless
import blog.EnvironmentConfiguration.environment
import blog._
import cats.effect._
import org.openqa.selenium._
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.support.ui.WebDriverWait
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import weaver._

import java.time.Duration.{ofMillis, ofSeconds}

object AssetNavBarTest extends SimpleIOSuite {

  override def maxParallelism = 1

  val configReader: ConfigReader[IO] = ConfigReader[IO]

  def wait(driver: WebDriver): WebDriverWait = new WebDriverWait(driver, ofSeconds(20), ofMillis(500))

  def withWebDriver[A](test: WebDriver => IO[A]): IO[A] = {
    val options = new ChromeOptions()
    val acquire: IO[WebDriver] = {
      for {
        driverProperty <- configReader.loadChromedriverConfig.map(_.chromedriver.path.driver)
        driverLocationPath <- environment() match {
          case Local => configReader.loadChromedriverConfig.map(_.chromedriver.path.local)
          case _ => configReader.loadChromedriverConfig.map(_.chromedriver.path.nix)
        }
        _ <- IO(System.setProperty(driverProperty, driverLocationPath))
        configOptions <- configReader.loadChromedriverConfig.map(config => config.chromedriver.options)
        _ <- IO(configOptions.map(options.addArguments(_)))
        _ <- headlessOrNonHeadless() match {
          case Headless => IO(options.addArguments("--headless"))
          case NonHeadless => IO.unit
        }
      } yield {
        new ChromeDriver(options)
      }
    }

    def release(driver: WebDriver): IO[Unit] = IO(driver.quit())

    Resource.make(acquire)(release).use(test)
  }

  val baseUrl =
    environment() match {
      case Local => "http://localhost:3000"
      case _ => "http://localhost:6060"
    }

  test("When the user clicks the Assets link, they should be on the Assets page'") {
    withWebDriver { driver =>

      def navToAssets(webDiver: WebDriver): IO[WebDriver] =
        for {
          _ <- IO(webDiver.get(baseUrl + "/"))
          getHomePageHeading = webDiver.findElement(By.cssSelector("#home")).getText
          _ <- IO(getHomePageHeading shouldBe "Home")
          assetsLink: WebElement = webDiver.findElement(By.id("assets"))
          _ <- IO(assetsLink.click())
          _ <-
            IO(wait(webDiver).until { driver =>
              webDiver.findElement(By.cssSelector("#root > div > div > h1")).isDisplayed
            })
          assetsPageH1 <- IO(webDiver.findElement(By.cssSelector("#root > div > div > h1")).getText)
          _ = assetsPageH1 shouldBe "Assets"
        } yield {
          webDiver
        }

      def buttonSteps(webDiver: WebDriver) = {
        for {
          buttonLink <- IO(webDiver.findElement(By.id("buttons")))
          _ <- IO(buttonLink.click())
          buttonPageH1 <- IO(webDiver.findElement(By.cssSelector("#root > div > div > h1")).getText)
          _ = buttonPageH1 shouldBe "Buttons"
          gradientLightUpButton <- IO(webDiver.findElement(By.cssSelector("#root > div > div > div > div:nth-child(4) > button")))
          _ = gradientLightUpButton.getText shouldBe "Gradient with Light up Button"
        } yield {
          webDiver
        }
      }

      def imagesSteps(webDiver: WebDriver): IO[String] = {
        for {
          imagesLink <- IO(webDiver.findElement(By.id("images")))
          _ <- IO(imagesLink.click())
          imagesPageH1 <- IO(webDiver.findElement(By.cssSelector("#root > div > div > h1")).getText)
        } yield {
          imagesPageH1
        }
      }


      for {
        webDriver1 <- navToAssets(driver)
        webDriver2 <- buttonSteps(webDriver1)
        imagesH1 <- imagesSteps(webDriver2)
      } yield {
        expect(imagesH1 == "Images")
      }

    }
  }
}
