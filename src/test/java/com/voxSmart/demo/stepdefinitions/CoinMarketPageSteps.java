package com.voxSmart.demo.stepdefinitions;

import com.voxSmart.demo.helpers.NavigationTasks;
import com.voxSmart.demo.helpers.TestProperties;
import com.voxSmart.demo.pageActions.CoinMarketPageActions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class CoinMarketPageSteps extends PageSteps {

    private NavigationTasks navigationTasks;
    private CoinMarketPageActions coinMarketPageActions;
    private TestProperties testProperties;


    public CoinMarketPageSteps(
            NavigationTasks navigationTasks,
            CoinMarketPageActions coinMarketPageActions,
            TestProperties testProperties) {
        this.navigationTasks = navigationTasks;
        this.coinMarketPageActions = coinMarketPageActions;
        this.testProperties = testProperties;
    }

    @Given("I open coinmarket home page")
    public void theUserIsOnTheLoginPage() {
        navigationTasks.goToURL(testProperties.getCOIN_MARKET_URL());
        coinMarketPageActions.waitForPageToLoad();
    }

    @Then("page displays {int} coins")
    public void coinsDisplayedAre(Integer count) {
        assertTrueWithMessage("Expected count of coins not displayed", coinMarketPageActions.noOfCoinsDisplayedAre(count));
    }

    @Then("from dropdown I select historical data for one of the currencies")
    public void viewHistoricalData() throws InterruptedException {
        coinMarketPageActions.viewHistoricalData();
    }
}
