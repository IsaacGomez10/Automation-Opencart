package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import OCUtil.OCUtil;
import OpencartBase.BaseClass;
import pageObjects.PageHome;
import pageObjects.PageSearch;

public class TC_005_AddToCartPageTest extends BaseClass {
	PageHome homePage = null;
	PageSearch pageSearch = null;

	@Test(groups = { "master" })
	public void verifyAddToCart() throws InterruptedException {
		logger.info("****> Starting TC_005_AddToCartPageTest <****");

		try {

			homePage = new PageHome(driver);
			String searchProduct = "iPhone";
			String product = "iPhone";
			String quantity = "2";
			homePage.enterProductName(searchProduct);
			homePage.clickSearch();
			logger.info("The searched product: " + searchProduct);

			pageSearch = new PageSearch(driver);

			if (pageSearch.isProductExist(searchProduct)) {
				pageSearch.selectProduct(product);
				logger.info("The product " + product + " had been selected");

				pageSearch.setQuantity(quantity);
				pageSearch.addToCart();

				logger.info("quantity " + quantity + " of the " + product + " product has been selected");

			}
			boolean msgConfirm = pageSearch.checkConfirmationMsg();
			if (msgConfirm == true) {
				Assert.assertEquals(msgConfirm, true);
				logger.info("The product " + product + " has been selected");
			}

		} catch (Exception e) {
			Assert.fail();
		} finally {
			logger.info("<**** Finished TC_004_SearchProductTest ****>");
		}
	}

	public boolean verifyAddToCart(String product, String quantity) throws InterruptedException {
		boolean statusProduct = false;
		try {

			homePage = new PageHome(driver);

			homePage.enterProductName(product);
			homePage.clickSearch();
			System.out.println("The searched product: " + product);

			pageSearch = new PageSearch(driver);

			if (pageSearch.isProductExist(product)) {
				pageSearch.selectProduct(product);
				OCUtil.wait(2);
				pageSearch.setQuantity(quantity);
				pageSearch.addToCart();
				System.out.println("Quantity " + quantity + " of the " + product + " product has been added");
			}
			boolean msgConfirm = pageSearch.checkConfirmationMsg();
			if (msgConfirm) {
				System.out.println("The product " + product + " has been selected");
				statusProduct = true;
			}else {
				statusProduct = false;
			}

		} catch (Exception e) {
			System.out.println("Add cart test failed...");
			e.getMessage();
		}
		return statusProduct;
	}
}
