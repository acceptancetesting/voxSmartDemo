package com.voxSmart.demo.businessObject;

import lombok.Getter;
import org.openqa.selenium.WebElement;

@Getter
public class Cryptos {
    private String symbol;
    private String sellPrice;
    private String buyPrice;
    private String percentageChange;
    private WebElement buyButton;
    private WebElement sellButton;
    private WebElement infoButton;
    private WebElement favButton;

    public Cryptos(String symbol, String sellPrice, String buyPrice,
                   WebElement buyButton, WebElement sellButton,String percentageChange, WebElement infoButton, WebElement favButton) {
        this.symbol = symbol;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.percentageChange = percentageChange;
        this.buyButton = buyButton;
        this.sellButton = sellButton;
        this.infoButton = infoButton;
        this.favButton =favButton;
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        // Check if o is an instance of Cart or not
        if (!(o instanceof Cryptos)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Cryptos c = (Cryptos) o;

        // Compare the data members and return accordingly
        return this.symbol.equalsIgnoreCase(c.symbol);
    }

}
