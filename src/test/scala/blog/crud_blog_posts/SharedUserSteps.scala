package blog.crud_blog_posts

import blog.BaseSpec
import blog.PageUrls.homePageUrl
import blog.constants.CreateBlogPostConstants._
import blog.constants.EditBlogPostConstants.deletedUpdatedPostContent
import blog.models.BlogPost
import cats.effect.IO
import org.openqa.selenium.{By, WebDriver, WebElement}

import scala.concurrent.duration.DurationInt

trait SharedUserSteps extends BaseSpec with Selectors {

  def ioSleep(timeInSeconds: Int) = IO.sleep(timeInSeconds.seconds)

  implicit class WebDriverExtensions(webdriver: WebDriver) {

    def findByCss(selector: String): WebElement = {
      webdriver.findElement(By.cssSelector(selector))
    }

    def findById(id: String): WebElement = {
      webdriver.findElement(By.id(id))
    }

    def findByLinkText(linkText: String): WebElement = {
      webdriver.findElement(By.linkText(linkText))
    }

    def findByXPath(xpath: String): WebElement = {
      webdriver.findElement(By.xpath(xpath))
    }
  }

  def navToCreatePostPage(webDriver: WebDriver): IO[WebDriver] = {
    for {
      _ <- IO(webDriver.get(homePageUrl))
      _ <- IO(wait(webDriver).until { driver =>
        driver.findByCss(navBarCreatePostButton).isDisplayed
      })
      _ <- IO(webDriver.findByCss(navBarCreatePostButton).click())
      createPostPageH1 = webDriver.findByCss(createPostPageH1Selector).getText
      _ <- IO(createPostPageH1 shouldBe createPostPageH1Content)
    } yield {
      webDriver
    }
  }

  def submitCreateBlogPost(webDriver: WebDriver, blogPost: BlogPost): IO[WebDriver] = {
    for {
      _ <- IO(webDriver.findByCss(titleFormInput).sendKeys(blogPost.title))
      _ <- IO(webDriver.findByCss(blogPostIdFormInput).sendKeys(blogPost.postId))
      _ <- IO(webDriver.findByCss(createBlogPostTextArea).sendKeys(blogPost.content))
      _ <- IO(webDriver.findByCss(createBlogPostButton).click())
    } yield {
      webDriver
    }
  }

  def checkCreatedBlogPost(webDriver: WebDriver, blogPost: BlogPost): IO[WebDriver] = {
    for {
      _ <- IO(webDriver.get(homePageUrl))
      _ <- IO(wait(webDriver).until { driver =>
        driver.findByCss(blogPostH2Link(blogPost.title)).isDisplayed
      })
      _ <- IO(webDriver.findByCss(blogPostH2Link(blogPost.title)).click())
      _ <- IO(wait(webDriver).until { driver =>
        driver.findByCss(blogPostH1(blogPost.postId)).isDisplayed
      })
      createdPostTitle <- IO(webDriver.findByCss(blogPostH1(blogPost.postId)).getText)
      _ = createdPostTitle shouldBe blogPost.title
    } yield {
      webDriver
    }
  }

  def updateBlogPost(webDriver: WebDriver, originalBlogPost: BlogPost, updatedBlogPost: BlogPost): IO[WebDriver] = {
    for {
      _ <- IO(webDriver.get(homePageUrl))
      _ <- IO(wait(webDriver).until { driver =>
        driver.findByCss(blogPostH2Link(originalBlogPost.title)).isDisplayed
      })
      _ <- IO(webDriver.findByCss(blogPostH2Link(originalBlogPost.title)).click())
      _ <- IO(wait(webDriver).until { driver =>
        driver.findByCss(blogPostH1(originalBlogPost.postId)).isDisplayed
      })
      createdPostTitle <- IO(webDriver.findByCss(blogPostH1(originalBlogPost.postId)).getText)
      _ = createdPostTitle shouldBe originalBlogPost.title
      editPostButtonClick <- IO(webDriver.findByCss(editPostButton()).click())
      _ <- IO(wait(webDriver).until { driver =>
        driver.findByCss("#edit-a-blog-post").isDisplayed
      })
      editBlogPostPageH1 <- IO(webDriver.findByCss("#edit-a-blog-post").getText)
      _ = editBlogPostPageH1 shouldBe "Edit a blog post"
      _ <- IO(wait(webDriver).until { driver =>
        driver.findByCss("#edit-blog-post-text-title").isDisplayed
      })
      _ <- IO(webDriver.findByCss("#edit-blog-post-text-title").clear())
      _ <- IO(webDriver.findByCss("#edit-blog-post-text-title").sendKeys(updatedBlogPost.title))
      _ <- IO(webDriver.findByCss("#edit-blog-post-text-area").clear())
      _ <- IO(webDriver.findByCss("#edit-blog-post-text-area").sendKeys(updatedBlogPost.content))
      _ <- IO(webDriver.findByCss("#edit-blog-post").click())
    } yield {
      webDriver
    }
  }

  def validateUpdatedBlogPost(webDriver: WebDriver, updatedBlogPost: BlogPost): IO[WebDriver] = {
    for {
      _ <- IO(webDriver.get(homePageUrl))
      _ <- IO(wait(webDriver).until { driver =>
        driver.findByCss(blogPostH2Link(updatedBlogPost.title)).isDisplayed
      })
      _ <- IO(webDriver.findByCss(blogPostH2Link(updatedBlogPost.title)).click())
      _ <- IO(wait(webDriver).until { driver =>
        driver.findByCss(blogPostH1(updatedBlogPost.postId)).isDisplayed
      })
      createdPostTitle <- IO(webDriver.findByCss(blogPostH1(updatedBlogPost.postId)).getText)
      _ <- IO(createdPostTitle shouldBe updatedBlogPost.title)
    } yield {
      webDriver
    }
  }

  def deleteBlogPost(webDriver: WebDriver, blogPost: BlogPost): IO[WebDriver] = {
    for {
      createdPostTitle <- IO(webDriver.findByCss(blogPostH1(blogPost.postId)).getText)
      _ <- IO(createdPostTitle shouldBe blogPost.title)
      _ <- IO(webDriver.findByCss(deleteSingleBlogPostsButton).click())
      handleAlert <- IO(webDriver.switchTo().alert().accept())
      _ <- IO(wait(webDriver).until { driver =>
        driver.findByCss(deleteMessageParagraph).isDisplayed
      })
      delete_message <- IO(webDriver.findByCss(deleteMessageParagraph).getText)
      _ <- IO(delete_message shouldBe deletedUpdatedPostContent(blogPost.title))
    } yield {
      webDriver
    }
  }

}
