package com.planittesting.cloud.jupiter.hooks;

import com.planittesting.cloud.jupiter.helpers.BrowserTasks;
import com.planittesting.cloud.jupiter.state.RuntimeState;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartBrowserHook {

    private RuntimeState runtimeState;
    private BrowserTasks browserTasks;

    public StartBrowserHook(
            RuntimeState runtimeState,
            BrowserTasks browserTasks) {
        log.debug("Creating new StartBrowserHook instance");

        this.runtimeState = runtimeState;
        this.browserTasks = browserTasks;
    }


    @Before
    public void before(final Scenario scenario) {
        log.debug(
                "Running before on scenario \"{}\" for thread \"{}\"",
                scenario.getName(),
                Thread.currentThread().getName());

        runtimeState.setScenario(scenario);
    }

    @After
    public void after(final Scenario scenario) {
        log.debug(
                "Running after on scenario \"{}\" for thread \"{}\"",
                scenario.getName(),
                Thread.currentThread().getName());
        runtimeState.getWebDriver().manage().deleteAllCookies();
        takeScreenshotOnFailedScenario(scenario);
    }

    private void takeScreenshotOnFailedScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            log.info("Test failed. Taking screenshot to help debug");
            browserTasks.takeScreenShot(scenario, scenario.getName());
        }
    }

}
