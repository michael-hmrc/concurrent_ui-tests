package blog.navbar

import blog.BrowserModeConfiguration.headlessOrNonHeadless
import blog.EnvironmentConfiguration.environment
import blog._
import cats.effect._
import org.openqa.selenium._
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import weaver._

object NavBarTest extends SimpleIOSuite {

  override def maxParallelism = 10

  val configReader: ConfigReader[IO] = ConfigReader[IO]

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

  test("When the user clicks the about link, they should be on the About page'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://localhost:6060/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        _ <- IO(aboutPageH1 shouldBe "About")
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 2'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://localhost:6060/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        _ <- IO(aboutPageH1 shouldBe "About")
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 3'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://localhost:6060/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        _ <- IO(aboutPageH1 shouldBe "About")
        //        _ <- IO.sleep(10.seconds)
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 4'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://localhost:6060/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        _ <- IO(aboutPageH1 shouldBe "About")
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 5'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://localhost:6060/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        _ <- IO(aboutPageH1 shouldBe "About")
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 6'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://localhost:6060/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        _ <- IO(aboutPageH1 shouldBe "About")
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 7'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://localhost:6060/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        _ <- IO(aboutPageH1 shouldBe "About")
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 8'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://localhost:6060/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        _ <- IO(aboutPageH1 shouldBe "About")
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 9'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://localhost:6060/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        _ <- IO(aboutPageH1 shouldBe "About")
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 10'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://localhost:6060/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        _ <- IO(aboutPageH1 shouldBe "About")
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }
}
