package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SignUpPage extends BasePage {
    private static final String SIGNUP_URL = "https://circula-qa-challenge.vercel.app/users/sign_up";

    // JavaScript chain for accessing the "Accept All" button inside the shadow DOM.
    private static final String ACCEPT_BUTTON_JS_CHAIN =
            "return document.querySelector('#usercentrics-root') " +
                    "  && document.querySelector('#usercentrics-root').shadowRoot " +
                    "  ? document.querySelector('#usercentrics-root').shadowRoot" +
                    ".querySelector('#uc-center-container > div.sc-kdBSHD.hwjBDA > div > div > div > button.sc-dcJsrY.eGzzjT') " +
                    "  : null;";

    /**
     * Constructor initializes the WebDriver and the PageFactory elements.
     * @param driver the WebDriver instance.
     */
    public SignUpPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Navigates to the sign-up page.
     */
    public void openSignUpPage() {
        driver.get(SIGNUP_URL);
    }

    // ================================
    // Step 1: Cookie Consent and Login
    // ================================

    @FindBy(xpath = "//button[@data-testid='uc-accept-all-button']")
    private WebElement acceptCookiesButton; // Not used directly since the element is inside a shadow DOM

    /**
     * Clicks the "Accept All" cookies button located inside a shadow DOM.
     */
    public void acceptCookies() {
        WebElement acceptButton = wait.until(new ExpectedCondition<>() {
            @Override
            public WebElement apply(WebDriver driver) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                return (WebElement) js.executeScript(ACCEPT_BUTTON_JS_CHAIN);
            }
            @Override
            public String toString() {
                return "shadow element returned by JS chain: " + ACCEPT_BUTTON_JS_CHAIN;
            }
        });
        wait.until(ExpectedConditions.elementToBeClickable(acceptButton));
        acceptButton.click();
    }

    @FindBy(xpath = "//input[@type='email' and @name='email']")
    private WebElement workEmailInput;

    /**
     * Enters the work email address.
     * @param email the email to enter.
     */
    public void enterWorkEmail(String email) {
        enterText(workEmailInput, email);
    }

    @FindBy(xpath = "//input[@type='password' and @name='password']")
    private WebElement passwordInput;

    /**
     * Enters the password.
     * @param password the password to enter.
     */
    public void enterPassword(String password) {
        enterText(passwordInput, password);
    }

    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Try for free')]")
    private WebElement tryForFreeButton;

    /**
     * Clicks the "Try for free" button.
     */
    public void clickTryForFree() {
        click(tryForFreeButton);
    }

    @FindBy(xpath = "//input[@type='checkbox' and @name='acceptTos']")
    private WebElement tosCheckbox;

    /**
     * Ticks the Terms of Service checkbox.
     */
    public void acceptTermsAndConditions() {
        if (!tosCheckbox.isSelected()) {
            try {
                click(tosCheckbox);
            } catch (ElementClickInterceptedException e) {
                // Use JavaScript click as a fallback if an overlay interferes.
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tosCheckbox);
            }
        }
    }

    // ================================
    // Step 2: Personal Details
    // ================================

    @FindBy(xpath = "//input[@name='firstname']")
    private WebElement firstNameInput;

    /**
     * Enters the first name.
     * @param firstName the first name to enter.
     */
    public void enterFirstName(String firstName) {
        enterText(firstNameInput, firstName);
    }

    @FindBy(xpath = "//input[@name='lastname']")
    private WebElement lastNameInput;

    /**
     * Enters the last name.
     * @param lastName the last name to enter.
     */
    public void enterLastName(String lastName) {
        enterText(lastNameInput, lastName);
    }

    @FindBy(xpath = "//input[@name='phoneNumber']")
    private WebElement phoneNumberInput;

    /**
     * Enters the phone number.
     * @param phoneNumber the phone number to enter.
     */
    public void enterPhoneNumber(String phoneNumber) {
        enterText(phoneNumberInput, phoneNumber);
    }

    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Next step')]")
    private WebElement nextStepButton;

    /**
     * Clicks the "Next step" button to proceed.
     */
    public void clickNextStep() {
        click(nextStepButton);
    }

    // ================================
    // Step 3: Organization & Country
    // ================================

    @FindBy(xpath = "//input[@name='organizationName']")
    private WebElement organizationNameInput;

    /**
     * Enters the organization name (or country name if applicable).
     * @param organizationName the organization name to enter.
     */
    public void enterOrganizationName(String organizationName) {
        enterText(organizationNameInput, organizationName);
    }

    // Country Selection Using Two Approaches:

    // Option 1: Using a dropdown element (if applicable)
    @FindBy(xpath = "//input[@name='country']")
    private WebElement countryInput;

    @FindBy(xpath = "//li//div[normalize-space()='Sweden']")
    private WebElement swedenOption;

    /**
     * Opens the country dropdown by clicking on the input field.
     */
    public void openCountryDropdown() {
        click(countryInput);
    }

    /**
     * Selects the country from the dropdown.
     * Currently, this implementation clicks the "Sweden" option.
     * @param countryName the country to select (for future dynamic selection).
     */
    public void selectCountry(String countryName) {
        // For a dynamic approach, you might build an XPath using the countryName parameter.
        // For now, we directly click the swedenOption.
        click(swedenOption);
    }

    // Option 2: Direct input and key press (preferred if the field supports typing)
    /**
     * Clears the country input field, sends the specified text, and presses Enter.
     * @param text the country text to input.
     */
    public void sendKeysToCountryInput(String text) {
        wait.until(ExpectedConditions.visibilityOf(countryInput));
        // Clear using both clear() and JavaScript for robustness
        countryInput.clear();
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", countryInput);
        wait.until(driver -> countryInput.getAttribute("value").isEmpty());
        countryInput.sendKeys(text);
        countryInput.sendKeys(Keys.ENTER);
    }

    /**
     * Returns the current value of the country input field.
     * @return the selected country.
     */
    public String getSelectedCountry() {
        return countryInput.getAttribute("value");
    }
}
