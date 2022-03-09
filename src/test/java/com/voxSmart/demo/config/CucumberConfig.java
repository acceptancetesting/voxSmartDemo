package com.voxSmart.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(
        basePackages = "com.voxSmart.demo")
public class CucumberConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CucumberConfig.class);
    private static final String APPLICATION_PROPERTIES = "application.properties";
    private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    /**
     * static PropertySourcesPlaceholderConfigurer is required for the @Value annotations to work.
     * Must be static
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer properties =
                new PropertySourcesPlaceholderConfigurer();
        final ClassPathResource environmentProperties =
                new ClassPathResource(
                        deriveFileToLoadPropertiesFrom(System.getProperty(SPRING_PROFILES_ACTIVE)));
        final ClassPathResource applicationProperties =
                new ClassPathResource(APPLICATION_PROPERTIES);
        properties.setLocations(new Resource[]{applicationProperties, environmentProperties});
        return properties;
    }

    @Bean
    public RestOperations restTemplate() {
        return new RestTemplate();
    }

    private static String deriveFileToLoadPropertiesFrom(String activeProfile) {
        String env = activeProfile;
        env = env != null ? env : "dev";
        final String propertiesFileLocation =
                "/environment/" + env.toLowerCase() + "/environment.properties";
        LOGGER.debug(String.format("Loading properties from: %s", propertiesFileLocation));
        return propertiesFileLocation;
    }
}
