package blog.crud_blog_posts

import blog.PageUrls.homePageUrl
import blog.{BaseSpec, WebDriverExtension}
import cats.effect.{IO, Resource}
import weaver.{GlobalResource, GlobalWrite}

object MyGlobalResource extends GlobalResource with BaseSpec with WebDriverExtension {

  import org.openqa.selenium.{JavascriptExecutor, WebDriver, WebElement}

  def scrollIntoView(webDriver: WebDriver, element: WebElement): Unit = {
    val jsExecutor = webDriver.asInstanceOf[JavascriptExecutor]
    jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element)
  }

  override def sharedResources(global: GlobalWrite): Resource[IO, Unit] =
    Resource.make(
      // Setup: Delete all blog posts before any test runs
      deleteAllBlogPosts().void
    )(
      // Teardown: Any cleanup after all tests have run
      _ => IO(println("Cleaning up global resource at the end"))
    )

  // Helper method to delete all blog posts
  def deleteAllBlogPosts(): IO[Unit] = {
    withWebDriver { webDriver =>
      for {
        _ <- IO(webDriver.get(homePageUrl))
        _ <- IO(scrollIntoView(webDriver, webDriver.findByCss("#root > div > main > div.flex.justify-start > div > form > div > button")))
        _ <- IO(webDriver.findByCss("#root > div > main > div.flex.justify-start > div > form > div > button").click())
        _ <- IO(webDriver.switchTo().alert().accept())
        deleteAllResponseText <- IO(webDriver.findByCss("#delete-button-response-body").getText)
        _ <- IO(assert(deleteAllResponseText == "All posts have been deleted."))
      } yield ()
    }
  }
}
