package com.planittesting.cloud.jupiter.pageobjects;

import com.planittesting.cloud.jupiter.businessObject.CartItem;
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
public class CartPage extends PageObject {

    @FindBy(xpath = "//tr[contains(@class,'cart-item')]")
    List<WebElement> itemsInCart;

    public CartPage(CucumberWebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void waitForPageToLoad() {
        this.waitForElementToAppear(itemsInCart.get(0), SIXTY_SECONDS);
    }

    public List<CartItem> getAllItems() {
        List<CartItem> allItems = new ArrayList<>();
        try {
            int count = 0;
            for (WebElement item : itemsInCart) {
                String title = item.findElement(By.xpath(".//td[1]")).getText();
                String price = item.findElement(By.xpath(".//td[2]")).getText();
                Integer quantity = Integer.parseInt(item.findElement(By.xpath(".//td[3]//input")).getAttribute("value"));
                String subTotal = item.findElement(By.xpath(".//td[4]")).getText();
                WebElement action = item.findElement(By.xpath(".//td[5]//a"));
                allItems.add(count++, new CartItem(title,price,quantity,subTotal,action));
            }
        } catch (Exception e) {
            log.error("Getting product list failed with error - {}", e.getMessage());
            throw new RuntimeException("Getting product list failed");
        }
        return allItems;
    }

}
