package com.voxSmart.demo.pageActions;

import com.voxSmart.demo.pageobjects.HomePage;
import com.voxSmart.demo.webdriver.drivers.CucumberWebDriver;
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

    public void clickTryFreeDemo() {
        homePage.moveToElementAndClick(homePage.getTryDemoButton());
    }

}
