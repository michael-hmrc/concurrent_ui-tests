package blog.constants

import blog.models.BlogPost

object CreateBlogPostConstants {

  val createPostPageH1Content = "Create a new blog post"

  private val blogTitleContent = "Fake Blog Post Title 1"
  private val blogPostId = "blog-post-1"
  private val blogPostContent = "some content"

  val deletedAllBlogPostsContent = "Blog post 'Fake Blog Post Title 1' has been deleted"

  val createBlogPostData: BlogPost =
    BlogPost(
      blogTitleContent,
      blogPostId,
      blogPostContent
    )

}
