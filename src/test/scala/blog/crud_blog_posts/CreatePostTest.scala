package blog.crud_blog_posts

import blog.constants.CreateBlogPostConstants.blogTitleContent
import weaver._

object CreatePostTest extends SimpleIOSuite with SharedUserSteps with Selectors {

  override def maxParallelism = 1

  test("When the user creates a Blog Post and Navigates to the 1st Blog post it should render the updated blog post'") {
    withWebDriver { driver =>
      for {
        webDriver1 <- navToCreatePostPage(driver)
        webDriver2 <- submitBlogPost(webDriver1)
        updatedTitle <- checkUpdatedBlogPost(webDriver2)
      } yield {
        expect(updatedTitle == blogTitleContent)
      }
    }
  }
}
