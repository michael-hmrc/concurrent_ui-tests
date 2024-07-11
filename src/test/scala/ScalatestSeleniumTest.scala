import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ScalatestSeleniumTest extends AnyFunSuite with Matchers {

  System.setProperty("webdriver.chrome.driver", "/usr/local/Caskroom/chromedriver/126.0.6478.126/chromedriver-mac-x64/chromedriver")

  def withWebDriver(test: WebDriver => Unit): Unit = {
    val options = new ChromeOptions()

    options.addArguments("--remote-allow-origins=*")
    options.addArguments("--headless")

    val driver = new ChromeDriver(options)
    try {
      test(driver)
    } finally {
      driver.quit()
    }
  }

  for (i <- 1 to 100) {

    test(s"Google title should be 'Google' $i") {
      withWebDriver { driver =>
        driver.get("https://www.google.com")
        val title = driver.getTitle
        title should be("Google")
      }
    }

    test(s"Bing title should be 'Bing' $i") {
      withWebDriver { driver =>
        driver.get("https://www.bing.com")
        val title = driver.getTitle
        title should be("Bing")
      }
    }

    test(s"Yahoo title should not be empty $i") {
      withWebDriver { driver =>
        driver.get("https://www.yahoo.com")
        val title = driver.getTitle
        title should not be empty
      }
    }
  }

  //  test("Form submission should show thank you message") {
  //    val formUrl = "https://example.com/form" // Replace with an actual form URL
  //    withWebDriver { driver =>
  //      driver.get(formUrl)
  //      val form: WebElement = driver.findElementByName("myForm") // Adjust the element locator
  //      form.findElementByName("inputField").sendKeys("test input")
  //      form.submit()
  //      // Check for form submission success message or redirection
  //      val successMessage = driver.findElementById("successMessage").getText
  //      successMessage should include("Thank you")
  //    }
  //  }
}
