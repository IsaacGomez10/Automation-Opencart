package pageObjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import OpencartBase.BasePage;

public class PageAccountRegistration extends BasePage {
	WebDriver driver;

	public PageAccountRegistration(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "input-firstname")
	WebElement inpFirstName;

	@FindBy(id = "input-lastname")
	WebElement inpLastName;

	@FindBy(id = "input-email")
	WebElement inpEmail;

	@FindBy(id = "input-password")
	WebElement inpPassword;

	@FindBy(xpath = "//input[@name='agree']")
	WebElement checkPolicy;

	@FindBy(xpath = "//button[normalize-space()='Continue']")
	WebElement btnContinue;

	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;

	public void setFirstName(String firstName) {
		inpFirstName.sendKeys(firstName);
	}

	public void setLastName(String lastName) {
		inpLastName.sendKeys(lastName);
	}

	public void setEmail(String email) {
		inpEmail.sendKeys(email);
	}

	public void setPassword(String password) {
		inpPassword.sendKeys(password);
	}

	public void setPrivacyPolicy() {
		checkPolicy.click();
	}

	/**
	 * Alternative options to click any button
	 */
	public void clickContinue() {

		// Example 1
		btnContinue.click();

		// Example 2
		// btnContinue.submit();

		// Example 3
		// Actions action = new Actions(driver);
		// action.moveToElement(btnContinue).click().perform();\

		// Example 4
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		// js.executeScript("arguments[0].click();", btnContinue);

		// Example 5
		// btnContinue.sendKeys(Keys.RETURN);

		// Example 6
		// WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// wait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();

	}

	public String getConfirmationMsg() {
		try {
			return (msgConfirmation.getText());
		} catch (Exception e) {
			return (e.getMessage());
		}
	}
}
