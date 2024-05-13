package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import OpencartBase.BasePage;

public class PageMyAccount extends BasePage {

	public PageMyAccount(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h2[text()='My Account']")
	WebElement msgHeading;

	@FindBy(xpath = "//div[@class='list-group mb-3']/a[text()='Logout']")
	WebElement linkLogOut;

	public boolean isMyAccountPageExist() {
		try {
			return (msgHeading.isDisplayed());
		} catch (Exception e) {
			return (false);
		}
	}

	public void clickLogOut() {
		linkLogOut.click();
	}
}
