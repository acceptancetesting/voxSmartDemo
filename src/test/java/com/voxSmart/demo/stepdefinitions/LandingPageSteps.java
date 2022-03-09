package com.voxSmart.demo.stepdefinitions;

import com.voxSmart.demo.helpers.TestProperties;
import com.voxSmart.demo.pageActions.LandingPageActions;
import io.cucumber.java.en.Given;

public class LandingPageSteps extends PageSteps {

    private LandingPageActions landingPageActions;
    private TestProperties testProperties;

    public LandingPageSteps(
            LandingPageActions landingPageActions, TestProperties testProperties) {
        this.landingPageActions = landingPageActions;
        this.testProperties = testProperties;
    }

    @Given("on landing page I open trading menu")
    public void openTradingApp() {
        landingPageActions.waitForPageToLoad();
        landingPageActions.openTrading();
    }

    @Given("I select crypto link from menu")
    public void openCryptoLink() {
        landingPageActions.openCryptoLink();
    }

    @Given("I open my favourites on trading page")
    public void openFavourites() {
        landingPageActions.openFavourites();
    }

}
