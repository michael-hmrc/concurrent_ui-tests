package blog

import cats.effect.{IO, Resource}
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}

trait BaseSpec {

  def withWebDriver[A](test: WebDriver => IO[A]): IO[A] = {
    val acquire = IO {
      System.setProperty("webdriver.chrome.driver", "/usr/local/Caskroom/chromedriver/126.0.6478.126/chromedriver-mac-x64/chromedriver")
      val options = new ChromeOptions()
      options.addArguments("--remote-allow-origins=*")
      options.addArguments("--headless")
      new ChromeDriver(options)
    }

    def release(driver: WebDriver): IO[Unit] = IO(driver.quit())

    Resource.make(acquire)(release).use(test)
  }


}
