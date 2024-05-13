package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import OpencartBase.BaseClass;
import pageObjects.PageHome;
import pageObjects.PageSearch;

public class TC_004_SearchProductTest extends BaseClass {
	PageHome homePage = null;
	PageSearch pageSearch = null;

	@Test(groups = { "master" })
	public void verifyPropductSearch() throws Exception {
		logger.info("****> Starting TC_004_SearchProductTest <****");
		try {
			homePage = new PageHome(driver);

			String searchProduct = "mac";
			String product = "MacBook";

			homePage.enterProductName(searchProduct);
			homePage.clickSearch();
			logger.info("The searched product: " + searchProduct);

			pageSearch = new PageSearch(driver);
			pageSearch.isProductExist("MacBook");

			boolean existProd = pageSearch.isProductExist(product);
			if (existProd == true) {
				Assert.assertEquals(existProd, true);
				logger.info("The product " + product + " has been found");
			}

		} catch (Exception e) {
			logger.error("Exception occurred: " + e.getMessage());
			Assert.fail("An unexpected error occurred: " + e.getMessage());
		} finally {
			logger.info("<**** Finished TC_004_SearchProductTest ****>");
		}

	}

	public boolean verifyPropductSearch(String product){
		boolean statusProduct = false;
		try {
			homePage = new PageHome(driver);

			homePage.enterProductName(product);
			homePage.clickSearch();
			System.out.println("The searched product: " + product);

			pageSearch = new PageSearch(driver);
			pageSearch.isProductExist("MacBook");

			boolean existProd = pageSearch.isProductExist(product);
			if (existProd == true) {
				System.out.println("The product " + product + " has been found");
				statusProduct = true;
			} else {
				System.out.println("The product " + product + " has not been found");
				statusProduct = false;
			}

		} catch (Exception e) {
			System.out.println("Search test failed...");
			e.getMessage();
		}
		return statusProduct;
	}
}
