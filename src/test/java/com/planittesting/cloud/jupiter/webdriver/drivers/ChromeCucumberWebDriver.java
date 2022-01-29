package com.planittesting.cloud.jupiter.webdriver.drivers;

import io.cucumber.core.api.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

@Slf4j
public class ChromeCucumberWebDriver extends ChromeDriver implements CucumberWebDriver {

    private String screenshotUrl;

    @Autowired
    private ScreenshotHelper screenshotHelper;

    public ChromeCucumberWebDriver(ChromeOptions options) {
        super(options);
        this.screenshotUrl = "";
    }

    public void takeScreenShot(Scenario scenario, String linkText) {
        final byte[] screenshot = this.getScreenshotAs(OutputType.BYTES);
        screenshotHelper.embedScreenshotInCucumberReport(scenario, linkText, screenshot);
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
