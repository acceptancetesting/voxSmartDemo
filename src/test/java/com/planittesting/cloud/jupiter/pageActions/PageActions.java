package com.planittesting.cloud.jupiter.pageActions;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Slf4j
public class PageActions {
    public String getErrorMessage(WebElement errorElement) {
        return errorElement
                .findElement(By.xpath(".//p[@class='mdc-validation__message']"))
                .getText();
    }
}
