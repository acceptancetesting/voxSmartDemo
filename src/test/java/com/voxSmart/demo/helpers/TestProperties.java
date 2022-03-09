package com.voxSmart.demo.helpers;

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

    @Value("${home.url}")
    private String HOME_ENDPOINT;

    @Value("${coinmarket.url}")
    private String COIN_MARKET_URL;


    public TestProperties(Environment environment) {
        this.environment = environment;
        this.baseEnv = environment.getProperty("spring.profiles.active");
    }

    public String getEnv() {
        return baseEnv;
    }

}
