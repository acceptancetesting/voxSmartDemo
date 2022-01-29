package com.planittesting.cloud.jupiter.pageActions;

import com.planittesting.cloud.jupiter.businessObject.Product;
import com.planittesting.cloud.jupiter.pageobjects.ContactPage;
import com.planittesting.cloud.jupiter.pageobjects.ShopPage;
import com.planittesting.cloud.jupiter.webdriver.drivers.CucumberWebDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static org.awaitility.Awaitility.with;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Slf4j
public class ShopPageActions extends PageActions {

    private CucumberWebDriver webDriver;
    private ShopPage shopPage;

    private static final String REQUIRED_MESSAGE = "required";
    private List<Product> allProducts = new ArrayList<>();

    public ShopPageActions(CucumberWebDriver webDriver, ShopPage shopPage) {
        this.webDriver = webDriver;
        this.shopPage = shopPage;
    }

    public void waitForPageToLoad() {
        shopPage.waitForPageToLoad();
    }

    private void getAllProducts() {
        this.allProducts = shopPage.getAllProducts();
    }

    public void buyProduct(String productTitle, Integer quantity) {
        getAllProducts();
        Optional<Product> productFound = this.allProducts.stream()
                .filter(product -> product.getTitle().equalsIgnoreCase(productTitle)).findFirst();
        if (!productFound.isPresent()) {
            throw new RuntimeException("Product not found with title " + productTitle);
        } else {
            for (int i = 0; i < quantity; i++) {
                with().atMost(Duration.ofSeconds(10)).pollInterval(Duration.ofSeconds(2)).until(() -> productFound.get().getBuyButton().isEnabled());
                productFound.get().getBuyButton().click();
            }
        }
    }
}
