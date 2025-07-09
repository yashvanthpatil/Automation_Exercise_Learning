Feature: Verify the contact us form

Background: 
    Given User launches the browser
    And Navigates to the application URL
    Then Home page should be visible with title "Automation Exercise"
    
    Scenario: Testing the contact us form with fileupload and feedbacks
    Given Useer landed to homepage and cicks on contact us Tab
    And Verify 'GET IN TOUCH' is visible
    And Enter name, email, subject and message
    Then Upload file
    And Click Submit button
    When user Click OK button or accept alert
    And Verify success message 'Success! Your details have been submitted successfully.' is visible
    Then Click Home button and verify that landed to home page successfully
	