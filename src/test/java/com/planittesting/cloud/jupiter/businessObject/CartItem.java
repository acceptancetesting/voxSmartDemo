package com.planittesting.cloud.jupiter.businessObject;

import lombok.Getter;
import org.openqa.selenium.WebElement;

@Getter
public class CartItem {
    private String title;
    private String price;
    private Integer quantity;
    private String subTotal;
    private WebElement action;

    public CartItem(String title, String price,Integer quantity, String subTotal, WebElement action) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.subTotal = subTotal;
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        // Check if o is an instance of Cart or not
        if (!(o instanceof CartItem)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        CartItem c = (CartItem) o;

        // Compare the data members and return accordingly
        return this.title.equalsIgnoreCase(c.title) && this.price.equalsIgnoreCase(c.price)
                && this.subTotal.equalsIgnoreCase(c.subTotal) && this.quantity == c.quantity;
    }

}
