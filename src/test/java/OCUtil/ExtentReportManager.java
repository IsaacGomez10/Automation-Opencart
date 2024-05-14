package OCUtil;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import OpencartBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter exSparkReporter; // Responsible for UI of the report and look and feel design.
	public ExtentReports exReports; // For providing the common information to the report or populating some common
									// details to the report.
	public ExtentTest exTest; // Creating the actual test in a report and updating the status and everything.

	String reportName = "";

	/**
	 * Execute before starting all the tests, this generate an empty report, the UI
	 * is the first thing the method will create
	 */
	public void onStart(ITestContext testContext) {
		/*
		 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		 * Date date = new Date(); String currentDateTimesTamp =
		 * dateFormat.format(date);
		 */

		String timeStamp = new SimpleDateFormat("yyyy-MM-dd--HH.mm.ss").format(new Date());
		reportName = "Test-Report-" + timeStamp + ".html";
		exSparkReporter = new ExtentSparkReporter(".\\reports\\" + reportName); // Specify location of the report

		exSparkReporter.config().setDocumentTitle("Opencart Automation Report");// Title of report
		exSparkReporter.config().setReportName("Opencart Functional testing"); // Name of the report
		exSparkReporter.config().setTheme(Theme.DARK);

		exReports = new ExtentReports();
		exReports.attachReporter(exSparkReporter);
		exReports.setSystemInfo("Application", "Opencart");
		exReports.setSystemInfo("Module", "Admin");
		exReports.setSystemInfo("Submodule", "Customer");
		exReports.setSystemInfo("Username", System.getProperty("user.name"));
		exReports.setSystemInfo("Environment", "QA");

		String os = testContext.getCurrentXmlTest().getParameter("os");
		exReports.setSystemInfo("Operating System", os);

		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		exReports.setSystemInfo("Browser", browser);

		List<String> includeGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if (!includeGroups.isEmpty()) {
			exReports.setSystemInfo("Groups", includeGroups.toString());
		}
	}

	/**
	 * This method will be triggered, if any test method is passed
	 */
	public void onTestSuccess(ITestResult testResult) {
		exTest = exReports.createTest(testResult.getTestClass().getName());
		exTest.assignCategory(testResult.getMethod().getGroups()); // to display groups in report
		exTest.log(Status.PASS, testResult.getName() + " --> got Successfully executed.");
	}

	/**
	 * This method will be activated; If any test method fails, it will capture
	 * evidence about the failed test.
	 */
	public void onTestFailure(ITestResult testResult) {
		exTest = exReports.createTest(testResult.getTestClass().getName());
		exTest.assignCategory(testResult.getMethod().getGroups()); // to display groups in report

		exTest.log(Status.FAIL, testResult.getName() + " --> got Failed executed.");
		exTest.log(Status.INFO, testResult.getThrowable().getMessage());

		try {
			String imgPath = new BaseClass().captureScreen(testResult.getName());
			exTest.addScreenCaptureFromPath(imgPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will be triggered, if any test method is skipped
	 */
	public void onTestSkipped(ITestResult testResult) {
		exTest = exReports.createTest(testResult.getTestClass().getName());
		exTest.assignCategory(testResult.getMethod().getGroups()); // to display groups in report

		exTest.log(Status.SKIP, testResult.getName() + " --> got Skipped executed.");
		exTest.log(Status.INFO, testResult.getThrowable().getMessage());
	}

	/**
	 * This method will get the test context
	 */
	public void onFinish(ITestContext testContext) {
		exReports.flush();

		/*
		 * Once the execution of the test cases is completed, immediately the report
		 * will be open on the browser and it will show case
		 */

		// Here we send the file location
		String pathOfExtetReport = System.getProperty("user.dir") + "\\reports\\" + reportName;
		File extentReport = new File(pathOfExtetReport);

		try {
			// This immediately open the report
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// To send email with attachment
		sendEmail("isaacg.21.10@gmail.com", "xcvc asda uykq oplo", "isaacg.21.10@gmail.com");
	}

	/**
	 * User defined method for sending email
	 * 
	 * @param senderEmail
	 * @param senderPassword
	 * @param recipientEmail
	 */
	public void sendEmail(String senderEmail, String senderPassword, String recipientEmail) {
		// SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		// Create a session object
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, senderPassword);
			}
		});

		try {
			// Create a MimeMessage object
			Message message = new MimeMessage(session);

			// Set the sender and recipient addresses
			message.setFrom(new InternetAddress(senderEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

			// Set the subject
			message.setSubject("Test Report with attachment");

			// Create a MimeMultipart object
			Multipart multipart = new MimeMultipart();

			// Attach the file
			String filePath = ".\\reports\\" + reportName;
			String fileName = reportName;

			MimeBodyPart attachmentPart = new MimeBodyPart();
			attachmentPart.attachFile(filePath);
			attachmentPart.setFileName(fileName);

			// Create a MimeBodyPart for the text content
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setText("Please find the attached file.");

			// Add the parts to the multipart
			multipart.addBodyPart(textPart);
			multipart.addBodyPart(attachmentPart);

			// Set the content of the message
			message.setContent(multipart);

			// Send the message
			Transport.send(message);

			System.out.println("Email sent successfully!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
