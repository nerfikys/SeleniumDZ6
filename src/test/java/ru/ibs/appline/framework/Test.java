package ru.ibs.appline.framework;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.ibs.appline.framework.base.BaseTests;


public class Test extends BaseTests {
    @ParameterizedTest
    @CsvSource(value = {
            "iphone"
    })
    public void oneTest(String word) {
        app.getHomePage()
                .checkOpenPage()
                .getHeader()
                .SendPoiskMessedge(word)
                .clickFind()
                //  .setUpFilter("Сроки","До 2 дней","true")
                //  .setUpFilter("Доставка","","true")
                //  .setUpFilter("Бренды","Apple","false")
                //  .setUpFilter("Premium","","true")
                // .setUpFilter("Бестселлеры","","true")
                //  .setUpFilter("со скидкой","","true")
                .setUpFilter("Цена", "до", "150000")
                //  .setUpFilter("Продавец","OZON","true")
                //   .setUpFilter("Оперативная","4-8","click")
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
