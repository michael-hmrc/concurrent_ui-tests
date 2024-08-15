//package blog.crud_blog_posts
//
//import blog.constants.CreateBlogPostConstants.blogPostId
//import weaver._
//
//object EditBlogPostTest extends SimpleIOSuite with SharedUserSteps {
//
//  override def maxParallelism = 1
//
//  test("The user should be able to edit a created blog post'") {
//    withWebDriver { driver =>
//      for {
//        webDriver1 <- navToCreatePostPage(driver)
//        webDriver2 <- submitBlogPost(webDriver1)
//        post_id_content <- checkUpdatedBlogPost(webDriver2)
//      } yield {
//        expect(post_id_content == blogPostId)
//      }
//    }
//  }
//}
