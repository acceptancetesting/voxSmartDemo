package com.voxSmart.demo.pageActions;

import com.voxSmart.demo.pageobjects.CoinMarketPage;
import com.voxSmart.demo.pageobjects.PageObject;
import com.voxSmart.demo.webdriver.drivers.CucumberWebDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static org.awaitility.Awaitility.setDefaultConditionEvaluationListener;
import static org.awaitility.Awaitility.with;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Slf4j
public class CoinMarketPageActions extends PageActions {

    private CucumberWebDriver webDriver;
    private CoinMarketPage coinMarketPage;

    public CoinMarketPageActions(CucumberWebDriver webDriver, CoinMarketPage coinMarketPage) {
        this.webDriver = webDriver;
        this.coinMarketPage = coinMarketPage;
    }

    public void waitForPageToLoad() {
        coinMarketPage.waitForPageToLoad();
    }

    public boolean noOfCoinsDisplayedAre(Integer expectedCount) {
        if (coinMarketPage.getAllCoins().size() == expectedCount) {
            return true;
        } else {
            log.error("Expected count {}, but found {}", expectedCount, coinMarketPage.getAllCoins().size());
            return false;
        }
    }

    public void viewHistoricalData() {
        String xpath = "//table[contains(@class,'cmc')]//tbody//tr[" + ThreadLocalRandom.current().nextInt(1, 5) + "]//td[11]//button"; //Temp dirty fix
        coinMarketPage.jsScrollToElement(coinMarketPage.getCoinTable());
        with().atMost(Duration.ofSeconds(30)).pollInterval(Duration.ofSeconds(1)).until(() -> {
            try {
                WebElement element = coinMarketPage.getWebDriver().findElement(By.xpath(xpath));
                coinMarketPage.moveToElement(element);
                element.click();
                return true;
            } catch (Exception e) {
                log.info(e.getMessage());
                return false;
            }
        });
        openHistoricalData();
    }

    public void openHistoricalData() {
        coinMarketPage.jsClick(coinMarketPage.getHistoricalDataButton());
    }

}
