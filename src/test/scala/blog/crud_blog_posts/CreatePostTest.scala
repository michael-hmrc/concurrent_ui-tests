package blog.crud_blog_posts

import blog.constants.CreateBlogPostConstants.createBlogPostData
import weaver._

object CreatePostTest extends SimpleIOSuite with SharedUserSteps with Selectors {

  override def maxParallelism = 1

  test("When the user creates a Blog Post and Navigates to the 1st Blog post it should render the updated blog post'") {
    withWebDriver { driver =>
      for {
        webDriver1 <- navToCreatePostPage(driver)
        webDriver2 <- submitCreateBlogPost(webDriver1, createBlogPostData)
        webDriver <- checkCreatedBlogPost(webDriver2, createBlogPostData)
        _ <- deleteBlogPost(webDriver, createBlogPostData)
      } yield {
        success
      }
    }
  }
}
