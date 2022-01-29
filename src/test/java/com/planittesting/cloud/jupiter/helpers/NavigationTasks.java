package com.planittesting.cloud.jupiter.helpers;

import com.planittesting.cloud.jupiter.state.RuntimeState;
import com.planittesting.cloud.jupiter.webdriver.drivers.CucumberWebDriver;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class NavigationTasks {
    @Autowired
    RuntimeState runtimeState;

    Logger LOGGER = LoggerFactory.getLogger(NavigationTasks.class);

    private CucumberWebDriver webDriver;

    public NavigationTasks(CucumberWebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void goToURL(String url) {
        webDriver.manage().deleteAllCookies();
        LOGGER.debug("Opening URL : " + url);
        webDriver.get(url);
        runtimeState.addVariable(
                "Browser Details",
                webDriver.getBrowserName()
                        + " "
                        + ((HasCapabilities) webDriver).getCapabilities().getVersion());
    }

    public void goToURLWithPrintScenarioName(String url, String scenarioName) {
        //        webDriver.get("data:text/html,<h1><center>" + scenarioName + "</center></h1>");
        LOGGER.info("Journey URL: {}", url);
        webDriver.get(url);
    }

    public void openNewTab() {
        ((JavascriptExecutor) webDriver).executeScript("window.open();");
    }

    public void switchToTab(Integer tab) {
        ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(tab));
    }

    public void refreshPage() {
        webDriver.navigate().refresh();
    }

    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    public void openDevTools() {
        Robot robot = null;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_F12);
            robot.keyRelease(KeyEvent.VK_F12);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
