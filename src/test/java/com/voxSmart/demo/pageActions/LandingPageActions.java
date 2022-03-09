package com.voxSmart.demo.pageActions;

import com.voxSmart.demo.businessObject.Cryptos;
import com.voxSmart.demo.pageobjects.LandingPage;
import com.voxSmart.demo.webdriver.WebDriverConfiguration;
import com.voxSmart.demo.webdriver.drivers.CucumberWebDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.ws.WebEndpoint;

import java.util.List;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Slf4j
public class LandingPageActions extends PageActions {

    private CucumberWebDriver webDriver;
    private LandingPage landingPage;


    public LandingPageActions(CucumberWebDriver webDriver, LandingPage landingPage) {
        this.webDriver = webDriver;
        this.landingPage = landingPage;
    }

    public void waitForPageToLoad() {
        landingPage.waitForPageToLoad();
    }

    public void openTrading() {
        closePopUpIfPresent();
        skipTutorial();
        landingPage.getTradeButton().click();
    }

    public void closePopUpIfPresent() {
        landingPage.getWelcomePopupCloseButton().click();
    }

    public void skipTutorial() {
        landingPage.getSkipTutorialButton().click();
    }

    public void openCryptoLink() {
        WebElement cryptoLink = landingPage.getCryptoLink();
        landingPage.moveToElement(cryptoLink);
        cryptoLink.click();
    }

    public void openFavourites() {
        WebElement favouritesLink = landingPage.getFavoritesLink();
        landingPage.moveToElement(favouritesLink);
        favouritesLink.click();
    }
}
