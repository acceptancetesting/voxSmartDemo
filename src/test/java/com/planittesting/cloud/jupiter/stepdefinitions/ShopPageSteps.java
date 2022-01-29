package com.planittesting.cloud.jupiter.stepdefinitions;

import com.planittesting.cloud.jupiter.helpers.TestProperties;
import com.planittesting.cloud.jupiter.pageActions.ContactPageActions;
import com.planittesting.cloud.jupiter.pageActions.ShopPageActions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class ShopPageSteps extends PageSteps {

    private ShopPageActions shopPageActions;
    private TestProperties testProperties;

    public ShopPageSteps(
            ShopPageActions shopPageActions, TestProperties testProperties) {
        this.shopPageActions = shopPageActions;
        this.testProperties = testProperties;
    }

    @Given("I click to buy button {int} times on {string}")
    public void buyProducts(Integer quantity, String product) {
        shopPageActions.waitForPageToLoad();
        shopPageActions.buyProduct(product, quantity);
    }

}
