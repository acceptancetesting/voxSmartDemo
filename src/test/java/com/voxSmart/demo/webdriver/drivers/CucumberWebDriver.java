package com.voxSmart.demo.webdriver.drivers;

import io.cucumber.core.api.Scenario;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public interface CucumberWebDriver extends WebDriver {

    Logger LOG = LoggerFactory.getLogger(CucumberWebDriver.class);

    void takeScreenShot(Scenario scenario, String linkText);

    File getScreenshot();

    String getScreenshotUrl();

    String getBrowserName();

    String getBrowserVersion();

    String getPlatform();

    String getPlatformVersion();

    void shutdown();

    /**
     * Captures screenshot to set directory for FT reporting purposes
     */
    void captureScreenshot();

    default void destroy() {
        LOG.debug("Destroying the browser instance");

        manage().deleteAllCookies();
        quit();
        shutdown();
    }
}
