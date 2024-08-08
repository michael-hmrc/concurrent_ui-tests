package blog.configuration

import blog.configuration.models.{AuthMethod, EnvironmentConfig}

case class AppConfig(
                      proxy: ProxyConfig,
                      local: EnvironmentConfig,
                      qa: EnvironmentConfig,
                      staging: EnvironmentConfig,
                      production: EnvironmentConfig,
                      useHttps: Boolean,
                      authMethods: List[AuthMethod]
                    )
