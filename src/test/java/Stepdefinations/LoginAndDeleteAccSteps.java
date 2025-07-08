package Stepdefinations;

import com.qa.pages.Valid_InvalidLogin;
import com.qa.pages.loginORsignupPage;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LoginAndDeleteAccSteps {

	private loginORsignupPage signup = new loginORsignupPage(DriverFactory.getDriver());
    private Valid_InvalidLogin login = new Valid_InvalidLogin(DriverFactory.getDriver());

    
    @Given ("signup with the new user")
    public void signup_with_the_new_user() {
      String Name =  signup.DataFromExcel("SignupAccountInformation", "Name");
       String Email = signup.DataFromExcel("SignupAccountInformation", "Email");
        signup.FillSignupFields(Name, Email);
        signup.ClickSignupButton();
        

    	String usertitle = signup.DataFromExcel("SignupAccountInformation", "Title");
		if (usertitle.equalsIgnoreCase("Mr") || usertitle.equalsIgnoreCase("mr")) {
			signup.ClickMaleTitleRadioButtons();
		} else if (usertitle.equalsIgnoreCase("Mrs") || usertitle.equalsIgnoreCase("mrs")) {
			signup.ClickFemaleRadioButton();
		}
		signup.EnterPasswordField("SignupAccountInformation", "Password");
		signup.SelectDOB("SignupAccountInformation", "DOB");

    	signup.selectCheckboxes();
    		// Add adress info sheet name
       signup.EnterAdressInformation("SignupAddressInformation");
        signup.ClickCreateAccButton();
        signup.Clickcontinuebutton();
        System.out.println("Signup completed");
    }

    @Given("user landed to login page")
    public void user_landed_on_login_page() {
       System.out.println("user landed to login page for login and delete acc");
    }

    @When("user enter correct {string} address and {string} from sheetName {string}")
    public void user_enters_correct_credentials(String emailKey, String passwordKey, String sheetName) {
        String Email = signup.DataFromExcel(sheetName, emailKey);
        String Pwd = signup.DataFromExcel(sheetName, passwordKey);
        login.EnterLoginDetails(Email, Pwd);
    }

    @And("Click login button")
    public void click_login_button() {
        login.ClickLoginButton();
    }

    @Then("Verify that Logged in as username is visible")
    public void verify_logged_in_as_username() {
    	String username = signup.DataFromExcel("SignupAccountInformation", "Name");
        Assert.assertEquals("Logged in as "+username, signup.logedinusername());
    }

    @And("Click Delete Account button")
    public void click_delete_account_button() {
        signup.DeleteAccountTab();
    }

    @Then("Verify that {string} message is visible")
    public void verify_account_deleted_message(String AccountDeletedexpectedMessage) {
        Assert.assertEquals(AccountDeletedexpectedMessage, signup.AccDeletedMesage());
    }

    @When("user enter invalid mail {string} address and invalid password {string}")
    public void enter_invalid_credentials(String invalidEmail, String invalidPassword) {
       login.EnterLoginDetails(invalidEmail, invalidPassword);
    }

    @Then("Verify error {string} is visible")
    public void verify_login_error(String expectedError) {
        Assert.assertEquals(login.InvalidCredsErrorMessage(),expectedError);
    }
    
    @Given("Verify loginformname {string} is visible")
    public void verify_loginformname_is_visible(String string) {
        login.loginformnamedisplayed();
    }

    @When("user enter correct {string} address and {string}")
    public void user_enters_valid_credentials(String validEmail, String validPassword) {
        login.EnterLoginDetails(validEmail, validPassword);
    }

    @Then("Verify that {string} is visible")
    public void verify_logged_in_message(String expectedloggedinuser) {
        Assert.assertEquals(signup.logedinusername(), expectedloggedinuser);
    }

    @And("Click Logout button")
    public void click_logout_button() {
        login.clickLogout();
    }

    @Then("Verify that user is navigated to login page")
    public void verify_user_navigated_to_login_page() {
    	String curenturl = signup.GetCurrentUrl();
        System.out.println("After logout the url is "+curenturl);
    }
    @Given ("enter existing mail id {string} and name {string} for signup")
    public void enter_existing_mail_id_and_name_for_signup(String email, String name){
    	signup.FillSignupFields(name, email);
    }
    @And("click signup button and check error message")
    public void click_signup_button_and_check_error_message() {
    	signup.ClickSignupButton();
    }
    @Then("Verify emailId exist already error {string} is visible")
    public void Verify_emailId_exist_already_error_is_visible(String ExistingmailErrorMessage) {
    	login.ExistingMailidErrdisplayed();
    	Assert.assertEquals(login.MailIdexistMessage(), ExistingmailErrorMessage);
    	
    }
}

