package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import OCUtil.OCUtil;
import OpencartBase.BasePage;

public class PageCheckout extends BasePage {

	public PageCheckout(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='input-shipping-firstname']")
	WebElement inpFirstName;

	@FindBy(xpath = "//input[@id='input-shipping-lastname']")
	WebElement inpLastName;

	@FindBy(xpath = "//input[@id='input-shipping-address-1']")
	WebElement inpAddress1;

	@FindBy(xpath = "//input[@id='input-shipping-address-2']")
	WebElement inpAddress2;

	@FindBy(xpath = "//input[@id='input-shipping-city']")
	WebElement inpCity;

	@FindBy(xpath = "//input[@id='input-shipping-postcode']")
	WebElement inpZipCode;

	@FindBy(xpath = "//select[@id='input-shipping-country']")
	WebElement slcCountry;

	@FindBy(xpath = "//select[@id='input-shipping-zone']")
	WebElement slcRegionState;

	@FindBy(xpath = "//*[@id='input-shipping-new']")
	WebElement radioNewAddress;

	@FindBy(xpath = "//textarea[@name='comment']")
	WebElement txtAreaComment;

	@FindBy(xpath = "//*[@id='button-shipping-address']")
	WebElement btnContinue;

	@FindBy(xpath = "//button[@id='button-shipping-methods']")
	WebElement btnAfterShippingAddress;

	@FindBy(xpath = "//input[@id='input-shipping-method-flat-flat']")
	WebElement radioShippingOption;

	@FindBy(xpath = "//*[@id='button-shipping-method']")
	WebElement btnBeforeShippingAddress;

	@FindBy(xpath = "//button[@id='button-payment-methods']")
	WebElement btnAfterPaymentMethod;

	@FindBy(xpath = "//input[@id='input-payment-method-cod-cod']")
	WebElement radioPaymentOption;

	@FindBy(xpath = "//*[@id='button-payment-method']")
	WebElement btnBeforePaymentMethod;

	@FindBy(xpath = "//input[@id='input-default-yes']")
	WebElement radioAcceptTerms;

	@FindBy(xpath = "//*[@id='checkout-confirm']//td/strong[text()='Total']/following::td")
	WebElement lblTotalPrice;

	@FindBy(xpath = "//button[text()='Confirm Order']")
	WebElement btnConfirmOrder;

	@FindBy(xpath = "//*[@id='content']/h1")
	WebElement msgOrderConfirm;
	
	@FindBy(xpath = "//*[text()='Continue']")
	WebElement btnFinishOrder;

	public void setFirstName(String firstName) {
		inpFirstName.sendKeys(firstName);
	}

	public void setLastName(String lastName) {
		inpLastName.sendKeys(lastName);
	}

	public void setAddress1(String address1) {
		inpAddress1.sendKeys(address1);
	}

	public void setAddress2(String address2) {
		inpAddress2.sendKeys(address2);
	}

	public void setCity(String city) {
		inpCity.sendKeys(city);
	}

	public void setZipCode(String zipCode) {
		inpZipCode.sendKeys(zipCode);
	}

	public void clickOnContinue() {
		btnContinue.click();
	}

	public void setCountry(String Country) throws InterruptedException {
		OCUtil.wait(1);
		new Select(slcCountry).selectByVisibleText(Country);
	}

	public void setState(String State) throws InterruptedException {
		OCUtil.wait(1);
		new Select(slcRegionState).selectByVisibleText(State);
	}

	public void clickOnContinueAfterDeliveryMethod() {
		btnAfterShippingAddress.click();
	}

	public void selectTermsAndConditions() {
		radioAcceptTerms.click();
	}

	public void clickOnContinueAfterPaymentMethod() {
		btnAfterPaymentMethod.click();
	}

	public String getTotalPriceBeforeConfOrder() {
		return lblTotalPrice.getText(); // $207.00

	}

	public void clickOnConfirmOrder() {
		btnConfirmOrder.click();
	}

	public void clickRadioNewAddress() {
		radioNewAddress.click();
	}

	public void addComments(String comment) {
		txtAreaComment.sendKeys(comment);
	}
	
	public void continueHomePage() {
		btnFinishOrder.click();
	}

	public boolean isOrderPlaced(WebDriver driver) throws InterruptedException {
		try {
			OCUtil.wait(2);
			//driver.switchTo().alert().accept();
			btnConfirmOrder.click();
			OCUtil.wait(3);
			if (msgOrderConfirm.getText().equals("Your order has been placed!"))
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public void newAddress() {
		System.out.println("Add a new address");
		if (radioNewAddress.isDisplayed()) {
			clickRadioNewAddress();
		}
	}

	public void choseShippingMethod() throws InterruptedException {
		OCUtil.wait(2);
		btnAfterShippingAddress.click();
		radioShippingOption.click();
		btnBeforeShippingAddress.click();
	}

	public void chosePaymentMethod() throws InterruptedException {
		OCUtil.wait(2);
		btnAfterPaymentMethod.click();
		radioPaymentOption.click();
		btnBeforePaymentMethod.click();
	}

}
