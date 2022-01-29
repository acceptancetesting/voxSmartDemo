package com.planittesting.cloud.jupiter.stepdefinitions;

import com.planittesting.cloud.jupiter.helpers.NavigationTasks;
import com.planittesting.cloud.jupiter.helpers.TestProperties;
import com.planittesting.cloud.jupiter.pageActions.CartPageActions;
import com.planittesting.cloud.jupiter.pageActions.ContactPageActions;
import com.planittesting.cloud.jupiter.pageActions.HomePageActions;
import com.planittesting.cloud.jupiter.pageActions.ShopPageActions;
import io.cucumber.java.en.Given;

public class HomePageSteps extends PageSteps {

    private NavigationTasks navigationTasks;
    private HomePageActions homePageActions;
    private TestProperties testProperties;
    private ContactPageActions contactPageActions;
    private CartPageActions cartPageActions;
    private ShopPageActions shopPageActions;

    public HomePageSteps(
            NavigationTasks navigationTasks,
            HomePageActions homePageActions,
            TestProperties testProperties,
            ContactPageActions contactPageActions,
            CartPageActions cartPageActions,
            ShopPageActions shopPageActions) {
        this.navigationTasks = navigationTasks;
        this.homePageActions = homePageActions;
        this.contactPageActions = contactPageActions;
        this.cartPageActions = cartPageActions;
        this.shopPageActions = shopPageActions;
        this.testProperties = testProperties;
    }

    @Given("I open planIT jupiter home page")
    public void theUserIsOnTheLoginPage() {
        navigationTasks.goToURL(testProperties.getBASE_URL() + testProperties.getHOME_ENDPOINT());
        homePageActions.waitForPageToLoad();
    }

    @Given("I click Contact")
    public void openContactForm() {
        homePageActions.clickContactUs();
        contactPageActions.waitForPageToLoad();
    }

    @Given("I click Shop")
    public void openShop() {
        homePageActions.clickShop();
        shopPageActions.waitForPageToLoad();
    }

    @Given("I click view cart")
    public void openCart() {
        homePageActions.clickCart();
        cartPageActions.waitForPageToLoad();
    }
}
