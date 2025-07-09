package Stepdefinations;

import org.testng.Assert;

import com.qa.factory.DriverFactory;
import com.qa.pages.ContactUsFormPage;
import com.qa.pages.loginORsignupPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ContactUsFormSteps {
	
	private ContactUsFormPage contactus  = new ContactUsFormPage(DriverFactory.getDriver());
	private loginORsignupPage ss = new loginORsignupPage(DriverFactory.getDriver());
	
	@Given("Useer landed to homepage and cicks on contact us Tab")
	public void useer_landed_to_homepage_and_cicks_on_contact_us_tab() {
		contactus.clickOnContactUsTab();
	}

	@And("Verify {string} is visible")
	public void verify_is_visible(String getInTouchHeader) {
	  String ActualHeader =  contactus.getInTouchHeader();
	  Assert.assertEquals(ActualHeader,getInTouchHeader);
	}

	@And("Enter name, email, subject and message")
	public void enter_name_email_subject_and_message() {
	    contactus.enterName();
	    contactus.enterEmail();
	    contactus.enterSubject();
	    contactus.enterMessage();
	}

	@Then("Upload file")
	public void upload_file() {
	    contactus.chooseFile();
	}

	@And("Click Submit button")
	public void click_submit_button()  {
	    contactus.clickSubmitButton();
	}

	@When("user Click OK button or accept alert")
	public void user_click_ok_button() {
	    contactus.clickOkButton();
	}

	@And("Verify success message {string} is visible")
	public void verify_success_message_is_visible(String successMessageText) {
		String ActualmessageText = contactus.sucessMessageText();
		contactus.successMessageVerify();
		Assert.assertEquals(ActualmessageText, successMessageText);
	    
	}

	@Then("Click Home button and verify that landed to home page successfully")
	public void click_home_button_and_verify_that_landed_to_home_page_successfully() throws Exception {
	   contactus.cickHomeButton();
	   ss.Takescreenshot("home_page_ss");
	   
	}

}
