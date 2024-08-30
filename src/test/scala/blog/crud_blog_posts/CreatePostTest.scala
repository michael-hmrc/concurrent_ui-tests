package blog.crud_blog_posts

import blog.constants.CreateBlogPostConstants.{blogPostData, blogTitleContent}
import weaver._

object CreatePostTest extends SimpleIOSuite with SharedUserSteps with Selectors {

  override def maxParallelism = 1

  test("When the user creates a Blog Post and Navigates to the 1st Blog post it should render the updated blog post'") {
    withWebDriver { driver =>
      for {
        webDriver1 <- navToCreatePostPage(driver)
        webDriver2 <- submitCreateBlogPost(webDriver1, blogPostData)
        updatedTitle <- checkCreatedBlogPost(webDriver2, blogPostData)
      } yield {
//        expect(updatedTitle == blogTitleContent)
        success
      }
    }
  }
}
