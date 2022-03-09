package com.voxSmart.demo.pageobjects;

import com.voxSmart.demo.webdriver.drivers.CucumberWebDriver;
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

    @FindBy(xpath = "//form[@id='loginForm']")
    WebElement loginInForm;

    @FindBy(xpath = "//a/span[contains(text(),'Try free demo')]")
    WebElement tryDemoButton;

    public HomePage(CucumberWebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void waitForPageToLoad() {
        this.waitForElementToAppear(loginInForm, TEN_SECONDS);
    }
}
