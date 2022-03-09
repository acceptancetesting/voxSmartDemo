package com.voxSmart.demo.state;

import com.voxSmart.demo.businessObject.Cryptos;
import com.voxSmart.demo.helpers.TestProperties;
import com.voxSmart.demo.webdriver.drivers.CucumberWebDriver;
import com.voxSmart.demo.webdriver.environments.RuntimeEnvironment;
import io.cucumber.core.api.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Slf4j
public class RuntimeState {

    public HashMap<String, String> scenarioVariables = new HashMap<>();
    public HashMap<String, Map<String, String>> scenarioMap = new HashMap<>();
    public List<Cryptos> scenarioCryptos = new ArrayList<>();
    private CucumberWebDriver webDriver;
    private Scenario scenario;
    private RuntimeEnvironment runtimeEnvironment;

    @Autowired
    TestProperties testProperties;

    public RuntimeState(CucumberWebDriver webDriver, RuntimeEnvironment runtimeEnvironment) {
        this.webDriver = webDriver;
        this.runtimeEnvironment = runtimeEnvironment;
    }

    public CucumberWebDriver getWebDriver() {
        return webDriver;
    }

    public Scenario getScenario() {
        if (scenario == null) {
            throw new RuntimeException(
                    "Could not retrieve Cucumber Scenario. Please ensure that a Cucumber Scenario is injected at the start of each test run.");
        }
        return scenario;
    }

    public void setScenario(Scenario inputScenario) {
        scenario = inputScenario;
    }

    public void addVariable(String key, String val) {
        scenarioVariables.put(key, val);
    }

    private String getEnvForUrls() {
        return testProperties.getBaseEnv();
    }

    public String getVariable(String key) {
        String returnVar = scenarioVariables.get(key);
        log.info("Runtime variable requested - " + key + " - " + returnVar);
        return returnVar;
    }

    public void setScenarioCryptos(List<Cryptos> scenarioCryptos) {
        this.scenarioCryptos.clear();
        this.scenarioCryptos.addAll(scenarioCryptos);
    }

    public List<Cryptos> getScenarioCryptos() {
        return this.scenarioCryptos;
    }

    public Map<String, String> getAllScenarioVariables() {
        Map<String, String> sortedMap =
                scenarioVariables.entrySet().stream()
                        .sorted(Comparator.comparingInt(a -> a.getValue().length()))
                        .collect(
                                Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (oldValue, newValue) -> oldValue,
                                        LinkedHashMap::new));

        return sortedMap;
    }

    public RuntimeEnvironment getRuntimeEnvironment() {
        return this.runtimeEnvironment;
    }

    private List<String> getScnearioTag() {
        List<String> scenarioTags = new ArrayList<>();
        try {
            Scenario scenario = getScenario();
            scenarioTags.addAll(scenario.getSourceTagNames().stream().collect(Collectors.toList()));
        } catch (Exception e) {
            log.debug("No scenario found");
        }
        return scenarioTags;
    }
}
