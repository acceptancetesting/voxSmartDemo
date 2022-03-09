package com.voxSmart.demo.testData;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Slf4j
public class FakeDataGenerator {
    private Faker testData = new Faker(new Locale("en-GB"));

    public TestSignUpAccount getFakeSignUpAccountDetails() {
        log.debug("Generating fake test account");
        return new TestSignUpAccount(testData.name().firstName(),testData.name().lastName(),testData.internet().emailAddress(),testData.phoneNumber().cellPhone());
    }

}
