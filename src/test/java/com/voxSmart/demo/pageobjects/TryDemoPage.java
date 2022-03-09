package com.voxSmart.demo.pageobjects;

import com.voxSmart.demo.webdriver.drivers.CucumberWebDriver;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Getter
public class TryDemoPage extends PageObject {

    @FindBy(xpath = "//input[@id='name']")
    WebElement foreNameInput;

    @FindBy(xpath = "//input[@id='lastName']")
    WebElement surNameInput;

    @FindBy(xpath = "//input[@id='email']")
    WebElement emailInput;

    @FindBy(xpath = "//input[@id='phoneInput']")
    WebElement telephoneInput;

    @FindBy(xpath = "//input[@id='GDPR']/following-sibling::label")
    WebElement termsAndConditionsInput;

    @FindBy(xpath = "//input[@id='NewsLetter']/following-sibling::label")
    WebElement newsLetterInput;

    @FindBy(xpath = "//button[@id='avaWidgetSubmit']")
    WebElement createAccountButton;

    @FindBy(xpath = "//article[@id='post-327']")
    WebElement registrationForm;

    public TryDemoPage(CucumberWebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void waitForPageToLoad() {
        this.waitForEitherElementToAppear(foreNameInput, termsAndConditionsInput, THIRTY_SECONDS);
    }
}
