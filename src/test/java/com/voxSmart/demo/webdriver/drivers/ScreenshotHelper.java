package com.voxSmart.demo.webdriver.drivers;

import io.cucumber.core.api.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriverException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Slf4j
@Component
public class ScreenshotHelper {


    private static final String IMAGE_PNG = "image/png";
    private static final String AUTOMATION_CAPTURE_PATH = "/automation/screenshots/capture.png";

    void embedScreenshotInCucumberReport(Scenario scenario, String linkText, final byte[] screenshot) {
        scenario.write(linkText);
        scenario.embed(screenshot, IMAGE_PNG);
    }

    void saveScreenshot(File screenshotFile) {
        boolean folderCreated = false;
        boolean folderExists = false;

        try {
            log.info("Attempting to capture screenshot");

            // Move image file to new destination
            File browserImgFile = new File(AUTOMATION_CAPTURE_PATH);

            if (browserImgFile.getParentFile() != null) {
                folderExists = browserImgFile.getParentFile().exists();
                if (!folderExists) {
                    folderCreated = browserImgFile.getParentFile().mkdirs();
                }
            }

            if (folderExists || folderCreated) {
                FileUtils.copyFile(screenshotFile, browserImgFile);
                log.info("Screenshot successfully captured at : {}", AUTOMATION_CAPTURE_PATH);
            } else {
                log.error("Failed to create folder");
            }
        } catch (IOException | WebDriverException e) {
            log.error("Failed to capture screenshot - " + e);
        }
    }
}
