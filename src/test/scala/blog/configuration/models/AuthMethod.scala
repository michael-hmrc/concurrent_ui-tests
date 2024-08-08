package blog.configuration.models

case class AuthMethod(
                       authType: String,
                       pkFile: Option[String],
                       username: Option[String],
                       password: Option[String]
                     )
