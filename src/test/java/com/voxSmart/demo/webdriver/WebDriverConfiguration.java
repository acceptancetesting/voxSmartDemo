package com.voxSmart.demo.webdriver;

import com.voxSmart.demo.webdriver.drivers.CucumberWebDriver;
import com.voxSmart.demo.webdriver.environments.RuntimeEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class WebDriverConfiguration {

    private Logger LOG = LoggerFactory.getLogger(WebDriverConfiguration.class);
    private Environment environment;

    public WebDriverConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean(destroyMethod = "destroy")
    public CucumberWebDriver cucumberWebDriver() {
        LOG.debug("Starting webdriver instance");
        return DriverFactory.getInstance(environment);
    }

    @Bean
    public RuntimeEnvironment getRuntimeEnvironment() {
        return DriverFactory.getRuntimeEnvironment();
    }
}
