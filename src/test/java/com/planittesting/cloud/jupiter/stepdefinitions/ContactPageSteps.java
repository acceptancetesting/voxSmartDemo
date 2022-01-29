package com.planittesting.cloud.jupiter.stepdefinitions;

import com.planittesting.cloud.jupiter.helpers.NavigationTasks;
import com.planittesting.cloud.jupiter.helpers.TestProperties;
import com.planittesting.cloud.jupiter.pageActions.ContactPageActions;
import com.planittesting.cloud.jupiter.pageActions.HomePageActions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Test;

public class ContactPageSteps extends PageSteps {

    private ContactPageActions contactPageActions;
    private TestProperties testProperties;

    public ContactPageSteps(
            ContactPageActions contactPageActions, TestProperties testProperties) {
        this.contactPageActions = contactPageActions;
        this.testProperties = testProperties;
    }

    @Given("I click submit")
    public void openContactForm() {
        contactPageActions.clickSubmit();
    }

    @Then("error message is displayed for mandatory fields")
    public void checkErrorForAllMandatoryFields() {
        assertTrueWithMessage("Expected all mandatory fields to have error message but not found", contactPageActions.allMandatoryFieldsHaveError());
    }

    @Then("no error messages are displayed")
    public void noErrorDisplayed() {
        assertTrueWithMessage("Expected no errors to be displayed but found", contactPageActions.getErrorCount() == 0);
    }

    @Then("success message is displayed")
    public void successMessageIsDisplayed() {
        assertTrueWithMessage("Expected success message is displayed", contactPageActions.successMessageIsDisplayed());
    }

    @Then("I enter forename {string}")
    public void enterForename(String foreName) {
        contactPageActions.enterForename(foreName);
    }

    @Then("I enter surname {string}")
    public void enterSurname(String surName) {
        contactPageActions.enterSurname(surName);
    }

    @Then("I enter valid email id")
    public void enterValidEmailId() {
        contactPageActions.enterEmailId(testProperties.getVALID_EMAIL_ID());
    }

    @Then("I enter invalid email id")
    public void enterInValidEmailId() {
        contactPageActions.enterEmailId(testProperties.getINVALID_EMAIL_ID());
    }

    @Then("I enter message {string}")
    public void enterMessage(String message) {
        contactPageActions.enterMessage(message);
    }

    @Then("error message is displayed for forename")
    public void foreNameErrorIsDisplayed() {
        assertTrueWithMessage("Expected Forename error message but not displayed", contactPageActions.isForeNameErrorDisplayed());
    }

    @Then("error message is displayed for email")
    public void emailErrorIsDisplayed() {
        assertTrueWithMessage("Expected EmailId error message but not displayed", contactPageActions.isEmailErrorDisplayed());
    }

    @Then("error message is displayed for message")
    public void messageErrorIsDisplayed() {
        assertTrueWithMessage("Expected Contact Message error message but not displayed", contactPageActions.isMessageErrorDisplayed());
    }
}
