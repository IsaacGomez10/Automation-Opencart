package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import OpencartBase.BasePage;

public class PageSearch extends BasePage {

	public PageSearch(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//*[@id='content']/div[5]//img")
	List<WebElement> searchProducts;

	@FindBy(name = "quantity")
	WebElement inpQuantity;

	@FindBy(xpath = "//button[@id='button-cart']")
	WebElement btnAddToCart;

	@FindBy(xpath = "//div[contains(text(),'Success: You have added')]")
	WebElement msgConfirmation;

	public boolean isProductExist(String productName) {
		boolean flag = false;
		for (WebElement product : searchProducts) {
			if (product.getAttribute("title").equals(productName)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public void selectProduct(String productName) {
		for (WebElement product : searchProducts) {
			if (product.getAttribute("title").equals(productName))
				product.click();
		}
	}

	public void setQuantity(String quantity) {
		inpQuantity.clear();
		inpQuantity.sendKeys(quantity);
	}

	public void addToCart() {
		btnAddToCart.click();
	}

	public boolean checkConfirmationMsg() {
		try {
			return msgConfirmation.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}
