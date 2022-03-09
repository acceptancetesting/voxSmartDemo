package com.voxSmart.demo.stepdefinitions;

import com.voxSmart.demo.pageActions.CryptoPageActions;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;

public class CryptoPageSteps extends PageSteps {

    private CryptoPageActions cryptoPageActions;

    public CryptoPageSteps(
            CryptoPageActions cryptoPageActions) {
        this.cryptoPageActions = cryptoPageActions;
    }

    @Given("select {int} cryptos as favourite")
    public void addFavourites(Integer noOfCryptos) {
      runtimeState.setScenarioCryptos(cryptoPageActions.selectFavCryptos(noOfCryptos));
    }


    @Given("new favourites are listed in all favourites")
    public void checkFavouritesAreListed() {
        assertTrueWithMessage("Expected scenario favourites to be found in favorites but not found, Check logs for differences", cryptoPageActions.doesFavoriteContain(runtimeState.getScenarioCryptos()));
    }

}
