package Stepdefinations;

import com.qa.pages.loginORsignupPage;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class SIgnupAndDeleteAccSteps {

	private loginORsignupPage loginRsignup = new loginORsignupPage(DriverFactory.getDriver());

	@Given("User launches the browser")
	public void userLaunchesTheBrowser() {
		System.out.println("user launches " + loginRsignup.browserName() + " browser");
	}

	@And("Navigates to the application URL")
	public void navigatesToTheApplicationURL() {
		loginRsignup.Enterurl();
	}

	@Then("Home page should be visible with title {string}")
	public void homePageShouldBeVisible(String homepageTitle) {
		assertEquals(loginRsignup.GetTitle(), homepageTitle);
	}

	@When("User clicks on SignupORLogin Tab")
	public void userClicksOnSignupLoginButton() {
		loginRsignup.clicksignuporloginTab();
	}

	@Then("New User Signup! section should be visible")
	public void newUserSignupSectionShouldBeVisible() {
		loginRsignup.verifyAllElementsDisplayed();
	}

	@When("User enters {string} and {string} address for signup from sheetname {string}")
	public void user_enters_and_address_for_signup_from_sheetname(String name, String email, String sheetname) {
		String signupname = loginRsignup.DataFromExcel(sheetname, name);
		String Signupemail = loginRsignup.DataFromExcel(sheetname, email);
		loginRsignup.FillSignupFields(signupname, Signupemail);
	}

	@And("Clicks on Signup button")
	public void clicksOnSignupButton() {
		loginRsignup.ClickSignupButton();
	}

	@Then("{string} should be visible")
	public void shouldBeVisible(String EnterAccInfo) {
		assertEquals(loginRsignup.FormHeading(), EnterAccInfo);
	}

	@When("User fills in account information from sheet {string}")
	public void user_fills_in_account_information_from_sheet(String sheetname) {
		String title = loginRsignup.DataFromExcel(sheetname, "Title");
		if (title.equalsIgnoreCase("Mr") || title.equalsIgnoreCase("mr")) {
			loginRsignup.ClickMaleTitleRadioButtons();
		} else if (title.equalsIgnoreCase("Mrs") || title.equalsIgnoreCase("mrs")) {
			loginRsignup.ClickFemaleRadioButton();
		}
		loginRsignup.EnterPasswordField(sheetname, "Password");
		loginRsignup.SelectDOB(sheetname, "DOB");

	}

	@And("Selects newsletter and special offers checkboxes")
	public void selectsNewsletterAndSpecialOffersCheckboxes() {
		loginRsignup.selectCheckboxes();

	}

	@And("Fills in personal address and contact details from sheet {string}")
	public void fills_in_personal_address_and_contact_details_from_sheet(String sheetname) {
		loginRsignup.EnterAdressInformation(sheetname);

	}

	@And("Clicks on Create Account button")
	public void clicksOnCreateAccountButton() {
		loginRsignup.ClickCreateAccButton();
	}

	@Then("{string} creation message should be visible")
	public void messageShouldBeVisible(String AccCreatedMessage) {
		assertEquals(loginRsignup.AccCreatedSuccessmessage(), AccCreatedMessage);
	}

	@When("User clicks on Continue button")
	public void userClicksOnContinueButton() {
		loginRsignup.continueButon().click();
	}

	@Then("Logged in as username should be visible")
	public void loggedInAsUsernameShouldBeVisible() {
		String username = loginRsignup.logedinusername();
		System.out.println(username);
	}

	@When("User clicks on Delete Account button")
	public void userClicksOnDeleteAccountButton() {
		loginRsignup.DeleteAccountButton();
	}

	@Then("{string} deletion message should be visible and click on continue button")
	public void messageShouldBeVisibl(String DeleteConfirmation) {
		assertEquals(loginRsignup.AccDeletedMesage(), DeleteConfirmation);
		loginRsignup.continueButon().click();
		System.out.println(loginRsignup.AccDeletedMesage());
	}

}
