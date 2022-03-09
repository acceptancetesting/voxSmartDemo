package com.voxSmart.demo.pageActions;

import com.voxSmart.demo.pageobjects.TryDemoPage;
import com.voxSmart.demo.testData.TestSignUpAccount;
import com.voxSmart.demo.webdriver.drivers.CucumberWebDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static org.awaitility.Awaitility.with;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Slf4j
public class TryDemoPageActions extends PageActions {

    private CucumberWebDriver webDriver;
    private TryDemoPage tryDemoPage;

    private static final String REQUIRED_MESSAGE = "required";

    public TryDemoPageActions(CucumberWebDriver webDriver, TryDemoPage tryDemoPage) {
        this.webDriver = webDriver;
        this.tryDemoPage = tryDemoPage;
    }

    public void waitForPageToLoad() {
        tryDemoPage.waitForPageToLoad();
    }

    public void signUpWithTestAccount(TestSignUpAccount testSignUpAccount) {
        enterForename(testSignUpAccount.getFirstName());
        enterSurname(testSignUpAccount.getLastName());
        enterEmailId(testSignUpAccount.getEmailId());
        enterPhoneNumber(testSignUpAccount.getPhoneNumber());
        acceptNewsLetter();
        acceptTermAndConditions();
        clickCreateAccount();
    }

    public void enterForename(String foreName) {
        tryDemoPage.getForeNameInput().sendKeys(foreName);
    }

    public void enterSurname(String surName) {
        tryDemoPage.getSurNameInput().sendKeys(surName);
    }

    public void enterEmailId(String emailId) {
        tryDemoPage.getEmailInput().sendKeys(emailId);
    }

    public void acceptTermAndConditions() {
        tryDemoPage.jsClick(tryDemoPage.getTermsAndConditionsInput());
    }

    public void acceptNewsLetter() {
        tryDemoPage.jsClick(tryDemoPage.getNewsLetterInput());
    }

    public void clickCreateAccount() {
        tryDemoPage.getRegistrationForm().click();
        with().pollInterval(Duration.ofSeconds(5)).atMost(Duration.ofSeconds(30)).until(() -> tryDemoPage.getCreateAccountButton().getAttribute("class").contains("green"));
        tryDemoPage.jsClick(tryDemoPage.getCreateAccountButton());
    }

    public void enterPhoneNumber(String phoneNo) {
        tryDemoPage.getTelephoneInput().sendKeys(phoneNo);
    }

}
