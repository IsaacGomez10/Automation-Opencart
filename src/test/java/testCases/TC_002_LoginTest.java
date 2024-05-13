package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import OCUtil.OCUtil;
import OpencartBase.BaseClass;
import pageObjects.PageHome;
import pageObjects.PageLogin;
import pageObjects.PageMyAccount;

public class TC_002_LoginTest extends BaseClass {
	PageHome homePage = null;
	PageLogin loginPage = null;
	PageMyAccount myAccPage = null;
	
	@Test(groups = { "sanity", "master" })
	public void verifyLogin() {
		logger.info("****> Starting TC_002_LoginTest <****");
		logger.debug("Capturing application debug logs...");

		// Home page
		homePage = new PageHome(driver);
		homePage.clickMyAcc();
		logger.info("Clicked on my account link on the home page");
		homePage.clickLogin(); // login link under my account
		logger.info("Clicked on login under my account");

		// Login page
		loginPage = new PageLogin(driver);
		logger.info("Entering valid email and password");
		loginPage.setEmail(properties.getProperty("email"));
		loginPage.setPassword(properties.getProperty("password"));
		loginPage.clickLogin(); // login button
		logger.info("Clicked login button");

		myAccPage = new PageMyAccount(driver);
		boolean targetPage = myAccPage.isMyAccountPageExist();
		if (targetPage) {
			logger.info("Login test passed");
			Assert.assertTrue(true);
		} else {
			logger.error("Login test failed");
			Assert.fail();
		}

		logger.info("<**** Finished TC_002_LoginTest ****>");

	}

	public boolean verifyLogin(String email, String password) {
		boolean loginUser = false;
		try {
			
			homePage = new PageHome(driver);
			homePage.clickMyAcc();
			homePage.clickLogin(); // login link under my account
			System.out.println("Clicked on login under my account");
			
			// Login page
			loginPage = new PageLogin(driver);
			loginPage.setEmail(email);
			loginPage.setPassword(password);

			System.out.println("Entering valid email and password");
			loginPage.clickLogin(); // login button
			System.out.println("Clicked login button");

			myAccPage = new PageMyAccount(driver);
			// Verificar si hay algún error de registro
			if (!homePage.alertError()) {
				// Si no hay error, esperar un tiempo (2 segundos)
				boolean targetPage = myAccPage.isMyAccountPageExist();
				if (targetPage) {
					System.out.println("Login passed");
					loginUser = true;
				} else {
					System.out.println("Login failed");
					loginUser = false;
				}
			} else {
				System.out.println("Login failed...");
				loginUser = false;
			}

		} catch (Exception e) {
			System.out.println("Login test failed...");
			e.getMessage();
		}
		return loginUser;
	}

}
