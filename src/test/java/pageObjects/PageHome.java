package pageObjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import OCUtil.OCUtil;
import OpencartBase.BasePage;

public class PageHome extends BasePage {
	WebDriver driver;

	public PageHome(WebDriver driver) {
		super(driver);
	}

	// WebElements
	@FindBy(xpath = "//span[normalize-space()='My Account']")
	WebElement routeMyAcc;

	@FindBy(xpath = "//a[normalize-space()='Register']")
	WebElement routeRegister;

	@FindBy(xpath = "//a[normalize-space()='Login']")
	WebElement routeLogin;

	@FindBy(xpath = "//input[@placeholder='Search']") // For Search Product Test
	WebElement inpSearchBox;

	@FindBy(xpath = "//div[@id='search']//button[@type='button']") // For Search Product Test
	WebElement btnSearch;

	@FindBy(xpath = "//*[@id='alert']/dirv")
	WebElement msgErrorAlert;

	// Action methods
	public void clickMyAcc() {
		routeMyAcc.click();
	}

	public void clickRegister() {
		routeRegister.click();
	}

	public void clickLogin() {
		routeLogin.click();
	}

	/**
	 * For Search Product Test
	 * 
	 * @param pName
	 */
	public void enterProductName(String productName) {
		inpSearchBox.clear();
		inpSearchBox.sendKeys(productName);
	}

	/**
	 * For Search Product Test
	 */
	public void clickSearch() {
		btnSearch.click();
	}

	public boolean alertError() {
		try {
			System.out.println("**** " + msgErrorAlert.getText());
			return msgErrorAlert.isDisplayed();

		} catch (Exception e) {
			return false; // Devuelve false si no se muestra el mensaje de error
		}
	}

}
