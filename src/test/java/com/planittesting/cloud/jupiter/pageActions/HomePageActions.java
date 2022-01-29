package com.planittesting.cloud.jupiter.pageActions;

import com.planittesting.cloud.jupiter.pageobjects.HomePage;
import com.planittesting.cloud.jupiter.webdriver.drivers.CucumberWebDriver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Slf4j
public class HomePageActions extends PageActions {

    private CucumberWebDriver webDriver;
    private HomePage homePage;

    public HomePageActions(CucumberWebDriver webDriver, HomePage homePage) {
        this.webDriver = webDriver;
        this.homePage = homePage;
    }

    public void waitForPageToLoad() {
        homePage.waitForPageToLoad();
    }

    public void clickContactUs() {
        homePage.moveToElementAndClick(homePage.getContactUsLink());
    }

    public void clickShop() {
        homePage.getShopLink().click();
    }
    public void clickCart() {
        homePage.getCartLink().click();
    }

    public void clickHome() {
        homePage.getHomeLink().click();
    }

}
