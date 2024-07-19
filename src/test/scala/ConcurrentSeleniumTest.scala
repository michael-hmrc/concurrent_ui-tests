//import cats.effect._
//import cats.implicits._
//import org.openqa.selenium.WebDriver
//import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
//import weaver._
//
////object ConfiguredSuite extends weaver.TestConfig {
////  // Limit parallelism to 4
////  def maxParallelism: Int = 5
////}
//
//object ConcurrentSeleniumTest extends SimpleIOSuite {
//
//  // https://storage.googleapis.com/chrome-for-testing-public/126.0.6478.126/mac-x64/chromedriv
//
////  override def maxParallelism = 5
//
//
//  def withWebDriver[A](test: WebDriver => IO[A]): IO[A] = {
//    val acquire = IO {
//      System.setProperty("webdriver.chrome.driver", "/usr/local/Caskroom/chromedriver/126.0.6478.126/chromedriver-mac-x64/chromedriver")
//      val options = new ChromeOptions()
//      options.addArguments("--remote-allow-origins=*")
//      options.addArguments("--headless")
//      new ChromeDriver(options)
//    }
//
//    def release(driver: WebDriver): IO[Unit] = IO(driver.quit())
//
//    Resource.make(acquire)(release).use(test)
//  }
//
//  test("Google title should be 'Google'") {
//    withWebDriver { driver =>
//      IO {
//        driver.get("https://www.google.com")
//        val title = driver.getTitle
//        expect(title == "Google")
//      }
//    }
//  }
//
//  test("Concurrent Google title checks") {
//    List.fill(10)(()).parTraverse { _ =>
//      withWebDriver { driver =>
//        IO {
//          driver.get("https://www.google.com")
//          val title = driver.getTitle
//          expect(title == "Google")
//        }
//      }
//    }.map(expectations => expectations.reduce(_ and _))
//  }
//
//  test("Parallel navigation to different sites") {
//
//    def duplicateListToSize[A](list: List[A], desiredSize: Int): List[A] = {
//      val repeatedList = LazyList.continually(list).flatten.take(desiredSize).toList
//      repeatedList
//    }
//
//    // Example usage:
//    val originalList =
//      List(
//        "https://www.google.com",
//        "https://www.bing.com",
//        "https://www.yahoo.com",
//        "https://www.youtube.com"
//      )
//    val desiredSize = 300
//    val duplicatedList = duplicateListToSize(originalList, desiredSize)
//
//
//    duplicatedList.parTraverse { url =>
//      withWebDriver { driver =>
//        IO {
//          driver.get(url)
//          val title = driver.getTitle
//          expect(title.nonEmpty)
//        }
//      }
//    }.map(expectations => expectations.reduce(_ and _))
//  }
//
//  //  test("Simultaneous form submissions") {
//  //    val formUrl = "https://example.com/form" // Replace with an actual form URL
//  //
//  //    List.fill(3)(()).parTraverse { _ =>
//  //      withWebDriver { driver =>
//  //        IO {
//  //          driver.get(formUrl)
//  //          driver.get(formUrl)  #W0wltc > div
//  //          val form: WebElement = driver.findElement(By.name("myForm")) // Adjust the element locator
//  //          form.findElement(By.name("inputField")).sendKeys("test input")
//  //          form.submit()
//  //          // Check for form submission success message or redirection
//  //          val successMessage = driver.findElement(By.id("successMessage")).getText
//  //          expect(successMessage.contains("Thank you"))
//  //        }
//  //      }
//  //    }.map(expectations => expectations.reduce(_ and _))
//  //  }
//}
