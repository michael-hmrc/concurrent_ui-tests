package blog.configuration

case class AppConfig(
                      proxy: ProxyConfig,
                      local: EnvironmentConfig,
                      qa: EnvironmentConfig,
                      staging: EnvironmentConfig,
                      production: EnvironmentConfig,
                      useHttps: Boolean,
                      authMethods: List[AuthMethod]
                    )
