package com.planittesting.cloud.jupiter.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import static java.lang.System.getProperty;
import static java.lang.System.setProperty;
import static org.apache.commons.lang3.StringUtils.isBlank;

@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        features = "src/test/resources/features/",
        stepNotifications = true,
        glue = {"com.planittesting.cloud.jupiter.stepdefinitions", "com.planittesting.cloud.jupiter.hooks"},
        tags = {"not @wip"},
        plugin = {
                "pretty",
                "html:target/cucumber-html-report",
                "json:target/cucumber-json-report.json"
        })
@ContextConfiguration(locations = {"classpath:cucumber.xml"})
public class JupiterTestRunner {

    @BeforeClass
    public static void setUpBeforeClass() {
        if (isBlank(getProperty("spring.profiles.active"))) {
            throw new RuntimeException("No environment specified");
        } else {
            getBaseEnvironment();
        }
    }

    private static void getBaseEnvironment() {
        String environment = getProperty("spring.profiles.active");
        if(environment.length() > 2) {  //Assuming we have dev, test, prod environment
            setProperty("environment", environment);
        } else {
            setProperty("environment", "dev");
        }
    }
}
