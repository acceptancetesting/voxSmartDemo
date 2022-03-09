package com.voxSmart.demo.pageobjects;

import com.voxSmart.demo.webdriver.drivers.CucumberWebDriver;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Getter
public class CoinMarketPage extends PageObject {

    @FindBy(xpath = "//span[contains(text(),'Trending')]")
    WebElement trendingSpan;

    @FindBy(xpath = "//table[contains(@class,'cmc')]")
    WebElement coinTable;

    @FindBy(xpath = "//table[contains(@class,'cmc')]//tbody//tr")
    List<WebElement> allCoins;

    @FindBy(xpath = "//a[@href='/currencies/bitcoin/historical-data/']")
    WebElement historicalDataButton;

    public CoinMarketPage(CucumberWebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void waitForPageToLoad() {
        this.waitForEitherElementToAppear(coinTable, allCoins.get(0), TEN_SECONDS);
    }

    public List<WebElement> getListedCoins() {
        new WebDriverWait(this.getWebDriver(), 20).until(ExpectedConditions.elementToBeClickable(coinTable));
        return getCoinTable().findElements(By.xpath(".//tbody//tr"));
    }
}
