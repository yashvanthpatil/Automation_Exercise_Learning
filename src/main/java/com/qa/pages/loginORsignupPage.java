package com.qa.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.utilities.Elements;
import com.qa.utilities.ReadConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class loginORsignupPage {

	private By SignupORLoginTab = By.cssSelector("a[href=\"/login\"]");
	private By DeleteAccount = By.xpath("//a[@href=\"/delete_account\"]");

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
	private By username = By.id("name");
	private By userEmail = By.id("email");
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
		String Testurl = prop.getProperty("TestUrl");
		logger.info("Navigating to test url "+ Testurl);
		driver.get(Testurl);
	}

	public void clicksignuporloginTab() {
		ele.dowaitForVisibility(SignupORLoginTab);
		ele.doclick(SignupORLoginTab);
	}

	public WebElement continueButon() {
		ele.dowaitForVisibility(continueButtn);
		return driver.findElement(continueButtn);
	}

	public boolean verifyAllElementsDisplayed() {
		By[] allLocators = { SignupORLoginTab, loginemailfield, loginpasswordfield, loginButton, signupnamefield,
				signupemailfield, signupbutton };

		for (By locator : allLocators) {
			try {
				ele.dowaitForVisibility(locator);// Optional wait
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
		return true;
	}

	public void FillSignupFields(String signupname, String signupemail) {
		logger.info("Filling signup fields: Name = {}, Email = {}", signupname, signupemail);
		ele.dowaitForVisibility(signupnamefield);
		ele.dowaitForVisibility(signupemailfield);
		ele.dosendKeys(signupnamefield, signupname);
		ele.dosendKeys(signupemailfield, signupemail);
	}

	public void fillLoginFields() {
		String loginEmail = prop.getProperty("LoginEmail");
		String loginPassword = prop.getProperty("LoginPassword");
		logger.info("Filling login form with email: {}, password: {}", loginEmail,loginPassword);
		ele.dowaitForVisibility(loginemailfield);
		ele.dowaitForVisibility(loginpasswordfield);
		ele.dosendKeys(loginemailfield, loginEmail);
		ele.dosendKeys(loginpasswordfield, loginPassword);
	}

	public void ClickLoginButton() {
		ele.dowaitForClickable(loginButton);
		ele.doclick(loginButton);
	}

	public void ClickSignupButton() {
		ele.dowaitForClickable(signupbutton);
		ele.doclick(signupbutton);
	}

	public String GetTitle() {
		ele.doWaitForPageLoadWithRetry(driver, 3, 15000);
		return ele.GetTitle();
	}

	public String GetCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public String FormHeading() {
		ele.dowaitForVisibility(FormHeading);
		return ele.dogetText(FormHeading);
	}

	public String usertype() {
		return prop.getProperty("UserType");
	}

	public String logedinusername() {
		ele.dowaitForVisibility(loginusername);
		return ele.dogetText(loginusername);
	}

	public boolean IsaccdeletedmessageDisplayed() {
		ele.dowaitForVisibility(AccDeletedMessage);
		return ele.doisDisplayed(AccDeletedMessage);
	}

	public void Clickcontinuebutton() {
		ele.dowaitForVisibility(continueButtn);
		ele.doisDisplayed(continueButtn);
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
		ele.doclick(MaleTitle);
	}

	public void ClickFemaleRadioButton() {
		ele.doclick(FemaleTitle);
	}

	public void EnterPasswordField(String Sheetname, String fieldname) {
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
		ele.doclick(newscheckbox);
		ele.doclick(reciveofferscheckbox);
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
		ele.dowaitForVisibility(AccCreatedMesssage);
		return ele.dogetText(AccCreatedMesssage);
	}

	public void DeleteAccountButton() {
		logger.info("Attempting to delete account...");
		ele.dowaitForVisibility(DeleteAccount);
		ele.doclick(DeleteAccount);
		logger.info("Delete account button clicked.");
	}

	public String AccDeletedMesage() {
		ele.dowaitForVisibility(AccDeletedMessage);
		return ele.dogetText(AccDeletedMessage);
	}

	public void ClickCreateAccButton() {
		ele.dowaitForVisibility(CreateAccButton);
		ele.doclick(CreateAccButton);
	}

	public boolean UserAlreadyExistsErrorMesage() {
		return ele.doisDisplayed(UserExistsAlreadyError);
	}
	
	public void Takescreenshot(String filename) throws IOException {
		ele.docaptureScreenshot(filename);
	}

}
