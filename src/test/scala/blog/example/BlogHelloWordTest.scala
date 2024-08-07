package blog.example

import cats.effect._
import org.openqa.selenium._
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import weaver._

import scala.concurrent.duration.DurationInt

object BlogHelloWordTest extends SimpleIOSuite {

  override def maxParallelism = 10

  def withWebDriver[A](test: WebDriver => IO[A]): IO[A] = {
    val acquire = IO {
//      System.setProperty("webdriver.chrome.driver", "/usr/local/Caskroom/chromedriver/126.0.6478.126/chromedriver-mac-x64/chromedriver")
      System.setProperty("webdriver.chrome.driver", "/home/runner/.nix-profile/bin/chromedriver")
      val options = new ChromeOptions()
      options.addArguments("--remote-allow-origins=*")
      options.addArguments("--headless")
      options.addArguments("--disable-gpu")
      options.addArguments("--no-sandbox")
      options.addArguments("--disable-dev-shm-usage")
      new ChromeDriver(options)
    }

    def release(driver: WebDriver): IO[Unit] = IO(driver.quit())

    Resource.make(acquire)(release).use(test)
  }

  test("When the user clicks the about link, they should be on the About page'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://192.168.1.106:3000/"))
//        _ <- IO(driver.get("http://localhost:3000/"))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ = println(getHomePageHeading)
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        finalResult <- IO(aboutPageH1 shouldBe "About")
        _ <- IO.sleep(5.seconds)
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 2'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://192.168.1.106:3000/"))
//        _ <- IO(driver.get("http://localhost:3000/"))
                getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText

        _ = println(getHomePageHeading)
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        finalResult <- IO(aboutPageH1 shouldBe "About")
        _ <- IO.sleep(10.seconds)
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 3'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://192.168.1.106:3000/"))
//        _ <- IO(driver.get("http://localhost:3000/"))
                getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText

        _ = println(getHomePageHeading)
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        finalResult <- IO(aboutPageH1 shouldBe "About")
        _ <- IO.sleep(10.seconds)
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 4'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://192.168.1.106:3000/"))
//        _ <- IO(driver.get("http://localhost:3000/"))
                getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText

        _ = println(getHomePageHeading)
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        finalResult <- IO(aboutPageH1 shouldBe "About")
        _ <- IO.sleep(10.seconds)
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 5'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://192.168.1.106:3000/"))
//        _ <- IO(driver.get("http://localhost:3000/"))
                getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText

        _ = println(getHomePageHeading)
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        finalResult <- IO(aboutPageH1 shouldBe "About")
        _ <- IO.sleep(10.seconds)
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 6'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://192.168.1.106:3000/"))
//        _ <- IO(driver.get("http://localhost:3000/"))
                getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText

        _ = println(getHomePageHeading)
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        finalResult <- IO(aboutPageH1 shouldBe "About")
        _ <- IO.sleep(10.seconds)
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 7'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://192.168.1.106:3000/"))
//        _ <- IO(driver.get("http://localhost:3000/"))
                getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText

        _ = println(getHomePageHeading)
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        finalResult <- IO(aboutPageH1 shouldBe "About")
        _ <- IO.sleep(10.seconds)
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 8'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://192.168.1.106:3000/"))
//        _ <- IO(driver.get("http://localhost:3000/"))
                getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText

        _ = println(getHomePageHeading)
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        finalResult <- IO(aboutPageH1 shouldBe "About")
        _ <- IO.sleep(10.seconds)
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 9'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://192.168.1.106:3000/"))
//        _ <- IO(driver.get("http://localhost:3000/"))
                getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText

        _ = println(getHomePageHeading)
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        finalResult <- IO(aboutPageH1 shouldBe "About")
        _ <- IO.sleep(10.seconds)
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 10'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://192.168.1.106:3000/"))
//        _ <- IO(driver.get("http://localhost:3000/"))
                getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText

        _ = println(getHomePageHeading)
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div.flex-grow.container.mx-auto.p-5 > div > h1")).getText
        finalResult <- IO(aboutPageH1 shouldBe "About")
        _ <- IO.sleep(10.seconds)
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }
}
