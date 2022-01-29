package com.planittesting.cloud.jupiter.webdriver.drivers;

import io.cucumber.core.api.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;

@Slf4j
public class FirefoxCucumberWebDriver extends FirefoxDriver implements CucumberWebDriver {

    private static final int IS_500 = 500;
    private static final int IS_20 = 20;

    private WebDriverException cause;
    private String screenshotUrl;
    private ScreenshotHelper screenshotHelper = new ScreenshotHelper();

    public FirefoxCucumberWebDriver(FirefoxOptions options) {
        super(options);
        this.screenshotUrl = "";
    }

    public void takeScreenShot(Scenario scenario, String linkText) {

        int count = 0;
        while (!snap(scenario, linkText)) {

            count++;
            if (count > IS_20) {
                log.info("Could not take screenshot", this.cause);
                break;
            }

            try {
                Thread.sleep(IS_500);
            } catch (InterruptedException e) {
                log.info("Waiting for page to load before attempting another screenshot.");
            }
        }
    }

    private boolean snap(Scenario scenario, String linkText) {

        boolean isScreenShotTaken = false;
        try {
            final byte[] screenshot = this.getScreenshotAs(OutputType.BYTES);
            screenshotHelper.embedScreenshotInCucumberReport(scenario, linkText, screenshot);
            isScreenShotTaken = true;

        } catch (
                WebDriverException cause) {
            this.cause = cause;
        }
        return isScreenShotTaken;
    }

    @Override
    public File getScreenshot() {
        return getScreenshotAs(OutputType.FILE);
    }

    @Override
    public String getScreenshotUrl() {
        return this.screenshotUrl;
    }

    @Override
    public String getBrowserName() {
        return getCapabilities().getBrowserName();
    }

    @Override
    public String getBrowserVersion() {

        String browserVersion = getCapabilities().getVersion();

        if (browserVersion.contains(".")) {
            browserVersion = browserVersion.substring(0, browserVersion.indexOf("."));
        }
        return browserVersion;
    }

    @Override
    public String getPlatform() {
        return getCapabilities().getPlatform().family().name();
    }

    @Override
    public String getPlatformVersion() {
        return "" + getCapabilities().getPlatform().getMajorVersion();
    }

    @Override
    public void shutdown() {
    }

    @Override
    public void captureScreenshot() {
        // Call getScreenshotAs method to create image file
        File screenshotFile = getScreenshotAs(OutputType.FILE);
        screenshotHelper.saveScreenshot(screenshotFile);
    }
}
