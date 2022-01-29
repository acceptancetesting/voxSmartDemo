package com.planittesting.cloud.jupiter.stepdefinitions;

import com.planittesting.cloud.jupiter.state.RuntimeState;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Slf4j
public class PageSteps {

    @Autowired protected RuntimeState runtimeState;

    public void assertContainsWithMessage(String expected, String found) {
        assertTrue(
                "Expected " + expected + " but found " + found,
                found.contains(expected));
    }

    public void assertEqualWithMessage(String expected, String found) {
        assertTrue(
                "Expected " + expected + " but found " + found,
                expected.equalsIgnoreCase(found.replace("\n", "")));
    }

    public void assertNotEqualWithMessage(String expected, String found) {
        assertFalse(
                "Expected " + expected + " to be not equal to " + found,
                expected.equalsIgnoreCase(found));
    }

    public void assertTrueWithMessage(String message, Boolean flag) {
        Assert.assertTrue(message, flag);
    }

}
