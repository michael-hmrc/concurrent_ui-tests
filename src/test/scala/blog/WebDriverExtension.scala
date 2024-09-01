package blog

import org.openqa.selenium.{By, WebDriver, WebElement}

trait WebDriverExtension {

  implicit class WebDriverExtensions(webdriver: WebDriver) {

    def findByCss(selector: String): WebElement = {
      webdriver.findElement(By.cssSelector(selector))
    }

    def findById(id: String): WebElement = {
      webdriver.findElement(By.id(id))
    }

    def findByLinkText(linkText: String): WebElement = {
      webdriver.findElement(By.linkText(linkText))
    }

    def findByXPath(xpath: String): WebElement = {
      webdriver.findElement(By.xpath(xpath))
    }
  }
}
