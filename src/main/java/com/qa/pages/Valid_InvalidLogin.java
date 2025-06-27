package com.qa.pages;

import com.qa.utilities.Elements;
import com.qa.utilities.ReadConfig;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Map;

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

	private WebDriver driver;
	private Elements ele;
	private ReadConfig readconfig;

	public Valid_InvalidLogin(WebDriver driver) {
		this.driver = driver;
		this.ele = new Elements(driver);
		this.readconfig = new ReadConfig();
	}

	public String loginFormName() {
		ele.dowaitForVisibility(loginformName);
		return ele.dogetText(loginformName);
	}

	public void EnterLoginDetails(String LoginEmail, String Loginpassword) {
		ele.dosendKeys(loginemailfield, LoginEmail);
		ele.dosendKeys(loginpasswordfield, Loginpassword);
	}

	public void ClickLoginButton() {
		ele.dowaitForVisibility(loginButton);
		ele.doclick(loginButton);
	}

	public String LoggedInUserdisplay() {
		ele.dowaitForVisibility(loginusername);
		return ele.dogetText(loginusername);
	}

	public void ClickDeleteAccount() {
		ele.dowaitForVisibility(DeleteAccount);
		ele.doclick(DeleteAccount);
	}

	public String AccountDeletedMessage() {
		ele.dowaitForVisibility(AccDeletedMessage);
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
		ele.doclick(continueButtn);
	}

	public void clickLogout() {
		ele.dowaitForVisibility(LogoutTab);
		ele.doclick(LogoutTab);
	}
	
	public boolean ExistingMailidErrdisplayed() {
		ele.dowaitForVisibility(existingMailErrormsg);
		return ele.doisDisplayed(existingMailErrormsg);
		
	}
	
	public String MailIdexistMessage() {
		ele.dowaitForVisibility(existingMailErrormsg);
		return ele.dogetText(existingMailErrormsg);
	}

	public void ScreenShot(String filename) throws IOException {
		ele.docaptureScreenshot(filename);
	}
	
}
