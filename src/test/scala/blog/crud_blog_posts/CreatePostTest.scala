package blog.crud_blog_posts

import blog.constants.CreateBlogPostConstants.createBlogPostData
import cats.effect.{IO, Resource}
import weaver._

object CreatePostTest extends SimpleIOSuite with GlobalResourceF[IO] with SharedUserSteps with BlogSelectors {

  override def maxParallelism = 1

  override def sharedResources(global: GlobalResourceF.Write[IO]): Resource[IO, Unit] = MyGlobalResource.sharedResources(global)

  test("When the user creates a Blog Post and Navigates to the 1st Blog post it should render the updated blog post'") { sharedString =>
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
