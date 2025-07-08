package com.qa.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.utilities.Elements;
import com.qa.utilities.ReadConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class loginORsignupPage {

	private By SignupORLoginTab = By.cssSelector("a[href=\"/login\"]");
	private By DeleteAccountTab = By.xpath("//a[@href=\"/delete_account\"]");

	private By loginusername = By.xpath("//a[contains(text(),\" Logged in as \")]");
	private By AccDeletedMessage = By.xpath("//h2[@class=\"title text-center\"]/b");
	private By continueButtn = By.xpath("//div/a[contains(text(),'Continue')]");

	private By loginemailfield = By.xpath("//div[@class=\"login-form\"]/form/input[@name=\"email\"]");
	private By loginpasswordfield = By.xpath("//div[@class=\"login-form\"]/form/input[@name=\"password\"]");
	private By loginButton = By.xpath("//div[@class=\"login-form\"]/form/button");

	private By FormHeading = By.xpath("//h2/b[contains(text(),'Enter Account Information')]");

	private By signupnamefield = By.xpath("//div[@class=\"signup-form\"]/form/input[@name=\"name\"]");
	private By signupemailfield = By.xpath("//div[@class=\"signup-form\"]/form/input[@name=\"email\"]");
	private By UserExistsAlreadyError = By.xpath("//form[@action=\"/signup\"]/p");
	private By signupbutton = By.xpath("//div[@class=\"signup-form\"]/form/button");

	private By MaleTitle = By.id("id_gender1");
	private By FemaleTitle = By.id("uniform-id_gender2");
//	private By username = By.id("name");
//	private By userEmail = By.id("email");
	private By password = By.id("password");
	private By selectDaydropdown = By.id("days");
	private By SelectMothDropdon = By.id("months");
	private By SelectyearDropdown = By.id("years");
	private By newscheckbox = By.id("newsletter");
	private By reciveofferscheckbox = By.id("optin");
	private By newFirstName = By.id("first_name");
	private By newLastName = By.id("last_name");
	private By company = By.id("company");
	private By Adress1 = By.id("address1");
	private By Adress2 = By.id("address2");
	private By Selectcountry = By.id("country");
	private By State = By.id("state");
	private By Zipcode = By.id("zipcode");
	private By MobileNumber = By.id("mobile_number");
	private By City = By.id("city");
	private By AccCreatedMesssage = By.xpath("//h2/b");
	private By CreateAccButton = By.xpath("//button[contains(text(),'Create Account')]");

	private WebDriver driver;
	private Elements ele;
	Properties prop;
	private ReadConfig readconfig;
	private String Testurl;
	private static final Logger logger = LogManager.getLogger(loginORsignupPage.class);


	public loginORsignupPage(WebDriver driver) {
		this.driver = driver;
		this.ele = new Elements(driver);
		getproperties();
	}

	public void getproperties() {
		readconfig = new ReadConfig();
		prop = readconfig.init_prop();
	}

	public String browserName() {
		return prop.getProperty("browser");
	}

	public void Enterurl() {
		Testurl = prop.getProperty("TestUrl");
		logger.info("Navigating to test url "+ Testurl);
		driver.get(Testurl);
		ele.waitForPageReady();
	}

	public void clicksignuporloginTab() {
		ele.waitForPageReady();
		logger.info("clicking signup/login Tab");
		ele.retryUntilDisplayed(SignupORLoginTab,3);
		ele.dowaitForClickable(SignupORLoginTab);
		ele.safeClick(SignupORLoginTab,3);
	}

	public boolean verifyAllElementsDisplayed() {
		By[] allLocators = { SignupORLoginTab, loginemailfield, loginpasswordfield, loginButton, signupnamefield,
				signupemailfield, signupbutton };
		ele.doWaitForPageLoadWithRetry(driver, 3, 10);
		for (By locator : allLocators) {
			try {
				ele.retryUntilDisplayed(locator,3);// Optional wait
				if (!ele.doisDisplayed(locator)) {
					System.out.println("Element not displayed: " + locator.toString());
					return false;
				}
			} catch (Exception e) {
				System.out.println("Exception while checking: " + locator.toString());
				e.printStackTrace();
				return false;
			}
		}
		logger.info("Verifyed all elements displayed or not");
		return true;
	}

	public void FillSignupFields(String signupname, String signupemail) {
		logger.info("Filling signup fields: Name = {}, Email = {}", signupname, signupemail);
		ele.retryUntilDisplayed(signupnamefield,3);
		ele.retryUntilDisplayed(signupemailfield,3);
		
		ele.retryUntilDisplayed(signupnamefield, 3);
		ele.dosendKeys(signupnamefield, signupname);
		
		ele.retryUntilDisplayed(signupemailfield, 3);
		ele.dosendKeys(signupemailfield, signupemail);
	}

	public void fillLoginFields() {
		String loginEmail = prop.getProperty("LoginEmail");
		String loginPassword = prop.getProperty("LoginPassword");
		logger.info("Filling login form with email: {}, password: {}", loginEmail,loginPassword);
		ele.retryUntilDisplayed(loginemailfield,3);
		ele.retryUntilDisplayed(loginpasswordfield,3);
		
		logger.info("entring login email: "+loginEmail);
		ele.retryUntilDisplayed(loginemailfield, 3);
		ele.dosendKeys(loginemailfield, loginEmail);
		
		logger.info("entring login password :");
		ele.retryUntilDisplayed(loginpasswordfield, 3);
		ele.dosendKeys(loginpasswordfield, loginPassword);
	}

	public void ClickLoginButton() {
		ele.retryUntilDisplayed(loginButton,3);
		ele.dowaitForClickable(loginButton);
		logger.info("clicking login button");
		ele.retryUntilDisplayed(loginButton, 3);
		ele.safeClick(loginButton,3);
		ele.waitForPageReady();
	}

	public void ClickSignupButton() {
		
		ele.retryUntilDisplayed(signupbutton, 3);
		ele.dowaitForClickable(signupbutton);
		logger.info("clicking signup button");
		ele.safeClick(signupbutton,3);
		ele.waitForPageReady();
		
	}
	public void retrySignupForExistingUser() {
			logger.info("retrying signup for the error message occured");
			String email = DataFromExcel("New User Details", "Email");
			String password = DataFromExcel("New User Details", "Password");
			
			ele.retryUntilDisplayed(loginemailfield, 3);
			logger.info("Entering email into the email text fild in retry method");
			ele.dosendKeys(loginemailfield, email);
			
			ele.retryUntilDisplayed(loginpasswordfield, 3);
			logger.info("entering password into the password field in retry method");
			ele.dosendKeys(loginpasswordfield, password);
			
			logger.info("clicking  the loggin button in retry method");
			ClickLoginButton();
			ele.waitForPageReady();
			
			DeleteAccountTab();
			Clickcontinuebutton();
			ele.waitForPageReady();
			
			driver.navigate().to(Testurl);
			ele.waitForPageReady();
		}
	
	

	public String GetTitle() {
		ele.doWaitForPageLoadWithRetry(driver, 3, 15000);
		logger.info("getting title of the page");
		ele.waitForPageReady();
		return ele.GetTitle();
	}

	public String GetCurrentUrl() {
		logger.info("getting current url");
		return driver.getCurrentUrl();
	}

	public String FormHeading() {
		ele.retryUntilDisplayed(FormHeading,3);
		logger.info("get text of Form Heading");
		ele.waitForPageReady();
		return ele.dogetText(FormHeading);
	}

	public String usertype() {
		logger.info("getting usertype from config.properties");
		return prop.getProperty("UserType");
	}

	public String logedinusername() {
		ele.retryUntilDisplayed(loginusername,3);
		logger.info("getting loggedin user name");
		ele.waitForPageReady();
		return ele.dogetText(loginusername);
	}

	public boolean IsaccdeletedmessageDisplayed() {
		ele.retryUntilDisplayed(AccDeletedMessage,3);
		logger.info("waiting for account deleted message");
		boolean AccountDeletionMessageDisplayed = ele.getElement(AccDeletedMessage).isDisplayed();
		logger.info("getting account deletion status ="+AccountDeletionMessageDisplayed);
		return AccountDeletionMessageDisplayed;
	}

	public void Clickcontinuebutton() {
		ele.retryUntilDisplayed(continueButtn,3);
		ele.dowaitForClickable(continueButtn);
		logger.info("Clicking on continue button");
		ele.safeClick(continueButtn, 3);
		ele.waitForPageReady();
	}

	public String DataFromExcel(String SheetName, String FieldName) {
		Map<String, String> data = readconfig.getUserDataFromExcel(SheetName);
		if (data == null || data.isEmpty()) {
		logger.error("Excel sheet '{}' is empty or not found!", SheetName);	
		} else {
		logger.debug("Reading field '{}' from sheet '{}': {}", FieldName, SheetName, data.get(FieldName));
		}
		return data.get(FieldName);
	}
	
	public void ClickMaleTitleRadioButtons() {
		ele.doWaitForPageLoadWithRetry(driver, 2, 10);
		ele.retryUntilDisplayed(MaleTitle,3);
		ele.dowaitForClickable(MaleTitle);
		logger.info("select male title radio button");
		
		ele.waitForPageReady();
		ele.safeClick(MaleTitle,3);
	}

	public void ClickFemaleRadioButton() {
		ele.doWaitForPageLoadWithRetry(driver, 2, 10);
		ele.retryUntilDisplayed(FemaleTitle,3);
		ele.dowaitForClickable(FemaleTitle);
		logger.info("select female title radio button");
		ele.waitForPageReady();
		ele.safeClick(FemaleTitle,3);
	}

	public void EnterPasswordField(String Sheetname, String fieldname) {
		logger.info("entering password");
		ele.dosendKeys(password, DataFromExcel(Sheetname, fieldname));
	}

	public void SelectDOB(String Sheetname, String dob) {
		String DOBData = DataFromExcel(Sheetname, dob);
		logger.info("Parsing DOB: {}", DOBData);
		
		if (DOBData != null && !DOBData.trim().isEmpty()) {
			String[] dobParts = DOBData.split("/"); // Renamed dob to dobParts for clarity
			if (dobParts.length == 3) {
				logger.info("Selecting DOB Day={}, Month={}, Year={}", dobParts[0], dobParts[1], dobParts[2]);
				
				// Strip leading zeros for day and month if HTML values are single digits
				String day = dobParts[0].startsWith("0") ? dobParts[0].substring(1) : dobParts[0];
				String month = dobParts[1].startsWith("0") ? dobParts[1].substring(1) : dobParts[1];
				String year = dobParts[2];

				ele.doselectByValue(selectDaydropdown, day);
				ele.doselectByValue(SelectMothDropdon, month);
				ele.doselectByValue(SelectyearDropdown, year);
				
			} else {
				logger.warn("DOB format invalid: {}", DOBData);
			}
		} 
	}

	public void selectCheckboxes() {
		ele.retryUntilDisplayed(newscheckbox,3);
		ele.dowaitForClickable(newscheckbox);
		logger.info("check newsletters check box");
		ele.safeClick(newscheckbox,3);
		
		ele.retryUntilDisplayed(reciveofferscheckbox,3);
		ele.dowaitForClickable(reciveofferscheckbox);
		logger.info("recove offers check box");
		ele.safeClick(reciveofferscheckbox,3);
	}

	public void EnterAdressInformation(String sheetname) {
		Map<String, String> adressdata = readconfig.getUserDataFromExcel(sheetname);
		ele.dosendKeys(newFirstName, adressdata.get("First name_*"));
		ele.dosendKeys(newLastName, adressdata.get("Last_name_*"));
		ele.dosendKeys(company, adressdata.get("Company"));
		ele.dosendKeys(Adress1, adressdata.get("Address_1"));
		ele.dosendKeys(Adress2, adressdata.get("Address_2"));
		ele.dosendKeys(State, adressdata.get("State_*"));
		ele.dosendKeys(Zipcode, adressdata.get("Zipcode_*"));
		ele.dosendKeys(MobileNumber, adressdata.get("Mobile_Number_*"));
		ele.dosendKeys(City, adressdata.get("City_*"));
		String country = adressdata.get("Country_*");
		ele.doselectByVisibleText(Selectcountry, country);
	}

	public String AccCreatedSuccessmessage() {
		logger.info("waiting for account created confirmation");
		ele.retryUntilDisplayed(AccCreatedMesssage,3);
		logger.info("Account created message is confirmed");
		return ele.dogetText(AccCreatedMesssage);
		
	}

	public void DeleteAccountTab() {
		
		ele.waitForPageReady();
		logger.info("Attempting to delete account...");
		ele.retryUntilDisplayed(DeleteAccountTab, 3);
		ele.safeClick(DeleteAccountTab,3);
		logger.info("Delete account button clicked.");
		ele.waitForPageReady();
	}

	public String AccDeletedMesage() {
		ele.retryUntilDisplayed(AccDeletedMessage, 3);
	String AccDeletionMessage =	ele.dogetText(AccDeletedMessage);
	logger.info("waiting for account deleted message to display ="+AccDeletionMessage);
		return AccDeletionMessage;
		
	}

	public void ClickCreateAccButton() {
		ele.retryUntilDisplayed(CreateAccButton,3);
		ele.dowaitForClickable(CreateAccButton);
		ele.safeClick(CreateAccButton,3);
		logger.info("clicked createAccount button");
		ele.waitForPageReady();
		
	}

	public boolean UserAlreadyExistsErrorMesage() {
		ele.waitForPageReady();
	    ele.retryUntilDisplayed(UserExistsAlreadyError, 3);
	    boolean isDisplayed = ele.getElement(UserExistsAlreadyError).isDisplayed();
	    logger.info("User already exists = " + isDisplayed);
	    return isDisplayed;
	}

	
	public void Takescreenshot(String filename) throws IOException {
		ele.docaptureScreenshot(filename);
	}

}
