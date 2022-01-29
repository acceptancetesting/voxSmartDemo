package com.planittesting.cloud.jupiter.pageobjects;

import com.planittesting.cloud.jupiter.businessObject.Product;
import com.planittesting.cloud.jupiter.webdriver.drivers.CucumberWebDriver;
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
public class ShopPage extends PageObject {

    @FindBy(xpath = "//li[contains(@id,'product-')]")
    List<WebElement> allProducts;

    public ShopPage(CucumberWebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void waitForPageToLoad() {
        this.waitForElement(allProducts.get(0), THIRTY_SECONDS);
    }

    public List<Product> getAllProducts() {
        List<Product> allProductList = new ArrayList<>();
        try {
            int count = 0;
            for (WebElement product : allProducts) {
                String title = product.findElement(By.xpath(".//h4[contains(@class,'product-title')]")).getText();
                String price = product.findElement(By.xpath(".//span[contains(@class,'product-price')]")).getText();
                WebElement button = product.findElement(By.xpath(".//a[contains(@class,'btn-success')]"));
                allProductList.add(count++, new Product(title,price,button));
            }
        } catch (Exception e) {
            log.error("Getting product list failed with error - {}", e.getMessage());
            throw new RuntimeException("Getting product list failed");
        }
        return allProductList;
    }
}
