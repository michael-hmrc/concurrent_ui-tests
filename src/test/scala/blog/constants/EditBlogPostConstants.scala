package blog.constants

import blog.models.BlogPost

object EditBlogPostConstants {

  val editPostPageH1Content = "Edit a blog post"

  val blogTitleContent = "Fake Blog Post Title 2"
  val blogPostId = "blog-post-2"
  val blogPostContent = "some content"

  val initialEditBlogPostData =
    BlogPost(
      blogTitleContent,
      blogPostId,
      blogPostContent
    )

  val updatedBlogTitleContent = "Fake Updated Blog Post Title 2"
  val updatedBlogPostId = "blog-post-2"
  val updatedBlogPostContent = "updated content"

  val updatedEditBlogPostData =
    BlogPost(
      updatedBlogTitleContent,
      updatedBlogPostId,
      updatedBlogPostContent
    )

  val deletedUpdatedPostContent = "Blog post 'Fake Updated Blog Post Title 2' has been deleted"


}
