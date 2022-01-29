package com.planittesting.cloud.jupiter.webdriver.environments;

import com.planittesting.cloud.jupiter.helpers.OSDetector;
import com.planittesting.cloud.jupiter.webdriver.drivers.CucumberWebDriver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

@Slf4j
public abstract class RuntimeEnvironment {

    private static Logger LOG = LoggerFactory.getLogger(RuntimeEnvironment.class);

    protected final Environment environment;
    private final String projectBaseDir;
    private Integer vrtVisualHeight;
    private Integer vrtVisualWidth;
    private String harFileName;

    RuntimeEnvironment(Environment environment) {
        this.environment = environment;
        this.projectBaseDir =
                System.getProperty("baseDir") != null
                        ? System.getProperty("baseDir")
                        : System.getProperty("user.dir");
        this.vrtVisualHeight = environment.getProperty("", Integer.class, 1080);
        this.vrtVisualWidth = environment.getProperty("", Integer.class, 1920);
    }


    protected void addProxyCapability(MutableCapabilities options) {
        DesiredCapabilities cap = new DesiredCapabilities();
        Proxy proxy = new Proxy();
        cap.setCapability(CapabilityType.PROXY, proxy);
        options.merge(cap);
    }

    public abstract void initialise();

    public abstract CucumberWebDriver createWebDriver();

    public void shutdown() {
    }

    public abstract String getDriverVersion();

    public Integer getLocallyInstalledVersion() {
        return null;
    }

    private String getProjectBaseDir() {
        return projectBaseDir;
    }

    String getDriverPath(String browser, String driverName) {
        return Paths.get(
                        getProjectBaseDir(),
                        "drivers",
                        OSDetector.getOSFamily(),
                        browser,
                        getDriverVersion(),
                        getExecutableDriver(driverName))
                .toString();
    }

    private String getExecutableDriver(String driverName) {

        StringBuilder name = new StringBuilder(driverName);

        if (OSDetector.isWindows()) {
            name.append(".exe");
        }

        return name.toString();
    }

    synchronized Integer runCommandForResult(List<String> commandParams, String extractionPattern) {

        ProcessBuilder processBuilder = new ProcessBuilder(commandParams);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();

            String commandOutput =
                    IOUtils.toString(process.getInputStream(), Charset.defaultCharset());


            if(commandOutput.contains("No Instance(s) Available")) {
                commandParams.set(3,"name='C:\\\\Program Files\\\\Google\\\\Chrome\\\\Application\\\\chrome.exe'");
                processBuilder = new ProcessBuilder(commandParams);
                processBuilder.redirectErrorStream(true);
                process = processBuilder.start();
                commandOutput =
                        IOUtils.toString(process.getInputStream(), Charset.defaultCharset());
            }

            Pattern compile = Pattern.compile(extractionPattern);
            Matcher matcher = compile.matcher(commandOutput);



            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            } else {
                throw new RuntimeException(
                        "Unable to find a match for extractionPattern '"
                                + extractionPattern
                                + "' run on command output '"
                                + String.join(" ", commandParams)
                                + "'");
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error attempting to run command '" + String.join(" ", commandParams) + "'", e);
        }
    }
}
