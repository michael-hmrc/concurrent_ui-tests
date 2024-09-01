package blog.constants

import blog.models.BlogPost

object EditBlogPostConstants {

  val editPostPageH1Content = "Edit a blog post"

  private val blogTitleContent = "Fake Blog Post Title 2"
  private val blogPostId = "blog-post-2"
  private val blogPostContent = "some content"

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

  def deletedUpdatedPostContent(title:String) = s"Blog post '$title' has been deleted"


}
