package com.planittesting.cloud.jupiter.pageobjects;

import com.planittesting.cloud.jupiter.webdriver.drivers.CucumberWebDriver;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Getter
public class HomePage extends PageObject {

    @FindBy(xpath = "//li[@id,'nav-home']/a")
    WebElement homeLink;

    @FindBy(xpath = "//li[@id='nav-shop']/a")
    WebElement shopLink;

    @FindBy(xpath = "//li[@id='nav-cart']/a")
    WebElement cartLink;

    @FindBy(xpath = "//li[@id='nav-contact']/a")
    WebElement contactUsLink;

    public HomePage(CucumberWebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void waitForPageToLoad() {
        this.waitForEitherElementToAppear(homeLink, shopLink, TEN_SECONDS);
    }
}
