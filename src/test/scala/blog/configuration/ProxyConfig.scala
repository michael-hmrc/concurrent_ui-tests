package blog.configuration

case class ProxyConfig(host: String, port: Int)

case class EnvironmentConfig(host: String, port: Int)

case class AuthMethod(
                       authType: String,
                       pkFile: Option[String],
                       username: Option[String],
                       password: Option[String]
                     )




