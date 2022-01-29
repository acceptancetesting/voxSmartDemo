@validations
Feature: Validations errors are displayed

  Scenario: Check contact page validation errors are displayed
    Given I open planIT jupiter home page
    And I click Contact
    And I click submit
    Then error message is displayed for mandatory fields
    And I enter forename "ForeName"
    And I enter valid email id
    And I enter message "Automation - Feedback message for contact from"
    And I click submit
    Then no error messages are displayed

  Scenario: On valid contact submission, success message is displayed
    Given I open planIT jupiter home page
    And I click Contact
    And I enter forename "ForeName"
    And I enter valid email id
    And I enter message "Automation - Feedback message for contact from"
    And I click submit
    Then success message is displayed

  Scenario: Negative - On Invalid submission error message is displayed for - Forename
    Given I open planIT jupiter home page
    And I click Contact
    And I enter forename "!@#!$"
    And I click submit
    Then error message is displayed for forename

  Scenario: Negative - On Invalid submission error message is displayed for - Email Id
    Given I open planIT jupiter home page
    And I click Contact
    And I enter invalid email id
    And I click submit
    Then error message is displayed for email

  Scenario: Negative - On Invalid submission error message is displayed for - Message
    Given I open planIT jupiter home page
    And I click Contact
    And I enter message "!@#!#!@@!"
    And I click submit
    Then error message is displayed for message
