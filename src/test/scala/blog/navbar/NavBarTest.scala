package blog.navbar

import blog.PageUrls.homePageUrl
import blog._
import cats.effect._
import org.openqa.selenium._
import weaver._

object NavBarTest extends SimpleIOSuite with BaseSpec {

  override def maxParallelism = 3

  test("When the user clicks the About link, they should be on the About page'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get(homePageUrl))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        aboutLink: WebElement = driver.findElement(By.id("about-me"))
        _ <- IO(aboutLink.click())
        aboutPageH1: String = driver.findElement(By.cssSelector("#about")).getText
        _ <- IO(aboutPageH1 shouldBe "About")
      } yield {
        expect(aboutPageH1 == "About")
      }
    }
  }

  test("When the user clicks the Contact link, they should be on the Contact page'") {
    withWebDriver { driver =>

      for {
        _ <- IO(driver.get(homePageUrl))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        contactLink: WebElement = driver.findElement(By.id("contact"))
        _ <- IO(contactLink.click())
        contactPageH1: String = driver.findElement(By.cssSelector("#contacts-page")).getText
      } yield {
        expect(contactPageH1 == "Contact Details")
      }
    }
  }

  test("When the user clicks the Interests link, they should be on the Interests page'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get(homePageUrl))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        interestsLink: WebElement = driver.findElement(By.id("interests"))
        _ <- IO(interestsLink.click())
        interestsPageH1: String = driver.findElement(By.cssSelector("#interests")).getText
      } yield {
        expect(interestsPageH1 == "Interests")
      }
    }
  }

  test("When the user clicks the Skills link, they should be on the Skills page'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get(homePageUrl))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        skillsLink: WebElement = driver.findElement(By.id("skills"))
        _ <- IO(skillsLink.click())
        skillsPageH1: String = driver.findElement(By.cssSelector("#skills")).getText
      } yield {
        expect(skillsPageH1 == "Skills")
      }
    }
  }

  test("When the user clicks the WorkLog link, they should be on the Work Log page'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get(homePageUrl))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        workLogLink: WebElement = driver.findElement(By.id("worklog"))
        _ <- IO(workLogLink.click())
        workLogPageH1: String = driver.findElement(By.cssSelector("#worklog")).getText
      } yield {
        expect(workLogPageH1 == "Work Log")
      }
    }
  }

  test("When the user clicks the Assets link, they should be on the Assets page'") {
    withWebDriver { driver =>
      for {
        _ <- IO(driver.get(homePageUrl))
        getHomePageHeading = driver.findElement(By.cssSelector("#home")).getText
        _ <- IO(getHomePageHeading shouldBe "Home")
        assetsLink: WebElement = driver.findElement(By.id("assets"))
        _ <- IO(assetsLink.click())
        _ <-
          IO(wait(driver).until { driver =>
            driver.findElement(By.cssSelector("#root > div > div > h1")).isDisplayed
          })
        assetsPageH1: String = driver.findElement(By.cssSelector("#root > div > div > h1")).getText
      } yield {
        expect(assetsPageH1 == "Assets")
      }
    }
  }
}
