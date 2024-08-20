package blog.crud_blog_posts

import blog.BaseSpec
import blog.PageUrls.homePageUrl
import blog.constants.CreateBlogPostConstants._
import cats.effect.IO
import org.openqa.selenium.{By, WebDriver}

trait SharedUserSteps extends BaseSpec with Selectors {

  def navToCreatePostPage(webDriver: WebDriver): IO[WebDriver] = {
    for {
      _ <- IO(webDriver.get(homePageUrl))
      _ <- IO(wait(webDriver).until { driver =>
        driver.findElement(By.cssSelector(navBarCreatePostButton)).isDisplayed
      })
      _ <- IO(webDriver.findElement(By.cssSelector(navBarCreatePostButton)).click())
      createPostPageH1 = webDriver.findElement(By.cssSelector(createPostPageH1Selector)).getText
      _ <- IO(createPostPageH1 shouldBe createPostPageH1Content)
    } yield {
      webDriver
    }
  }

  def submitBlogPost(webDriver: WebDriver): IO[WebDriver] = {
    for {
      _ <- IO(webDriver.findElement(By.cssSelector(titleFormInput)).sendKeys(blogTitleContent))
      _ <- IO(webDriver.findElement(By.cssSelector(blogPostIdFormInput)).sendKeys(blogPostId))
      _ <- IO(webDriver.findElement(By.cssSelector(createBlogPostTextArea)).sendKeys(blogPostContent))
      _ <- IO(webDriver.findElement(By.cssSelector(createBlogPostButton)).click())
    } yield {
      webDriver
    }
  }

  def checkUpdatedBlogPost(webDriver: WebDriver): IO[String] = {
    for {
      _ <- IO(webDriver.get(homePageUrl))
      _ <- IO(wait(webDriver).until { driver =>
        driver.findElement(By.cssSelector(firstBlogPostReadMeLink)).isDisplayed
      })
      _ <- IO(webDriver.findElement(By.cssSelector(firstBlogPostReadMeLink)).click())
      _ <- IO(wait(webDriver).until { driver =>
        driver.findElement(By.cssSelector(updatedBlogPostH1)).isDisplayed
      })
      updatedTitle <- IO(webDriver.findElement(By.cssSelector(updatedBlogPostH1)).getText)
      _ = updatedTitle shouldBe blogTitleContent
      _ <- IO(webDriver.findElement(By.cssSelector(deleteAllBlogPostsButton)).click())
      handleAlert <- IO(webDriver.switchTo().alert().accept())
      _ <- IO(wait(webDriver).until { driver =>
        driver.findElement(By.cssSelector(deleteMessageParagraph)).isDisplayed
      })
      delete_message <- IO(webDriver.findElement(By.cssSelector(deleteMessageParagraph)).getText)
      _ <- IO(delete_message shouldBe deletedAllBlogPostsContent)
    } yield {
      updatedTitle
    }
  }

}
