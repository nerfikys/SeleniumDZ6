package ru.ibs.appline.framework.pages;

import io.qameta.allure.Step;
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

    @Step("Обязательная проверка при первом входе на наличие всплывающего окна и его закрытие")
    public BasketPage checkAlert() {
        waitUtilElementToBeVisible(alert);
        waitToClickable(alert.findElements(By.xpath("./../../../../..//button")).get(1)).click();
        return this;
    }


//    @Step("Проверка что всё из списка присутствует в корзине2")
//    public BasketPage checkBasketPull2() {
//        wait.until(ExpectedConditions.visibilityOfAllElements(mainList));
//        for (Product product : dataManager.getProductArrayList()) {
//            boolean flagNotFind = true;
//            for (int i = 1; i < mainList.size(); i++) {
//                if (mainList.get(i).findElement(By.xpath("./div/a")).getText().split("\n")[0].equals(product.getTitle())) {
//                    if (Utils.convertToInteger(mainList.get(i).findElements(By.xpath("./div//span[contains(text(),'₽')]")).get(1).getText()).equals(product.getPrice())) {
//                        flagNotFind = false;
//                        break;
//                    }
//                }
//            }
//            Assertions.assertFalse(flagNotFind, "Продукт " + product.getTitle() + " и с ценой " + product.getPrice() + " не был найден в корзине");
//        }
//        return this;
//    }
//
//    @Step("Проверка что всё из списка присутствует в корзине")
//    public BasketPage checkBasketPull() {
//        wait.until(ExpectedConditions.visibilityOfAllElements(mainList));
//        List<Product> productList = (ArrayList) dataManager.getProductArrayList().clone();
//        List<Product> productList2 = new ArrayList<>();
//        List<WebElement> main = new ArrayList<>();
//        List<WebElement> main2 = new ArrayList<>();
//        main.addAll(mainList);
//        main.remove(0);
//        for (WebElement x : main) {
//            for (Product product : productList) {
//                if (x.findElement(By.xpath("./div/a")).getText().split("\n")[0].equals(product.getTitle())) {
//                    if (Utils.convertToInteger(x.findElements(By.xpath("./div//span[contains(text(),'₽')]")).get(1).getText()).equals(product.getPrice())) {
//                        productList2.add(product);
//                        main2.add(x);
//                        break;
//                    }
//                }
//            }
//        }
//        Assertions.assertTrue(main2.containsAll(main) && main.containsAll(main2) && main.size() == main2.size(), "На странице остались элементы которых нет в списке");
//        Assertions.assertTrue(productList2.containsAll(productList) && productList.containsAll(productList2) && productList.size() == productList2.size(), "В списке остались элементы которых нет на странице");
//        return this;
//    }

    @Step("Проверка что всё из списка присутствует в корзине")
    public BasketPage checkBasketPull() {
        wait.until(ExpectedConditions.visibilityOfAllElements(mainList));
        List<Product> productList = dataManager.getProductArrayList();
        List<Product> productList2 = new ArrayList<>();
        List<WebElement> main = mainList.subList(1, mainList.size());
        boolean flagNotFind = true;
        for (WebElement x : main) {
            for (Product product : productList) {
                if (x.findElement(By.xpath("./div/a")).getText().split("\n")[0].equals(product.getTitle())) {
                    if (Utils.convertToInteger(x.findElements(By.xpath("./div//span[contains(text(),'₽')]")).get(1).getText()).equals(product.getPrice())) {
                        productList2.add(product);
                        flagNotFind = false;
                        break;
                    }
                }
            }
            Assertions.assertFalse(flagNotFind, "Продукт " + x.findElement(By.xpath("./div/a")).getText().split("\n")[0] + " не был найден в списке");
        }
        Assertions.assertTrue(productList2.containsAll(productList) && productList.containsAll(productList2) && productList.size() == productList2.size(), "Со страницы не получилось собрать идентичный список продуктов");
        return this;
    }

    @Step("Проверяем текст \"Ваша корзина n товаров\"")
    public BasketPage checkTextBasketProducts() {
        int number = dataManager.getNumber();
        Assertions.assertTrue(totalInfo.get(0).getText().contains("Ваша корзина"), "Не найден текст 'Ваша корзина'");
        Assertions.assertTrue(totalInfo.get(1).getText().contains(number + " товаров"), "Число товаров не совпало ожидалось: " + number + ", а на самом деле: " + totalInfo.get(1).getText().split(" ")[0]);
        return this;
    }

    @Step("Удалить всё из корзины")
    public BasketPage dellAllFromBasket() {
        waitToClickable(dell).click();
        waitToClickable(driverManager.getDriver().findElement(By.xpath("//div[contains(text(),'Удаление товаров')]/..//button"))).click();
        return this;
    }

    @Step("Проверка что корзина пуста")
    public BasketPage checkEmpty() {
        Assertions.assertEquals("Корзина пуста", driverManager.getDriver().findElement(By.xpath("//h1")).getText(), "На странице не найден необходимый текст");
        return this;
    }
}
