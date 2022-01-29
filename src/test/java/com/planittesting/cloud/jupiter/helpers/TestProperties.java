package com.planittesting.cloud.jupiter.helpers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("TestProperties")
@Getter
@Slf4j
public class TestProperties {
    private final Environment environment;
    private final String baseEnv;

    @Value("${base.http.url}")
    private String BASE_URL;

    @Value("${jupiter.home.url}")
    private String HOME_ENDPOINT;

    @Value("${jupiter.shop.url}")
    private String SHOP_ENDPOINT;

    @Value("${jupiter.contact.url}")
    private String CONTACT_ENDPOINT;

    @Value("${valid.test.email}")
    private String VALID_EMAIL_ID;

    @Value("${invalid.test.email}")
    private String INVALID_EMAIL_ID;

    public TestProperties(Environment environment) {
        this.environment = environment;
        this.baseEnv = environment.getProperty("spring.profiles.active");
    }

    public String getEnv() {
        return baseEnv;
    }

}
