package com.planittesting.cloud.jupiter.pageobjects;

import com.planittesting.cloud.jupiter.webdriver.drivers.CucumberWebDriver;
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
public class ContactPage extends PageObject {

    @FindBy(xpath = "//input[@id='forename']")
    WebElement foreNameInput;

    @FindBy(xpath = "//input[@id='surname']")
    WebElement surNameInput;

    @FindBy(xpath = "//input[@id='email']")
    WebElement emailInput;

    @FindBy(xpath = "//input[@id='telephone']")
    WebElement telephoneInput;

    @FindBy(xpath = "//textarea[@id='message']")
    WebElement messageInput;

    @FindBy(xpath = "//span[@id='forename-err']")
    WebElement forenameErrorMessage;

    @FindBy(xpath = "//span[@id='email-err']")
    WebElement emailErrorMessage;

    @FindBy(xpath = "//span[@id='message-err']")
    WebElement messageErrorMessage;

    @FindBy(xpath = "//div[contains(@class,'form-actions')]/a[contains(text(),'Submit')]")
    WebElement submitButton;

    @FindBy(xpath = "//div[contains(@class,'alert alert-success')]")
    WebElement contactSuccessAlert;

    @FindBy(xpath = "//*[@required!='']")
    List<WebElement> requiredFields;

    @FindBy(xpath = "//span[contains(@id,'-err')]")
    List<WebElement> allErrorSpans;

    public ContactPage(CucumberWebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void waitForPageToLoad() {
        this.waitForEitherElementToAppear(foreNameInput, submitButton, THIRTY_SECONDS);
    }
}
