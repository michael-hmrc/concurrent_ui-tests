package blog

import blog.EnvironmentConfiguration.environment

object PageUrls {

  val baseUrl =
    environment() match {
      case ProxyEnv => "http://localhost:6060"
      case GithubActions => "http://frontend:3000"
      case _ => "http://localhost:3000"
    }

  val homePageUrl = baseUrl + "/"

}
