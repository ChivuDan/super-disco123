package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.SignUpPage;
import utils.ConfigReader;
import utils.DriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignUpTest {
    private WebDriver driver;
    private SignUpPage signUpPage;

    @BeforeEach
    public void setUp() {
        driver = DriverManager.getDriver();
        signUpPage = new SignUpPage(driver);
    }

    @Test
    public void testCompleteSignUpProcessAndCountrySelection() {
        // Step 1: Open page, accept cookies, and enter email/password
        signUpPage.openSignUpPage();
        signUpPage.acceptCookies();
        signUpPage.enterWorkEmail(ConfigReader.getProperty("test_email"));
        signUpPage.enterPassword(ConfigReader.getProperty("test_password"));
        signUpPage.acceptTermsAndConditions();
        signUpPage.clickTryForFree();

        // Step 2: Fill in first name, last name, and phone number, then proceed
        signUpPage.enterFirstName("John");
        signUpPage.enterLastName("Doe");
        signUpPage.enterPhoneNumber("1234567890");
        signUpPage.clickNextStep();

        // Step 3: Fill in organization name (or any required field for this step)
        signUpPage.enterOrganizationName("Some Organization");

        // Step 4: Open country dropdown and select "Sweden"

        signUpPage.sendKeysToCountryInput("Sweden");

        // Assert that the country dropdown now shows "Sweden"
        String selectedCountry = signUpPage.getSelectedCountry();
        assertEquals("Sweden", selectedCountry, "The country should be Sweden after selection.");
    }

    @AfterEach
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
