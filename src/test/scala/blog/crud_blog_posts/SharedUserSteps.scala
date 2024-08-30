package blog.crud_blog_posts

import blog.BaseSpec
import blog.PageUrls.homePageUrl
import blog.constants.CreateBlogPostConstants._
import blog.constants.EditBlogPostConstants.deletedUpdatedPostContent
import blog.models.BlogPost
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

  def submitCreateBlogPost(webDriver: WebDriver, blogPost: BlogPost): IO[WebDriver] = {
    for {
      _ <- IO(webDriver.findElement(By.cssSelector(titleFormInput)).sendKeys(blogPost.title))
      _ <- IO(webDriver.findElement(By.cssSelector(blogPostIdFormInput)).sendKeys(blogPost.postId))
      _ <- IO(webDriver.findElement(By.cssSelector(createBlogPostTextArea)).sendKeys(blogPost.content))
      _ <- IO(webDriver.findElement(By.cssSelector(createBlogPostButton)).click())
    } yield {
      webDriver
    }
  }

  def checkCreatedBlogPost(webDriver: WebDriver, blogPost: BlogPost): IO[WebDriver] = {
    for {
      _ <- IO(webDriver.get(homePageUrl))
      _ <- IO(wait(webDriver).until { driver =>
        driver.findElement(By.cssSelector(blogPostH2Link(blogPost.title))).isDisplayed
      })
      _ <- IO(webDriver.findElement(By.cssSelector(blogPostH2Link(blogPost.title))).click())
      _ <- IO(wait(webDriver).until { driver =>
        driver.findElement(By.cssSelector(blogPostH1(blogPost.postId))).isDisplayed
      })
      createdPostTitle <- IO(webDriver.findElement(By.cssSelector(blogPostH1(blogPost.postId))).getText)
      _ = createdPostTitle shouldBe blogPost.title
    } yield {
      webDriver
    }
  }

  def updateBlogPost(webDriver: WebDriver, originalBlogPost: BlogPost, updatedBlogPost: BlogPost): IO[WebDriver] = {
    for {
      _ <- IO(webDriver.get(homePageUrl))
      _ <- IO(wait(webDriver).until { driver =>
        driver.findElement(By.cssSelector(blogPostH2Link(originalBlogPost.title))).isDisplayed
      })
      _ <- IO(webDriver.findElement(By.cssSelector(blogPostH2Link(originalBlogPost.title))).click())
      _ <- IO(wait(webDriver).until { driver =>
        driver.findElement(By.cssSelector(blogPostH1(originalBlogPost.postId))).isDisplayed
      })
      createdPostTitle <- IO(webDriver.findElement(By.cssSelector(blogPostH1(originalBlogPost.postId))).getText)
      _ = createdPostTitle shouldBe originalBlogPost.title
      editPostButtonClick <- IO(webDriver.findElement(By.cssSelector(editPostButton())).click())
      _ <- IO(wait(webDriver).until { driver =>
        driver.findElement(By.cssSelector("#edit-a-blog-post")).isDisplayed
      })
      editBlogPostPageH1 <- IO(webDriver.findElement(By.cssSelector("#edit-a-blog-post")).getText)
      _ = editBlogPostPageH1 shouldBe "Edit a blog post"
      _ <- IO(wait(webDriver).until { driver =>
        driver.findElement(By.cssSelector("#edit-blog-post-text-title")).isDisplayed
      })
      _ <- IO(webDriver.findElement(By.cssSelector("#edit-blog-post-text-title")).clear())
      _ <- IO(webDriver.findElement(By.cssSelector("#edit-blog-post-text-title")).sendKeys(updatedBlogPost.title))
      _ <- IO(webDriver.findElement(By.cssSelector("#edit-blog-post-text-area")).clear())
      _ <- IO(webDriver.findElement(By.cssSelector("#edit-blog-post-text-area")).sendKeys(updatedBlogPost.content))
      _ <- IO(webDriver.findElement(By.cssSelector("#edit-blog-post")).click())
    } yield {
      webDriver
    }
  }

  def validateUpdatedBlogPost(webDriver: WebDriver, updatedBlogPost: BlogPost): IO[WebDriver] = {
    for {
      _ <- IO(webDriver.get(homePageUrl))
      _ <- IO(wait(webDriver).until { driver =>
        driver.findElement(By.cssSelector(blogPostH2Link(updatedBlogPost.title))).isDisplayed
      })
      _ <- IO(webDriver.findElement(By.cssSelector(blogPostH2Link(updatedBlogPost.title))).click())
      _ <- IO(wait(webDriver).until { driver =>
        driver.findElement(By.cssSelector(blogPostH1(updatedBlogPost.postId))).isDisplayed
      })
      createdPostTitle <- IO(webDriver.findElement(By.cssSelector(blogPostH1(updatedBlogPost.postId))).getText)
      _ <- IO(createdPostTitle shouldBe updatedBlogPost.title)
    } yield {
      webDriver
    }
  }

  def deleteBlogPost(webDriver: WebDriver, blogPost: BlogPost): IO[WebDriver] = {
    for {
      createdPostTitle <- IO(webDriver.findElement(By.cssSelector(blogPostH1(blogPost.postId))).getText)
      _ <- IO(createdPostTitle shouldBe blogPost.title)
      _ <- IO(webDriver.findElement(By.cssSelector(deleteSingleBlogPostsButton)).click())
      handleAlert <- IO(webDriver.switchTo().alert().accept())
      _ <- IO(wait(webDriver).until { driver =>
        driver.findElement(By.cssSelector(deleteMessageParagraph)).isDisplayed
      })
      delete_message <- IO(webDriver.findElement(By.cssSelector(deleteMessageParagraph)).getText)
      _ <- IO(delete_message shouldBe deletedUpdatedPostContent)
    } yield {
      webDriver
    }
  }

}
