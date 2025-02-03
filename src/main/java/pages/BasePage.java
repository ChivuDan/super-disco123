package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        // Parse the timeout from config.properties
        int timeout = Integer.parseInt(ConfigReader.getProperty("timeout"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        PageFactory.initElements(driver, this);
    }


    /**
     * Waits until the given element is visible on the page.
     * @param element the WebElement to wait for.
     */
    protected void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits until the given element is clickable and then clicks it.
     * @param element the WebElement to click.
     */
    protected void click(WebElement element) {
        waitForElementToBeVisible(element);
        waitForElementToBeClickable(element);
        element.click();
    }

    /**
     * Waits until the given element is visible, clears its current text, and sends the provided text.
     * @param element the WebElement to send keys to.
     * @param text the text to enter.
     */
    protected void enterText(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Waits until the given element is clickable.
     * @param element the WebElement to wait for.
     */
    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
