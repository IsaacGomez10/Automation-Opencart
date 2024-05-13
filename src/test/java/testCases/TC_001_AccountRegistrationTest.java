package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import OCUtil.OCUtil;
import OpencartBase.BaseClass;
import pageObjects.PageAccountRegistration;
import pageObjects.PageHome;
import pageObjects.PageLogin;
import pageObjects.PageMyAccount;

public class TC_001_AccountRegistrationTest extends BaseClass {
	PageHome homePage = null;
	PageMyAccount myAccPage = null;
    PageAccountRegistration arPage = null;

	@Test(groups = { "regression", "master" })
	public void verifyAccountRegistration() {
		logger.info("****> Starting TC 001 AccountRegistrationTest <****");
		logger.debug("Start applications logs...");
		try {
			homePage = new PageHome(driver);
			homePage.clickMyAcc();
			logger.info("Clicked on my account link");

			homePage.clickRegister();
			logger.info("Clicked on registration link");

			logger.info("Entering customer information");
			
			arPage = new PageAccountRegistration(driver);
			arPage.setFirstName(randomFirstName());
			arPage.setLastName(randomLastName());
			arPage.setEmail(randomEmail());
			arPage.setPassword(randomPassword());

			arPage.setPrivacyPolicy();
			arPage.clickContinue();
			logger.info("Clicked on continue");

			logger.info("Validating expected message...");
			String confirmationMsg = arPage.getConfirmationMsg();
			if (confirmationMsg.equals("Your Account Has Been Created!")) {
				logger.info("Test passed...");
				Assert.assertTrue(true);
			} else {
				logger.error("Test failed.");
				Assert.fail();
			}
		} catch (Exception e) {
			logger.error("Test failed");
			e.getMessage();
		} finally {
			logger.debug("End applications logs...");
			logger.info("<**** Finished TC 001 AccountRegistrationTest ****>");
		}
	}

	public boolean verifyAccountRegistration(String firstName, String lastName, String email, String password) {
		boolean registerUser = false;
		try {
			homePage = new PageHome(driver);
			homePage.clickMyAcc();
			homePage.clickRegister();
			System.out.println("Clicked on registration link");

			arPage = new PageAccountRegistration(driver);

			arPage.setFirstName(firstName);
			arPage.setLastName(lastName);
			arPage.setEmail(email);
			arPage.setPassword(password);
			System.out.println("Entering customer information");

			arPage.setPrivacyPolicy();
			arPage.clickContinue();
			System.out.println("Clicked on continue");
			
			 // Verificar si hay algún error de registro
	        if (!homePage.alertError()) {
	            // Si no hay error, esperar un tiempo (2 segundos)
	            // Obtener el mensaje de confirmación
	            String confirmationMsg = arPage.getConfirmationMsg();
	            
	            // Verificar si la cuenta ha sido creada exitosamente
	            if ("Your Account Has Been Created!".equals(confirmationMsg)) {
	                System.out.println("Registration passed...");
	                myAccPage = new PageMyAccount(driver);
	                myAccPage.clickLogOut();
	                registerUser = true;
	            } else {
	                System.out.println("Registration failed...");
	                registerUser = false;
	            }
	        } else {
	            registerUser = false;
	        }

		} catch (Exception e) {
			System.out.println("Registration test failed...");
			e.getMessage();
		}

		return registerUser;
	}
}
