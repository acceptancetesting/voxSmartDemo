package com.voxSmart.demo.businessObject;

import lombok.Data;
import lombok.Getter;
import org.openqa.selenium.WebElement;

import java.util.Currency;

@Getter
public class Product {
    private String title;
    private String price;
    private WebElement buyButton;

    public Product(String title, String price, WebElement buyButton) {
        this.title = title;
        this.price = price;
        this.buyButton = buyButton;
    }

}
