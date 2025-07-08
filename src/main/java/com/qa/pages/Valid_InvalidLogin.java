package com.qa.pages;

import com.qa.utilities.Elements;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class Valid_InvalidLogin {

	private By loginformName = By.xpath("//div[@class=\"login-form\"]/h2");
	private By loginusername = By.xpath("//a[contains(text(),\" Logged in as \")]");
	private By DeleteAccount = By.xpath("//a[@href=\"/delete_account\"]");
	private By AccDeletedMessage = By.xpath("//h2[@class=\"title text-center\"]/b");
	private By continueButtn = By.xpath("//div/a[contains(text(),'Continue')]");
	private By LogoutTab = By.xpath(" //a[@href=\"/logout\"]");

	private By loginemailfield = By.xpath("//div[@class=\"login-form\"]/form/input[@name=\"email\"]");
	private By loginpasswordfield = By.xpath("//div[@class=\"login-form\"]/form/input[@name=\"password\"]");
	private By loginButton = By.xpath("//div[@class=\"login-form\"]/form/button");
	private By invalidCredsErrormsg = By.xpath("//form[@action=\"/login\"]/p");
	private By existingMailErrormsg = By.xpath("//form[@action=\"/signup\"]/p");

	private Elements ele;
	private static final Logger logger = LogManager.getLogger(Valid_InvalidLogin.class);

	public Valid_InvalidLogin(WebDriver driver) {
		
		this.ele = new Elements(driver);
		
	}

	public String loginFormName() {
		ele.dowaitForVisibility(loginformName);
		
		ele.waitForPageReady();
		ele.retryUntilDisplayed(loginformName, 3);
		return ele.dogetText(loginformName);
	}

	public void EnterLoginDetails(String LoginEmail, String Loginpassword) {
		
		ele.retryUntilDisplayed(loginemailfield, 3);
		ele.dosendKeys(loginemailfield, LoginEmail);
		
		ele.retryUntilDisplayed(loginpasswordfield, 3);
		ele.dosendKeys(loginpasswordfield, Loginpassword);
	}

	public void ClickLoginButton() {
		ele.dowaitForVisibility(loginButton);
		ele.dowaitForClickable(loginButton);
		
		ele.retryUntilDisplayed(loginButton, 3);
		ele.safeClick(loginButton,3);
	}

	public String LoggedInUserdisplay() {
		ele.dowaitForVisibility(loginusername);
		
		ele.waitForPageReady();
		return ele.dogetText(loginusername);
	}

	public void ClickDeleteAccount() {
		ele.dowaitForVisibility(DeleteAccount);
		ele.dowaitForClickable(DeleteAccount);
		
		ele.waitForPageReady();
		ele.safeClick(DeleteAccount,3);
	}

	public String AccountDeletedMessage() {
		ele.dowaitForVisibility(AccDeletedMessage);
		
		ele.waitForPageReady();
		return ele.dogetText(AccDeletedMessage);
	}

	public String InvalidCredsErrorMessage() {
		ele.dowaitForVisibility(invalidCredsErrormsg);
		return ele.dogetText(invalidCredsErrormsg);
	}

	public boolean loginformnamedisplayed() {
		ele.dowaitForVisibility(loginformName);
		return ele.doisDisplayed(loginformName);
	}

	public void clickContinueButton() {
		ele.dowaitForVisibility(continueButtn);
		ele.dowaitForClickable(continueButtn);
		ele.safeClick(continueButtn,3);
	}

	public void clickLogout() {
		ele.dowaitForVisibility(LogoutTab);
		ele.dowaitForClickable(LogoutTab);
		logger.info("..............clicking logout tab.................");
		
		ele.waitForPageReady();
		ele.safeClick(LogoutTab,3);
	}
	
	public boolean ExistingMailidErrdisplayed() {
		ele.dowaitForVisibility(existingMailErrormsg);
		logger.info("..............existing mail id error message for signup.................");
		return ele.doisDisplayed(existingMailErrormsg);
		
	}
	
	public String MailIdexistMessage() {
		ele.dowaitForVisibility(existingMailErrormsg);
		return ele.dogetText(existingMailErrormsg);
	}

	public void ScreenShot(String filename) throws IOException {
		logger.info("..............taking screenshot.................");
		ele.docaptureScreenshot(filename);
	}
	
}
