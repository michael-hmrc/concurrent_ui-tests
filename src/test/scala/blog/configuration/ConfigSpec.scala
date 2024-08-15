package blog.configuration

// src/test/scala/AppConfigSpec.scala

import blog.ConfigReader
import blog.configuration.models.PathConfig
import cats.effect.IO
import weaver.SimpleIOSuite

object ConfigSpec extends SimpleIOSuite {

  val configReader = ConfigReader[IO]

  test("load test configurations correctly") {

    for {
      config <- configReader.loadAppConfig
    } yield {
      expect(config.proxy.host == "localhost") &&
        expect(config.proxy.port == 6060) &&
        expect(config.qa.host == "example.com") &&
        expect(config.qa.port == 1111) &&
        expect(config.staging.host == "example.com") &&
        expect(config.staging.port == 2222) &&
        expect(config.production.host == "example.com") &&
        expect(config.production.port == 3333) &&
        expect(config.useHttps) &&
        expect(config.authMethods.length == 2) &&
        expect(config.authMethods.head.authType == "private-key") &&
        expect(config.authMethods.head.pkFile.contains("/home/user/myauthkey")) &&
        expect(config.authMethods.head.username.isEmpty) &&
        expect(config.authMethods.head.password.isEmpty) &&
        expect(config.authMethods(1).authType == "login") &&
        expect(config.authMethods(1).username.contains("pureconfig")) &&
        expect(config.authMethods(1).password.contains("12345678")) &&
        expect(config.authMethods(1).pkFile.isEmpty)
    }
  }

  test("load chromedriver configurations correctly") {

    for {
      config <- configReader.loadChromedriverConfig
    } yield {
      expect(config.chromedriver.options == List("--remote-allow-origins=*", "--disable-gpu", "--window-size=1920,1080", "--no-sandbox", "--disable-dev-shm-usage")) &&
        expect(config.chromedriver.path == PathConfig("webdriver.chrome.driver", "/usr/local/Caskroom/chromedriver/126.0.6478.126/chromedriver-mac-x64/chromedriver", "/home/runner/.nix-profile/bin/chromedriver", "/usr/bin/chromedriver"))
    }
  }
}
