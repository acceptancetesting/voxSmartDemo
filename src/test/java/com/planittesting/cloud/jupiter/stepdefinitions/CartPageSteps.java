package com.planittesting.cloud.jupiter.stepdefinitions;

import com.planittesting.cloud.jupiter.pageActions.CartPageActions;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;

public class CartPageSteps extends PageSteps {

    private CartPageActions cartPageActions;

    public CartPageSteps(
            CartPageActions cartPageActions) {
        this.cartPageActions = cartPageActions;
    }

    @Given("cart has following items")
    public void cartHasItems(DataTable expectedItems) {
       assertTrueWithMessage("Items expected in cart not found",cartPageActions.doesCartHasItems(expectedItems));
    }

}
