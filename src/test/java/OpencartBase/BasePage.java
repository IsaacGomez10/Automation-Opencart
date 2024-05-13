package OpencartBase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

	WebDriver driver;

	/**
	 * Constructor Taking the driver instance
	 */
	public BasePage(WebDriver driver) {
		// Sending the driver
		this.driver = driver;
		// Put the PageFactory
		PageFactory.initElements(driver, this);
	}
}
