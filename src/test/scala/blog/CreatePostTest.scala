package blog

import cats.effect._
import org.openqa.selenium._
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import weaver._

object CreatePostTest extends SimpleIOSuite with BaseSpec {

  override def maxParallelism = 1

  def navToCreatePostPage(webDriver: WebDriver): IO[WebDriver] =
    for {
      _ <- IO(webDriver.get(baseUrl + "/"))
      _ <- IO(wait(webDriver).until { driver =>
        driver.findElement(By.cssSelector("#create-blog-post")).isDisplayed
      })
      _ <- IO(webDriver.findElement(By.cssSelector("#create-blog-post")).click())
      createPostPageH1 = webDriver.findElement(By.cssSelector("#root > div > div > h1")).getText
      _ <- IO(createPostPageH1 shouldBe "Create a new blog post")
    } yield {
      webDriver
    }

  test("When the user creates a Blog Post and Navigates to the 1st Blog post it should render the updated blog post'") {
    withWebDriver { driver =>

      def submitBlogPost(webDriver: WebDriver) = {
        for {
          _ <- IO(webDriver.findElement(By.cssSelector("#root > div > div > div > form > div:nth-child(1) > label > input")).sendKeys("Fake Blog Post Title 1"))
          _ <- IO(webDriver.findElement(By.cssSelector("#root > div > div > div > form > div:nth-child(2) > label > input")).sendKeys("mikey-1"))
          _ <- IO(webDriver.findElement(By.cssSelector("#root > div > div > div > form > div.mt-4.mb-4 > label > textarea")).sendKeys("some content"))
          clickCreatePost <- IO(webDriver.findElement(By.cssSelector("#root > div > div > div > form > button")).click())
        } yield {
          webDriver
        }
      }

      def checkUpdatedBlogPost(webDriver: WebDriver): IO[String] = {
        for {
          _ <- IO(webDriver.get(baseUrl + "/"))
          _ <- IO(wait(webDriver).until { driver =>
            driver.findElement(By.cssSelector("#root > div > main > div > ul > li:nth-child(1) > div > a")).isDisplayed
          })
          clickBlogPost1ReadMeLink <- IO(webDriver.findElement(By.cssSelector("#root > div > main > div > ul > li:nth-child(1) > div > a")).click())
          refresh <- IO(webDriver.navigate().refresh())
          updatedTitle = webDriver.findElement(By.cssSelector("#root > div > main > div > div:nth-child(1) > div > h1")).getText
          _ = updatedTitle shouldBe "Hardcoded title update"
          post_id_content = webDriver.findElement(By.cssSelector("#root > div > main > div > div:nth-child(1) > div > p:nth-child(3)")).getText
          delete_blog_post <- IO(webDriver.findElement(By.cssSelector("#root > div > main > div > div:nth-child(3) > button")).click())
          handleAlert <- IO(webDriver.switchTo().alert().accept())
          delete_message = webDriver.findElement(By.cssSelector("#root > div > main > div > div:nth-child(3) > div > p")).getText
          _ <- IO(delete_message shouldBe "All posts have been deleted.")
        } yield {
          post_id_content
        }
      }

      for {
        webDriver1 <- navToCreatePostPage(driver)
        webDriver2 <- submitBlogPost(webDriver1)
        post_id_content <- checkUpdatedBlogPost(webDriver2)
      } yield {
        expect(post_id_content == "mikey-2")
      }

    }
  }
}
