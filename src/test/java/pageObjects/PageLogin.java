package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import OpencartBase.BasePage;

public class PageLogin extends BasePage {

	public PageLogin(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "input-email")
	WebElement inpEmailAddress;
	
	
	@FindBy(id = "input-password")
	WebElement inpPassword;
	
	@FindBy(xpath = "//button[normalize-space()='Login']")
	WebElement btnLogin;

	public void setEmail(String email) {
		inpEmailAddress.sendKeys(email);
	}
	
	public void setPassword(String password) {
		inpPassword.sendKeys(password);
	}
	
	public void clickLogin() {
		btnLogin.click();
	} 
}
