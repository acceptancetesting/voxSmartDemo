package com.voxSmart.demo.pageobjects;

import com.voxSmart.demo.webdriver.drivers.CucumberWebDriver;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Getter
@Slf4j
public class LandingPage extends PageObject {

    @FindBy(xpath = "//span[contains(text(),'Skip Tutorial')]//ancestor::button")
    WebElement skipTutorialButton;

    @FindBy(xpath = "//div[contains(@class,'popup_header')]//button[contains(@class,'close')]")
    WebElement welcomePopupCloseButton;

    @FindBy(xpath = "//span[@data-key='asideMenuTrade']")
    WebElement tradeButton;

    @FindBy(xpath = "//a[contains(@href,'crypto')]")
    WebElement cryptoLink;

    @FindBy(xpath = "//div[@data-qa='categories__list' and contains(text(),'Favorites')]//ancestor::a[contains(@href,'trade')]")
    WebElement favoritesLink;

    public LandingPage(CucumberWebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void waitForPageToLoad() {
        this.waitForElement(tradeButton, THIRTY_SECONDS);
    }
}
