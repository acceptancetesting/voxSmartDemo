package com.voxSmart.demo.stepdefinitions;

import com.voxSmart.demo.helpers.NavigationTasks;
import com.voxSmart.demo.helpers.TestProperties;
import com.voxSmart.demo.pageActions.TryDemoPageActions;
import com.voxSmart.demo.pageActions.HomePageActions;
import io.cucumber.java.en.Given;

public class HomePageSteps extends PageSteps {

    private NavigationTasks navigationTasks;
    private HomePageActions homePageActions;
    private TestProperties testProperties;
    private TryDemoPageActions tryDemoPageActions;


    public HomePageSteps(
            NavigationTasks navigationTasks,
            HomePageActions homePageActions,
            TestProperties testProperties,
            TryDemoPageActions tryDemoPageActions) {
        this.navigationTasks = navigationTasks;
        this.homePageActions = homePageActions;
        this.tryDemoPageActions = tryDemoPageActions;
        this.testProperties = testProperties;
    }

    @Given("I open voxSmart home page")
    public void theUserIsOnTheLoginPage() {
        navigationTasks.goToURL(testProperties.getBASE_URL() + testProperties.getHOME_ENDPOINT());
        homePageActions.waitForPageToLoad();
    }

    @Given("I click Try Free Demo")
    public void clickTryFreeDemo() {
        homePageActions.clickTryFreeDemo();
        tryDemoPageActions.waitForPageToLoad();
    }
}
