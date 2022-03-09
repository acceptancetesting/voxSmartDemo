package com.voxSmart.demo.stepdefinitions;

import com.voxSmart.demo.helpers.TestProperties;
import com.voxSmart.demo.pageActions.LandingPageActions;
import com.voxSmart.demo.pageActions.TryDemoPageActions;
import com.voxSmart.demo.pageobjects.LandingPage;
import com.voxSmart.demo.testData.FakeDataGenerator;
import com.voxSmart.demo.testData.TestSignUpAccount;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class TryDemoPageSteps extends PageSteps {

    private TryDemoPageActions tryDemoPageActions;
    private FakeDataGenerator fakeDataGenerator;
    private LandingPageActions landingPageActions;

    public TryDemoPageSteps(
            TryDemoPageActions tryDemoPageActions, LandingPageActions landingPageActions, FakeDataGenerator fakeDataGenerator) {
        this.tryDemoPageActions = tryDemoPageActions;
        this.landingPageActions = landingPageActions;
        this.fakeDataGenerator = fakeDataGenerator;
    }

    @Then("I sign up as new user")
    public void openContactForm() {
        tryDemoPageActions.signUpWithTestAccount(fakeDataGenerator.getFakeSignUpAccountDetails());
        landingPageActions.waitForPageToLoad();
    }
}
