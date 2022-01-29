package com.planittesting.cloud.jupiter.webdriver.environments;

import com.planittesting.cloud.jupiter.helpers.OSDetector;
import com.planittesting.cloud.jupiter.webdriver.drivers.CucumberWebDriver;
import com.planittesting.cloud.jupiter.webdriver.drivers.FirefoxCucumberWebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import static java.util.Arrays.asList;

public class FirefoxRuntimeEnvironment extends RuntimeEnvironment {

    private static Logger LOG = LoggerFactory.getLogger(FirefoxRuntimeEnvironment.class);

    public FirefoxRuntimeEnvironment(Environment environment) {
        super(environment);
    }

    @Override
    public void initialise() {
        setDriver();
    }

    @Override
    public CucumberWebDriver createWebDriver() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(true);
        profile.setPreference("acceptSslCerts", "true");
        profile.setPreference("acceptInsecureCerts", "true");
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        this.addProxyCapability(options);
        return new FirefoxCucumberWebDriver(options);
    }

    private void setDriver() {
        String driverPath = getDriverPath("firefox", "geckodriver");

        if (getLocallyInstalledVersion() < 53) {
            System.setProperty("webdriver.firefox.marionette", driverPath);
        } else {
            System.setProperty("webdriver.gecko.driver", driverPath);
        }
    }

    @Override
    public String getDriverVersion() {
        return "0.24.0";
    }

    @Override
    public Integer getLocallyInstalledVersion() {

        switch (OSDetector.getOSFamily()) {
            case OSDetector.LINUX:
                return runCommandForResult(asList("firefox", "-version"), "(\\d*)\\.");
            case OSDetector.WINDOWS:
                return runCommandForResult(
                        asList(
                                "wmic",
                                "datafile",
                                "where",
                                "name='C:\\\\Program Files\\\\Mozilla Firefox\\\\firefox.exe'",
                                "get",
                                "Version",
                                "/value"),
                        "=(\\d*)\\.");
            case OSDetector.MAC:
                return runCommandForResult(
                        asList("/Applications/Firefox.app/Contents/MacOS/firefox", "--version"),
                        "(\\d*)\\.");
            default:
                throw new RuntimeException("Unsupported OS!");
        }
    }
}
