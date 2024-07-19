package blog

import cats.effect._
import org.openqa.selenium._
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import weaver._

import scala.concurrent.duration.DurationInt

object BlogTest extends SimpleIOSuite with BaseSpec with Urls {

  override def maxParallelism = 2

  test("When the user clicks the about link, they should be on the About page'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get(homePage))
        getHomePageHeading = driver.findElement(By.id("landing-page-name")).getText
        _ = println(getHomePageHeading)
        _ <- IO(getHomePageHeading shouldBe "Michael Yau")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div > h1")).getText
        _ <- IO(aboutPageH1 shouldBe "About")
        _ <- IO.sleep(5.seconds)
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the about link, they should be on the About page 2'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get("http://localhost:3000/"))
        getHomePageHeading = driver.findElement(By.id("landing-page-name")).getText
        _ = println(getHomePageHeading)
        _ <- IO(getHomePageHeading shouldBe "Michael Yau")
        aboutLink: WebElement = driver.findElement(By.id("about"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#root > div > div > h1")).getText
        _ <- IO(aboutPageH1 shouldBe "About")
        _ <- IO.sleep(10.seconds)
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }
}
