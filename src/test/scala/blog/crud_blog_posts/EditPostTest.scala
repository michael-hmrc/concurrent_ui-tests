package blog.crud_blog_posts

import blog.constants.EditBlogPostConstants._
import cats.effect.IO
import weaver._

object EditPostTest extends SimpleIOSuite with SharedUserSteps with Selectors {

  override def maxParallelism = 1

  test("When the user creates a Blog Post and Edits the blog post, the user should see an Updated blog post'") {
    withWebDriver { driver =>
      for {
        webDriver1 <- navToCreatePostPage(driver)
        webDriver2 <- submitCreateBlogPost(webDriver1, initialEditBlogPostData)
        webDriver3 <- checkCreatedBlogPost(webDriver2, initialEditBlogPostData)
        webDriver4 <- updateBlogPost(webDriver3, initialEditBlogPostData, updatedEditBlogPostData)
        webDriver5 <- validateUpdatedBlogPost(webDriver4, updatedEditBlogPostData)
        _ <- deleteBlogPost(webDriver5, updatedEditBlogPostData)
      } yield {
        success
      }
    }
  }
}
