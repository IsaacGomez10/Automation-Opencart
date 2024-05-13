package testCases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import OCUtil.OCUtil;
import OpencartBase.BaseClass;
import pageObjects.PageCheckout;
import pageObjects.PageShopingCart;

public class TC_006_EndToEndTest extends BaseClass {
	SoftAssert softAssert = null;
	TC_001_AccountRegistrationTest tc001 = null;
	TC_002_LoginTest tc002 = null;
	TC_004_SearchProductTest tc004 = null;
	TC_005_AddToCartPageTest tc005 = null;
	PageShopingCart pageShopingCart = null;
	PageCheckout pageCheckout = null;
	Actions action = null;

	String[] data = { randomFirstName(), randomLastName(), randomEmail(), "123456", randomAddress() };
	String[] information = { "iPhone", "1" };

	@Test(groups = { "master" })
	public void endToendTest() throws InterruptedException {
		// Soft assertions
		logger.info("****> Starting TC_006_EndToEndTest <****");
		softAssert = new SoftAssert();
		tc001 = new TC_001_AccountRegistrationTest();
		tc002 = new TC_002_LoginTest();
		tc005 = new TC_005_AddToCartPageTest();
		pageShopingCart = new PageShopingCart(driver);
		pageCheckout = new PageCheckout(driver);
		action = new Actions(driver);
		
		// Verify registered account
		boolean status = tc001.verifyAccountRegistration(data[0], data[1], data[2], data[3]);
		if (status) {
			logger.info("Has been successfully registered");
		} else {
			logger.info("Could not register correctly, trying to log in.");
		}

		// Verify logged in account
		status = tc002.verifyLogin(data[2], data[3]);
		if (status) {
			logger.info("Has been loged correctly");
		} else {
			logger.info("Could not log in correctly");
			Assert.fail();
		}

		// check the product in the cart
		status = tc005.verifyAddToCart(information[0], information[1]);
		if (status) {
			logger.info("The product has been found");
			String totalPrice = pageShopingCart.goCheckout();
			logger.info("Total price in shopping cart: " + totalPrice);
			
			pageCheckout.newAddress();
			
			pageCheckout.setFirstName(data[0]);
			pageCheckout.setLastName(data[1]);
			pageCheckout.setAddress1(data[4]);
			pageCheckout.setAddress2(randomAddress());
			OCUtil.wait(1);
			pageCheckout.setCity(randomCity());
			pageCheckout.setZipCode(randomZipCode());
			pageCheckout.setCountry("Colombia");
			pageCheckout.setState("Bogota D.C.");
			OCUtil.wait(2);
			pageCheckout.clickOnContinue();
			logger.info("You have added new address");
			action.keyDown(Keys.ESCAPE).keyUp(Keys.ESCAPE).perform();
			pageCheckout.choseShippingMethod();
			pageCheckout.chosePaymentMethod();
			OCUtil.wait(2);
			logger.info("You have selected Shipping and Payment method.");
			pageCheckout.addComments("Test comment");
			String totalOrderPrice = pageCheckout.getTotalPriceBeforeConfOrder();
			logger.info("Total price in your order: " + totalOrderPrice);
			//pageCheckout.clickOnConfirmOrder();
			OCUtil.wait(2);
			boolean orderConfirm = pageCheckout.isOrderPlaced(driver);
			if (orderConfirm) {
				logger.info("Your order has been confirmed!");
				pageCheckout.continueHomePage();
				softAssert.assertAll();
			} else {
				Assert.fail();
			}
		} else {
			logger.info("The product has not been found");
			Assert.fail();
		}
	}

}
