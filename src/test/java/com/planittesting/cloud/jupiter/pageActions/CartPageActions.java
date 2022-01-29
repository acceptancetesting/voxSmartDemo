package com.planittesting.cloud.jupiter.pageActions;

import com.planittesting.cloud.jupiter.businessObject.CartItem;
import com.planittesting.cloud.jupiter.pageobjects.CartPage;
import com.planittesting.cloud.jupiter.webdriver.drivers.CucumberWebDriver;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Slf4j
public class CartPageActions extends PageActions {

    private CucumberWebDriver webDriver;
    private CartPage cartPage;

    private List<CartItem> allItemsInCart = new ArrayList<>();

    public CartPageActions(CucumberWebDriver webDriver, CartPage cartPage) {
        this.webDriver = webDriver;
        this.cartPage = cartPage;
    }

    public void waitForPageToLoad() {
        cartPage.waitForPageToLoad();
    }

    private void getAllItemsInCart() {
        this.allItemsInCart = cartPage.getAllItems();
    }

    public boolean doesCartHasItems(DataTable expectedItems) {
        List<Map<String, String>> rows = expectedItems.asMaps(String.class, String.class);
        getAllItemsInCart();
        List<CartItem> itemsMatched = new ArrayList<>();
        for (Map<String, String> row : rows) {
            Optional<CartItem> itemFound = this.allItemsInCart.stream()
                    .filter(item -> item.getTitle().equalsIgnoreCase(row.get("title")) &&
                            item.getQuantity() == Integer.parseInt(row.get("quantity"))).findAny();
            if(itemFound.isPresent()) itemsMatched.add(itemFound.get());
        }
       return this.allItemsInCart.containsAll(itemsMatched);
    }
}
