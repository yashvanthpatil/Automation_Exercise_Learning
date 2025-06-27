Feature: Verify the Login with valid and invalid credentinals

  Background: 
    Given User launches the browser
    And Navigates to the application URL
    Then Home page should be visible with title "Automation Exercise"
    When User clicks on SignupORLogin Tab

  Scenario: Signup to verify the login
  Given signup with the new user
    
  Scenario: Verify that new user can login account and Delete the Account successfully with valid creds
    Given user landed to login page
    When user enter correct "Email" address and "Password" from sheetName "SignupAccountInformation"
    And Click login button
    Then Verify that Logged in as username is visible
    And Click Delete Account button
    Then Verify that "ACCOUNT DELETED!" message is visible
    And User clicks on Continue button

  Scenario Outline: Login User with incorrect email and password
    When user enter invalid mail "<invalidemail>" address and invalid password "<invalidpassword>"
    And Click login button
    Then Verify error "Your email or password is incorrect!" is visible

    Examples: 
      | invalidemail   | invalidpassword |
      | jhon@gmail.com | Jhon@123        |

  Scenario Outline: : Logout User
    Given Verify loginformname "Login to your account" is visible
    When user enter correct "<validemail>" address and "<validpassword>"
    And Click login button
    Then Verify that "Logged in as <username>" is visible
    And Click Logout button
    Then Verify that user is navigated to login page

    Examples: 
      | validemail      | validpassword | username |
      | yash4@gmail.com | yash4@1234    | yash    |
      
     Scenario: Register with the existing mail and check the error message
     Given enter existing mail id "yash4@gmail.com" and name "yash4" for signup
     And click signup button and check error message
     Then Verify emailId exist already error 'Email Address already exist!' is visible 
