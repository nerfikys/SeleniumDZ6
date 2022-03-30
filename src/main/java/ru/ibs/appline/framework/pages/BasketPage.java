package ru.ibs.appline.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.ibs.appline.framework.data.Product;
import ru.ibs.appline.framework.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class BasketPage extends BasePage {

    @FindBy(xpath = "//span[contains(text(),'Добавить компанию')]")
    WebElement alert;

    @FindBy(xpath = "//div[contains(@id,'split-Main')]/div")
    List<WebElement> mainList;

    @FindBy(xpath = "//section[@data-widget='total']/div/div/div/span")
    List<WebElement> totalInfo;

    @FindBy(xpath = "//span[contains(text(),'Удалить выбранные')]")
    WebElement dell;

    public BasketPage checkAlert() {
        waitUtilElementToBeVisible(alert);
        waitToClickable(alert.findElements(By.xpath("./../../../../..//button")).get(1)).click();
        return this;
    }

    public BasketPage checkBasketPull() {
        wait.until(ExpectedConditions.visibilityOfAllElements(mainList));
        List<Product> productList = (ArrayList) dataManager.getProductArrayList().clone();
        List<Product> productList2 = new ArrayList<>();
        List<WebElement> main = new ArrayList<>();
        List<WebElement> main2 = new ArrayList<>();
        main.addAll(mainList);
        main.remove(0);
        for (WebElement x : main) {
            for (Product product : productList) {
                if (x.findElement(By.xpath("./div/a")).getText().split("\n")[0].equals(product.getTitle())) {
                    if (Utils.convertToInteger(x.findElements(By.xpath("./div//span[contains(text(),'₽')]")).get(1).getText()).equals(product.getPrice())) {
                        productList2.add(product);
                        main2.add(x);
                        break;
                    }
                }
            }
        }
        Assertions.assertTrue(main2.containsAll(main) && main.size() == main2.size(), "На странице остались элементы которых нет в списке");
        Assertions.assertTrue(productList2.containsAll(productList) && productList.containsAll(productList2) && productList.size() == productList2.size(), "В списке остались элементы которых нет на странице");
        return this;
    }

    public BasketPage checkTextBasketProducts() {
        int number = dataManager.getNumber();
        Assertions.assertTrue(totalInfo.get(0).getText().contains("Ваша корзина"), "Не найден текст 'Ваша корзина'");
        Assertions.assertTrue(totalInfo.get(1).getText().contains(number + " товаров"), "Число товаров не совпало ожидалось: " + number + ", а на самом деле: " + totalInfo.get(1).getText().split(" ")[0]);
        return this;
    }

    public BasketPage dellAllFromBasket() {
        waitToClickable(dell).click();
        waitToClickable(driverManager.getDriver().findElement(By.xpath("//div[contains(text(),'Удаление товаров')]/..//button"))).click();
        return this;
    }

    public BasketPage checkEmpty() {
        Assertions.assertEquals("Корзина пуста", driverManager.getDriver().findElement(By.xpath("//h1")).getText(), "На странице не найден необходимый текст");
        return this;
    }
}
