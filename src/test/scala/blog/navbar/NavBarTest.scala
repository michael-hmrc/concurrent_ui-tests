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
import scala.concurrent.duration.DurationInt

object NavBarTest extends SimpleIOSuite {

  override def maxParallelism = 3

  val sleepTime = 10.seconds

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

  test("When the user clicks the About link, they should be on the About page'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get(baseUrl + "/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        _ <- IO(aboutPageH1 shouldBe "About")
        _ <- IO.sleep(sleepTime)
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the Contact link, they should be on the Contact page'") {
    withWebDriver { driver =>

      for {
        _ <- IO(driver.get(baseUrl + "/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        contactLink: WebElement = driver.findElement(By.id("contact"))
        _ <- IO(contactLink.click())
        contactPageH1: String = driver.findElement(By.cssSelector("#root > div > div > h1")).getText
        _ <- IO.sleep(sleepTime)
      } yield {
        expect(contactPageH1 == "Contact Details")
      }
    }
  }

  test("When the user clicks the Interests link, they should be on the Interests page'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get(baseUrl + "/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        interestsLink: WebElement = driver.findElement(By.id("interests"))
        _ <- IO(interestsLink.click())
        interestsPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.pt-4 > div > h1")).getText
        _ <- IO.sleep(sleepTime)
      } yield {
        expect(interestsPageH1 == "Interests")
      }
    }
  }

  test("When the user clicks the Skills link, they should be on the Skills page'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get(baseUrl + "/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        skillsLink: WebElement = driver.findElement(By.id("skills"))
        _ <- IO(skillsLink.click())
        skillsPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.pt-4 > h1")).getText
        _ <- IO.sleep(sleepTime)
      } yield {
        expect(skillsPageH1 == "Skills")
      }
    }
  }

  test("When the user clicks the WorkLog link, they should be on the Work Log page'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get(baseUrl + "/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        workLogLink: WebElement = driver.findElement(By.id("work-log"))
        _ <- IO(workLogLink.click())
        workLogPageH1: String = driver.findElement(By.cssSelector("#root > div > div.container.mx-auto.p-4 > h1")).getText
        _ <- IO.sleep(sleepTime)
      } yield {
        expect(workLogPageH1 == "Work Log")
      }
    }
  }

  test("When the user clicks the Assets link, they should be on the Assets page'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get(baseUrl + "/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        assetsLink: WebElement = driver.findElement(By.id("assets"))
        _ <- IO(assetsLink.click())
        _ <-
          IO(wait(driver).until { driver =>
            driver.findElement(By.cssSelector("#root > div > div > h1")).isDisplayed
          })
        assetsPageH1: String = driver.findElement(By.cssSelector("#root > div > div > h1")).getText
        _ <- IO.sleep(sleepTime)
      } yield {
        expect(assetsPageH1 == "Assets")
      }
    }
  }
}
