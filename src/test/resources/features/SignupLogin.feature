Feature: User Authentication - Signup and Login

  Background: 
    Given User launches the browser
    And Navigates to the application URL
    Then Home page should be visible with title "Automation Exercise"
    When User clicks on SignupORLogin Tab

  Scenario: Verify that new user can sign up successfully with valid credentials
    Then New User Signup! section should be visible
    When User enters "Name" and "Email" address for signup from sheetname "New User Details"
    And Clicks on Signup button
    Then "ENTER ACCOUNT INFORMATION" should be visible
    When User fills in account information from sheet "New User Details"
    And Selects newsletter and special offers checkboxes
    And Fills in personal address and contact details from sheet "NewUser Adress Info"
    And Clicks on Create Account button
    Then "ACCOUNT CREATED!" creation message should be visible
    When User clicks on Continue button
    Then Logged in as username should be visible
    When User clicks on Delete Account button
    Then "ACCOUNT DELETED!" deletion message should be visible and click on continue button
