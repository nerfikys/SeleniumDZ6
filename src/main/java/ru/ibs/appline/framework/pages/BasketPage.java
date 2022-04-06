package ru.ibs.appline.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.ibs.appline.framework.data.Product;
import ru.ibs.appline.framework.utils.Utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BasketPage extends BasePage {

    @FindBy(xpath = "//span[contains(text(),'Добавить компанию')]")
    WebElement alert;

    @FindBy(xpath = "//div[contains(@id,'split-Main')]/div/div/a/span")
    List<WebElement> mainListTitle;

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
        wait.until(ExpectedConditions.visibilityOfAllElements(mainListTitle));
        List<Product> productList = dataManager.getProductArrayList();
        Collections.sort(productList);
        List<Product> productList2 = new ArrayList<>();
        for (WebElement x : mainListTitle) {
            productList2.add(new Product(x.getText(),
                    Utils.convertToInteger(x.findElement(By.xpath("./../../../div[3]//span")).getText())));
        }
        Collections.sort(productList2);
        ArrayList<Product> productArrayList3 = new ArrayList<>();
        ArrayList<Product> productArrayList4 = new ArrayList<>();
        if (!productList.equals(productList2)) {
            productArrayList3.addAll(productList);
            productArrayList3.removeAll(productList2);
            if (productList.size() == productList2.size()) {
                productArrayList4.addAll(productList2);
                productArrayList4.removeAll(productList);
                Assertions.fail("Со страницы не получилось собрать идентичный список продуктов, найденные различия: \nПервый список: " + productArrayList3 + "\nВторой список: " + productArrayList4);
            }
        }
        Assertions.assertTrue(productArrayList3.isEmpty(), "Со страницы не получилось собрать идентичный список продуктов, не были найдены(вероятно они были удалены): " + productArrayList3);
        return this;
    }

    public BasketPage checkTextBasketProducts() {
        int number = dataManager.getNumber();
        Assertions.assertTrue(totalInfo.get(0).getText().contains("Ваша корзина"), "Не найден текст 'Ваша корзина'");
        Assertions.assertTrue(totalInfo.get(1).getText().contains(number + " товар"), "Число товаров не совпало ожидалось: " + number + ", а на самом деле: " + totalInfo.get(1).getText());
        return this;
    }

    public BasketPage dellAllFromBasket() {
        waitToClickable(dell).click();
        waitToClickable(driverManager.getDriver().findElement(By.xpath("//div[contains(text(),'Удаление товаров')]/..//button"))).click();
        return this;
    }

    public BasketPage checkEmpty() {
        try {
            Duration temp = driverManager.getDriver().manage().timeouts().getImplicitWaitTimeout();
            driverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(driverManager.getDriver().findElement(By.xpath("//h1[contains(text(),'Корзина пуста')]"))));
            wait.withTimeout(temp);
        } catch (TimeoutException e) {
            Assertions.fail("На странице не найден необходимый текст: 'Корзина пуста'");
        }
        return this;
    }
}
