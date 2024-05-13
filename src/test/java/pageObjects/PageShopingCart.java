package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import OCUtil.OCUtil;
import OpencartBase.BasePage;

public class PageShopingCart extends BasePage {

	public PageShopingCart(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//button[contains(text(),'item(s)')]")
	WebElement btnItems;

	@FindBy(xpath = "//strong[normalize-space()='View Cart']")
	WebElement lnkViewCart;

	@FindBy(xpath = "//div[@id='shopping-cart']//td/strong[text()='Total']/following::td")
	WebElement lblTotalPrice;

	@FindBy(xpath = "//a[text()='Checkout']")
	WebElement btnCheckout;

	public void clickItemsToNavigateToCart() {
		btnItems.click();
	}

	public void clickViewCart() {
		lnkViewCart.click();
	}

	public String getTotalPrice() {
		return lblTotalPrice.getText();
	}

	public void clickOnCheckout() {
		btnCheckout.click();
	}

	public String goCheckout() throws InterruptedException {
		OCUtil.wait(1);
		clickItemsToNavigateToCart();
		clickViewCart();
		String totalPrice = getTotalPrice();
		clickOnCheckout();
		return totalPrice;
	}
}
