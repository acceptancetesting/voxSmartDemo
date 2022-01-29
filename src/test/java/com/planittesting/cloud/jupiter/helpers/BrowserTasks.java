package com.planittesting.cloud.jupiter.helpers;

import com.planittesting.cloud.jupiter.webdriver.drivers.CucumberWebDriver;
import io.cucumber.core.api.Scenario;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class BrowserTasks {

    private CucumberWebDriver webDriver;

    public BrowserTasks(CucumberWebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void takeScreenShot(Scenario scenario, String description) {
        webDriver.takeScreenShot(scenario, description);
    }
}
