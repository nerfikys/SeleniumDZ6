package ru.ibs.appline.framework;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.ibs.appline.framework.base.Base;


class OzonTest extends Base {
    @ParameterizedTest
    @CsvSource(value = {
            "iphone"
    })
    void oneTest(String word) {
        app.getHomePage()
                .checkOpenPage()
                .getHeader()
                .sendPoiskMessedge(word)
                .clickFind()
                .setUpFilter("Цена", "до", "150000")
                .setUpFilter("Высокий рейтинг", "", "click")
                .setUpFilter("Беспроводные", "NFC", "click")
                .turn8Product()
                .getHeader()
                .clickOnBasket()
                .checkAlert()
                .checkBasketPull()
                .checkTextBasketProducts(8)
                .dellAllFromBasket()
                .checkEmpty()
                .printListOfProduct();
    }
}
