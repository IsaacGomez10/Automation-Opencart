package OpencartBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; // log4j
import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Reusable methods in our test cases
 */
public class BaseClass {

	static public WebDriver driver;
	public Logger logger;

	public Properties properties;

	/**
	 * Start the application include logging logs
	 * 
	 * @param os
	 * @param browser
	 * @throws IOException
	 */
	@BeforeClass(groups = { "sanity", "regression", "master" })
	@Parameters({ "os", "browser" })
	public void setUp(String os, String browser) throws IOException {
		// loading properties file
		FileInputStream file = new FileInputStream(".//src/test/resources/config.properties");
		properties = new Properties();
		properties.load(file);

		// Loading log4j2 file
		logger = LogManager.getLogger(this.getClass());// log4j

		// This is the hub remote for selenium grid
		if (properties.getProperty("execution_environment").equalsIgnoreCase("remote")) {
			String nodeURL = "http://localhost:4444/wd/hub";
			DesiredCapabilities desCapabilities = new DesiredCapabilities();
			// OS
			if (os.equalsIgnoreCase("windows")) {
				desCapabilities.setPlatform(Platform.WIN11); // Cambiado a WINDOWS
			} else if (os.equalsIgnoreCase("mac")) {
				desCapabilities.setPlatform(Platform.MAC);
			} else {
				System.out.println("No matching os...");
				return;
			}

			// Browser
			switch (browser.toLowerCase()) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				desCapabilities.setBrowserName(browser);
				break;
			case "edge":
				desCapabilities.setBrowserName("MicrosoftEdge");
				break;
			default:
				System.out.println("No matching browser...");
				return;
			}
			driver = new RemoteWebDriver(new URL(nodeURL), desCapabilities);
		}
		// This is the hub local for selenium grid
		else if (properties.getProperty("execution_environment").equalsIgnoreCase("local")) {
			// launching browser based on condition - locally
			switch (browser.toLowerCase()) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;

			default:
				System.out.println("No matching browser...!");
				return;
			}
		}
		logger.info("**** Running in " + properties.getProperty("execution_environment") + " platform...");

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

		driver.get(properties.getProperty("appURL"));
		driver.manage().window().maximize();
	}

	/**
	 * Close the application then to finished the test
	 */
	@AfterClass(groups = { "sanity", "regression", "master" })
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	// Use dependency to create random information
	Faker faker = new Faker();

	/**
	 * Will generate randomly a first name
	 * 
	 * @return - random first name
	 */
	public String randomFirstName() {
		String fkName = faker.name().firstName().toUpperCase();
		return fkName;
	}

	/**
	 * Will generate randomly a last name
	 * 
	 * @return - random last name
	 */
	public String randomLastName() {
		String fkLastName = faker.name().lastName().toUpperCase();
		return fkLastName;
	}

	/**
	 * Will generate randomly a email address
	 * 
	 * @return - random email address
	 */
	public String randomEmail() {
		String fkEmail = faker.internet().emailAddress();
		return fkEmail;
	}

	/**
	 * Will generate randomly a password
	 * 
	 * @return - random password
	 */
	public String randomPassword() {
		String fkPassword = faker.internet().password();
		return fkPassword;
	}
	
	/**
	 * Will generate randomly an address
	 * @return
	 */
	public String randomAddress() {
		String fkAddress = faker.address().fullAddress();
		return fkAddress;
	}
	
	/**
	 * Will generate randomly a city
	 * @return
	 */
	public String randomCity() {
		String fkCity = faker.address().city();
		return fkCity;
	}
		
	/**
	 * Will generate randomly a postal code
	 * @return
	 */
	public String randomZipCode() {
		String fkPostCode = faker.address().zipCode();
		return fkPostCode;
	}
	
	/**
	 * Will generate randomly a country
	 * @return
	 */
	public String randomCountry() {
		String fkCountry = faker.address().country();
		return fkCountry;
	}
	
	/**
	 * Will generate randomly a state
	 * @return
	 */
	public String randomState() {
		String fkState = faker.address().state();
		return fkState;
	}
	
	
	/**
	 * Method to capture evidences
	 * 
	 * @param testName
	 * @return
	 * @throws Exception
	 */
	public String captureScreen(String testName) throws Exception {
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + testName + "_" + timeStamp
				+ ".png";
		File targetFile = new File(targetFilePath);

		sourceFile.renameTo(targetFile);

		return targetFilePath;
	}
}
