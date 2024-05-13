package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import OCUtil.DataProviders;
import OpencartBase.BaseClass;
import pageObjects.PageHome;
import pageObjects.PageLogin;
import pageObjects.PageMyAccount;

public class TC_003_LoginDDT extends BaseClass {
	PageHome homePage = null;
	PageLogin loginPage = null;
	PageMyAccount myAccPage = null;

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = { "sanity", "master" })
	public void verifyLoginDDT(String email, String password, String expectation) {
		logger.info("****> Starting TC_003_LoginDDT <****");
		try {

			// Home page
			homePage = new PageHome(driver);
			homePage.clickMyAcc();
			homePage.clickLogin(); // login link under my account

			// Login page
			loginPage = new PageLogin(driver);
			logger.info("Entering valid email and password");
			loginPage.setEmail(email);
			loginPage.setPassword(password);
			loginPage.clickLogin(); // login button
			logger.info("Clicked login button");

			myAccPage = new PageMyAccount(driver);
			boolean targetPage = myAccPage.isMyAccountPageExist();
			Thread.sleep(2000);
			if (expectation.equals("Valid")) {
				if (targetPage == true) {
					logger.info("Login Valid test passed");
					Assert.assertTrue(true);
					myAccPage.clickLogOut();
				} else {
					logger.error("Login Valid test failed");
					Assert.assertTrue(false);
				}
			}

			if (expectation.equals("Invalid")) {
				if (targetPage == true) {
					logger.error("Login invalid test failed");
					Assert.assertTrue(false);
					myAccPage.clickLogOut();
				} else {
					logger.info("Login invalid test passed");
					Assert.assertTrue(true);
				}
			}
		} catch (Exception e) {
			logger.error("Exception occurred: " + e.getMessage());
			Assert.fail("An unexpected error occurred: " + e.getMessage());
		} finally {
			logger.info("<**** Finished TC_003_LoginDDT ****>");
		}

	}
}
