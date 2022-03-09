package com.voxSmart.demo.pageActions;

import com.voxSmart.demo.businessObject.Cryptos;
import com.voxSmart.demo.pageobjects.CryptoPage;
import com.voxSmart.demo.webdriver.drivers.CucumberWebDriver;
import lombok.With;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static org.awaitility.Awaitility.with;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Slf4j
public class CryptoPageActions extends PageActions {

    public static final String SELECTED_FAV_CLASS = "watchlist-entity.component_icon_accent__2nGRy";
    private CucumberWebDriver webDriver;
    private CryptoPage cryptoPage;

    private List<Cryptos> allCryptos = new ArrayList<>();

    public CryptoPageActions(CucumberWebDriver webDriver, CryptoPage cryptoPage) {
        this.webDriver = webDriver;
        this.cryptoPage = cryptoPage;
    }

    public void waitForPageToLoad() {
        cryptoPage.waitForPageToLoad();
    }

    private void getAllCryptos() {
        this.allCryptos = cryptoPage.getAllCryptos();
    }

    public List<Cryptos> selectFavCryptos(Integer noOfCryptos) {
        List<Cryptos> favouritesSelected = new ArrayList<>();
        getAllCryptos();
        Integer cryptosFound = allCryptos.size();
        if (cryptosFound < noOfCryptos) {
            throw new IllegalArgumentException(String.format("Expected to select %d, but only %d available", noOfCryptos, cryptosFound));
        } else {
            List<Integer> randomList= IntStream.range(0, noOfCryptos)
                    .boxed()
                    .collect(Collectors.toList());
            Collections.shuffle(randomList); //Select random numbers but unique
            IntStream.range(0, noOfCryptos).forEach(n -> {
                Cryptos favCrypto = allCryptos.get(randomList.get(n));
                cryptoPage.moveToElement(favCrypto.getFavButton());
                cryptoPage.jsClick(favCrypto.getFavButton());
                with().atMost(Duration.ofSeconds(10)).pollInterval(Duration.ofSeconds(1)).until(() -> {
                    String currentClass = favCrypto.getFavButton().getAttribute("class");
                    log.info(currentClass);
                    return currentClass.contains(SELECTED_FAV_CLASS);   //Wait for fav to be selected
                });
                favouritesSelected.add(n,favCrypto);
            });
        }
        return favouritesSelected;
    }

    public boolean doesFavoriteContain(List<Cryptos> expectedCryptos) {
        getAllCryptos();
        if(allCryptos.containsAll(expectedCryptos)) {
           return true;
        } else {
            List<Cryptos> result = allCryptos.stream().distinct()
                    .filter(expectedCryptos::contains)
                    .collect(Collectors.toList());
            List<Cryptos> differences = expectedCryptos.stream()
                    .filter(element -> !result.contains(element))
                    .collect(Collectors.toList());
            log.error("Cryptos not found in favorites : {}", differences.toArray().toString());
            return false;
        }
    }

//    public boolean doesCartHasItems(DataTable expectedItems) {
//        List<Map<String, String>> rows = expectedItems.asMaps(String.class, String.class);
//        getAllCryptos();
//        List<Cryptos> itemsMatched = new ArrayList<>();
//        for (Map<String, String> row : rows) {
//            Optional<Cryptos> itemFound = this.allItemsInCart.stream()
//                    .filter(item -> item.getSymbol().equalsIgnoreCase(row.get("title")) &&
//                            item.getBuyPrice() == Integer.parseInt(row.get("quantity"))).findAny();
//            if(itemFound.isPresent()) itemsMatched.add(itemFound.get());
//        }
//       return this.allItemsInCart.containsAll(itemsMatched);
//    }
}
