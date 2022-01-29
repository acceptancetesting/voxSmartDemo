package com.planittesting.cloud.jupiter.pageActions;

import com.planittesting.cloud.jupiter.pageobjects.ContactPage;
import com.planittesting.cloud.jupiter.pageobjects.HomePage;
import com.planittesting.cloud.jupiter.webdriver.drivers.CucumberWebDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static org.awaitility.Awaitility.with;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Slf4j
public class ContactPageActions extends PageActions {

    private CucumberWebDriver webDriver;
    private ContactPage contactPage;

    private static final String REQUIRED_MESSAGE = "required";

    public ContactPageActions(CucumberWebDriver webDriver, ContactPage contactPage) {
        this.webDriver = webDriver;
        this.contactPage = contactPage;
    }

    public void waitForPageToLoad() {
        contactPage.waitForPageToLoad();
    }

    public void enterForename(String foreName) {
        contactPage.getForeNameInput().sendKeys(foreName);
    }

    public void enterSurname(String surName) {
        contactPage.getSurNameInput().sendKeys(surName);
    }

    public void enterEmailId(String emailId) {
        contactPage.getEmailInput().sendKeys(emailId);
    }

    public void enterMessage(String message) {
        contactPage.getMessageInput().sendKeys(message);
    }

    public boolean isForeNameErrorDisplayed() { //ToDo - code revisit required
        try {
            return contactPage.getForenameErrorMessage().isDisplayed() && contactPage.getForenameErrorMessage().getText().contains(REQUIRED_MESSAGE);
        } catch (Exception e) {
            log.error("ForeName error not displayed - {}", e.getMessage());
            return false;
        }
    }

    public boolean isEmailErrorDisplayed() {
        try {
            return contactPage.getEmailErrorMessage().isDisplayed() && contactPage.getEmailErrorMessage().getText().contains(REQUIRED_MESSAGE);
        } catch (Exception e) {
            log.error("Emailid error not displayed - {}", e.getMessage());
            return false;
        }
    }

    public boolean isMessageErrorDisplayed() {
        try {
            return contactPage.getMessageErrorMessage().isDisplayed() && contactPage.getMessageErrorMessage().getText().contains(REQUIRED_MESSAGE);
        } catch (Exception e) {
            log.error("Message error not displayed - {}", e.getMessage());
            return false;
        }
    }

    public void clickSubmit() {
        contactPage.getSubmitButton().click();
    }

    public int getErrorCount() {
        return contactPage.getAllErrorSpans().size();
    }

    public boolean successMessageIsDisplayed() {
        with().atMost(Duration.ofSeconds(30)).pollInterval(Duration.ofSeconds(5)).until(() -> contactPage.getContactSuccessAlert().isDisplayed());
        return contactPage.getContactSuccessAlert().isDisplayed();
    }

    public boolean allMandatoryFieldsHaveError() {
        boolean errorFound = false;
        for (WebElement mandatoryField : contactPage.getRequiredFields()
        ) {
            errorFound = mandatoryField.findElement(By.xpath(".//following-sibling::*[contains(@id,'err')]")).isDisplayed();
            if (!errorFound) {
                log.error("Mandatory Field error not displayed for - {}",
                        mandatoryField.getText());
                return false;
            }
        }
        return errorFound;
    }

}
