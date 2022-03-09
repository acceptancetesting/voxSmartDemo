package com.voxSmart.demo.pageobjects;

import com.voxSmart.demo.businessObject.Cryptos;
import com.voxSmart.demo.webdriver.drivers.CucumberWebDriver;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Getter
@Slf4j
public class CryptoPage extends PageObject {

    @FindBy(xpath = "//table[@data-qa='table__container']")
    WebElement cryptoTable;

    @FindBy(xpath = "//table[@data-qa='table__container']//tbody//tr")
    List<WebElement> allCryptos;

    public CryptoPage(CucumberWebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void waitForPageToLoad() {
        this.waitForElementToAppear(cryptoTable, THIRTY_SECONDS);
    }

    public List<Cryptos> getAllCryptos() {
        List<Cryptos> allItems = new ArrayList<>();
        try {
            int count = 0;
            for (WebElement crypto : allCryptos) {
                this.moveToElement(crypto);
                String symbol = crypto.findElement(By.xpath(".//td[1]")).getText();
                String sellPrice = crypto.findElement(By.xpath(".//td[2]")).getText();
                WebElement sellButton = crypto.findElement(By.xpath(".//td[3]//span[@data-key='watchlistEntitySell']"));
                WebElement buyButton = crypto.findElement(By.xpath(".//td[3]//span[@data-key='watchlistEntityBuy']"));
                String buyPrice = crypto.findElement(By.xpath(".//td[4]")).getText();
                String percentageChange = crypto.findElement(By.xpath(".//td[5]")).getText();
                WebElement infoButton = crypto.findElement(By.xpath(".//td[6]//i[@data-qa='watchlist-entity__info-icon-cell']"));
                WebElement favButton = crypto.findElement(By.xpath(".//td[6]//i[@data-qa='watchlist-entity__favorite-icon-cell-set']"));
                allItems.add(count++, new Cryptos(symbol, sellPrice, buyPrice, sellButton, buyButton, percentageChange, infoButton, favButton));
            }
        } catch (Exception e) {
            log.error("Getting crypto list failed with error - {}", e.getMessage());
            throw new RuntimeException("Getting product list failed");
        }
        return allItems;
    }

}
