package com.qa.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import com.qa.utilities.Elements;

public class ContactUsFormPage {
	
	private By contactUsTab = By.xpath("//a[@href=\"/contact_us\"]");
	private String pageTitle = "Automation Exercise - Contact Us";
	private By GetInTouchHeader = By.xpath("//h2[contains(text(),'Get In Touch')]");
	private By nameField = By.xpath("//input[@name=\"name\"]");
	private By emailField = By.xpath("//input[@name=\"email\"]");
	private By subjectField = By.xpath("//input[@name=\"subject\"]");
	private By MessageField = By.id("message");
	private By chooseFile = By.xpath("//input[@type=\"file\"]");
	private By submitButton = By.xpath("//input[@type=\"submit\"]");
	private By successMessageAfterSubmiting = By.xpath("//div[@class=\"status alert alert-success\"]");
	private By HomeButton = By.xpath("//a[@class=\"btn btn-success\"]");
	
	private By testcaseTab = By.xpath("//a[@href=\"/test_cases\"]");
	private By testCasesHeader = By.xpath("//h2/b");
//	private String testCasesPageTitleExpected  =  "Automation Practice Website for UI Testing - Test Cases";
			

	private WebDriver driver;
	private Elements ele;
	
	private static final Logger logger = LogManager.getLogger(ContactUsFormPage.class);
	
	public ContactUsFormPage(WebDriver driver) {
		this.driver = driver;
		this.ele= new Elements(driver);
	}
	public String ActualPageTitle() {
		logger.info("expected page title="+pageTitle);
		return pageTitle;
	}
	public void clickOnContactUsTab() {
		ele.waitForPageReady();
		ele.retryUntilDisplayed(contactUsTab, 2);
		ele.safeClick(contactUsTab, 3);
		logger.info("click on contactus tab");
		ele.waitForPageReady();
	}
	
	public String contactUsFormPageTitle() {
		String ContactUspageTitle = ele.GetTitle();
		logger.info("getting contact us form page title");
		return ContactUspageTitle;
	}
	
	public String getInTouchHeader() {
	String pageHeader =	ele.getElement(GetInTouchHeader).getText().toUpperCase();
	logger.info("gettting getintouch header text and convert to upper case");
	return pageHeader;
	}
	
	public void enterName() {
		String name = "nameisthisAndThat";
		ele.dosendKeys(nameField, name);
		logger.info("enter name field = "+name);
	}
	
	public void enterEmail() {
		String email = "nameisthisAndThat@gmail.com";
		ele.dosendKeys(emailField, email);
		logger.info("enter email field = "+email);
	}
	
	public void enterSubject() {
		String subject = "This is for just learning purpose";
		ele.dosendKeys(subjectField, subject);
		logger.info("enter subject field = "+subject);
	}
	
	public void enterMessage() {
		String message = "enter your message here and verify the "
				+ "format and the fonts and the necessary details";
		ele.dosendKeys(MessageField, message);
		logger.info("enter message");
	}

	public void chooseFile() {
		String filePath = "Y:\\Yashvanth patil learnings"
				+ "\\Eclipse Workspace\\AutomationExercise\\src\\test"
				+ "\\resources\\config\\uploadfileTest.txt";
		ele.getElement(chooseFile).sendKeys(filePath);
		logger.info("file uploaded from path ="+filePath);
	String uploadedFilename = 	ele.getElement(chooseFile).getText();
	logger.info("uploaded file name is(uploadfileTest.txt) =  "+uploadedFilename);
	}
	
	public void clickSubmitButton() {
		ele.safeClick(submitButton, 3);
		logger.info("click submit button not waiting for page to ready");
		
	}
	
	public boolean successMessageVerify() {
		logger.info("success message is displayed or not verifying");
		return ele.getElement(successMessageAfterSubmiting).isDisplayed();
	}
	
	public String sucessMessageText() {
		logger.info("success message text extracted");
		return ele.getElement(successMessageAfterSubmiting).getText();
	}
	
	public void clickOkButton() {
		try {
		    Alert alert = driver.switchTo().alert();
		    logger.info("Alert handled successfully ="+alert.getText());
		    alert.accept(); // or alert.dismiss();
		    
		} catch (NoAlertPresentException e) {
		    System.out.println("No alert present after submit.");
		}
		
	}
	
	public void cickHomeButton() {
		ele.safeClick(HomeButton, 3);
		logger.info("click on home button");
		ele.waitForPageReady();
	}
	
	public void clickOnTestCaseTab() {
		ele.waitForPageReady();
		ele.safeClick(testcaseTab, 3);
		logger.info("click on test case tab");
		ele.waitForPageReady();
	}
	
	public boolean testcaseHeaderDisplayed_or_Not() {
		ele.waitForPageReady();
		logger.info("checking testcases header is displayed or not");
	boolean testCaseHeader = ele.getElement(testCasesHeader).isDisplayed();
	logger.info("status of testcase header displayed or not ="+testCaseHeader);
	return testCaseHeader;
	}
	
	public String getTestCaseHeaderText() {
	String text = ele.dogetText(testCasesHeader);
	logger.info("getting text of testcase header ="+text);
	return text;
	
	}
	
	public String getTitleOfPage() {
		logger.info("getting the title of the page");
		return ele.GetTitle();
	}
}
