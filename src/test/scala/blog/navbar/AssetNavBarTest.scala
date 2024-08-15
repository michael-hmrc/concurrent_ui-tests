package blog.navbar

import blog.PageUrls.homePageUrl
import blog._
import cats.effect._
import org.openqa.selenium._
import weaver._

object AssetNavBarTest extends SimpleIOSuite with BaseSpec {

  override def maxParallelism = 1

  def navToAssets(webDiver: WebDriver): IO[WebDriver] =
    for {
      _ <- IO(webDiver.get(homePageUrl))
      getHomePageHeading = webDiver.findElement(By.cssSelector("#home")).getText
      _ <- IO(getHomePageHeading shouldBe "Home")
      assetsLink: WebElement = webDiver.findElement(By.id("assets"))
      _ <- IO(assetsLink.click())
      _ <-
        IO(wait(webDiver).until { driver =>
          driver.findElement(By.cssSelector("#root > div > div > h1")).isDisplayed
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

  test("When the user navigates to the Images page, they should be on the Images page'") {
    withWebDriver { driver =>

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
