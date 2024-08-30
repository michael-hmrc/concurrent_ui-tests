package blog.constants

import blog.models.BlogPost

object CreateBlogPostConstants {

  val createPostPageH1Content = "Create a new blog post"

  val blogTitleContent = "Fake Blog Post Title 1"
  val blogPostId = "blog-post-1"
  val blogPostContent = "some content"

  val deletedAllBlogPostsContent = "Blog post 'Fake Blog Post Title 1' has been deleted"

  val blogPostData =
    BlogPost(
      blogTitleContent,
      blogPostId,
      blogPostContent
    )

}
