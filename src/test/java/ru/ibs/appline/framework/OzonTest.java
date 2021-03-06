package ru.ibs.appline.framework;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.ibs.appline.framework.base.Base;
import ru.ibs.appline.framework.pages.HomePage;


class OzonTest extends Base {
    @ParameterizedTest
    @CsvSource(value = {
            "iphone"
    })
    void oneTest(String word) {
        app.getPage(HomePage.class)
                .checkOpenPage()
                .getHeader()
                .sendPoiskMessedge(word)
                .clickFind()
                .setUpFilter("Цена", "до", "150000")
                .setUpFilter("Высокий рейтинг", "", "click")
                .setUpFilter("Беспроводные", "NFC", "click")
                .turnProduct("Чет",8)
                .getHeader()
                .clickOnBasket()
                .checkAlert()
                .checkBasketPull()
                .checkTextBasketProducts()
                .dellAllFromBasket()
                .checkEmpty()
                .printListOfProduct();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "беспроводные наушники"
    })
    void twoTest(String word) {
        app.getPage(HomePage.class)
                .checkOpenPage()
                .getHeader()
                .sendPoiskMessedge(word)
                .clickFind()
                .setUpFilter("Цена", "до", "50000")
                .setUpFilter("Бренды", "Beats", "click")
                .setUpFilter("Бренды", "Samsung", "click")
                .setUpFilter("Бренды", "Xiaomi", "click")
                .setUpFilter("Высокий рейтинг", "", "click")
                .turnProduct("Нечет",0)
                .getHeader()
                .clickOnBasket()
                .checkAlert()
                .checkBasketPull()
                .checkTextBasketProducts()
                .dellAllFromBasket()
                .checkEmpty()
                .printListOfProduct();
    }
}
