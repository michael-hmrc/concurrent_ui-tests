package blog.configuration


case class PathConfig(
                       driver: String,
                       local: String,
                       nix: String
                     )

case class ChromeDriver(
                         options: List[String],
                         path: PathConfig
                       )

case class ChromeDriverConfiguration(
                                      chromedriver: ChromeDriver
                                    )


