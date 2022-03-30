package ru.ibs.appline.framework;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.ibs.appline.framework.base.Base;
import ru.ibs.appline.framework.manager.DataManager;


class OzonTest extends Base {
    @ParameterizedTest
    @CsvSource(value = {
            "iphone","iphone","iphone"
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
    @ParameterizedTest
    @CsvSource(value = {
            "беспроводные наушники","беспроводные наушники","беспроводные наушники"
    })
    void twoTest(String word) {
        app.getHomePage()
                .checkOpenPage()
                .getHeader()
                .sendPoiskMessedge(word)
                .clickFind()
                //.setUpFilter("Бренды","Beats","click")
                .setUpFilter("Бренды","Samsung","click")
                .setUpFilter("Бренды","Xiaomi","click")
                .setUpFilter("Цена", "до", "50000")
                .setUpFilter("Высокий рейтинг", "", "click")
                .turnAllProduct()
                .getHeader()
                .clickOnBasket()
                .checkAlert()
                .checkBasketPull()
                .checkTextBasketProducts(DataManager.getINSTANCE().getProductArrayList().size())
                .dellAllFromBasket()
                .checkEmpty()
                .printListOfProduct();
    }
}
