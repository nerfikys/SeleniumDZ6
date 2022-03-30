package ru.ibs.appline.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.ibs.appline.framework.data.Product;
import ru.ibs.appline.framework.utils.Utils;

import java.util.List;

public class PoiskPage extends BasePage {
    @FindBy(xpath = "//div[contains(@class,'filter')]")
    List<WebElement> filters;

    @FindBy(xpath = "//div[contains(@class,'search-result')]/div/div")
    List<WebElement> listProduct;

    @Step("Установка значения фильтра")
    public PoiskPage setUpFilter(String nameOfFilter, String valueName, String value) {
        boolean flagFind = false;
        wait.until(ExpectedConditions.visibilityOfAllElements(filters));
        for (WebElement element : filters) {
            if (element.getText().contains(nameOfFilter)) {
                Actions action = new Actions(driverManager.getDriver());
                action.moveToElement(element);
                boolean flagFindValue = false;
                if (value.equals("click")) {
                    if (valueName.equals("")) {
                        waitToClickable(element.findElement(By.xpath(".//span"))).click();
                    } else {
                        waitToClickable(element.findElement(By.xpath(".//*[contains(text(),'" + valueName + "')]"))).click();
                    }
                    flagFindValue = true;
                } else if (valueName.equals("от") || valueName.equals("до")) {

                    WebElement inputBox = element.findElement(By.xpath(".//p[contains(text(),'" + valueName + "')]/../input"));
                    inputBox.sendKeys(Keys.chord(Keys.CONTROL, "a"), "" + value);
                    flagFindValue = true;
                } else {
                    Assertions.fail("С такими входными данными нельзя заполнить поля: " + nameOfFilter + " " + valueName + " " + value);
                }
                Assertions.assertTrue(flagFindValue, "У фильтра " + nameOfFilter + " не было найдено поле " + valueName);
                flagFind = true;
                break;
            }
        }
        Assertions.assertTrue(flagFind, "Фильтр " + nameOfFilter + " не был найден");
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(filters)));
        return this;
    }

    @Step("Выбор 8 чётных продуктов")
    public PoiskPage turn8Product() {
        int numbers = 0;
        int i = 1;
        while (numbers < 8) {
            List<WebElement> inBasket = listProduct.get(i * 2 - 1).findElements(By.xpath(".//span[text()='В корзину']"));
            if (inBasket.size() == 2) {
                add(listProduct.get(i * 2 - 1), inBasket.get(0));
                (inBasket.get(1)).click();
                wait.until(ExpectedConditions.invisibilityOf(inBasket.get(1)));
                numbers++;
            } else {
                if (!inBasket.get(0).findElement(By.xpath("./../../../../..//b")).getText().contains("час")) {
                    add(listProduct.get(i * 2 - 1), inBasket.get(0));
                    (inBasket.get(0)).click();
                    wait.until(ExpectedConditions.invisibilityOf(inBasket.get(0)));
                    numbers++;
                }
                if (driverManager.getDriver().findElements(By.xpath("//div[@data-widget='container']")).size() == 2) {
                    driverManager.getDriver().findElements(By.xpath("//div[@data-widget='container']")).get(1).findElement(By.xpath("/../../../div/div/button")).click();
                }
            }
            i++;
        }
        return this;
    }

    @Step("Выбор всех нечётных продуктов")
    public PoiskPage turnAllProduct() {
        int numbers = 0;
        int i = 0;
        while (i * 2 < listProduct.size()) {
            List<WebElement> inBasket = listProduct.get(i * 2).findElements(By.xpath(".//span[text()='В корзину']"));
            if (inBasket.size() == 2) {
                add(listProduct.get(i * 2), inBasket.get(0));
                (inBasket.get(1)).click();
                wait.until(ExpectedConditions.invisibilityOf(inBasket.get(1)));
                numbers++;
            } else if (!inBasket.get(0).findElement(By.xpath("./../../../../..//b")).getText().contains("час")) {
                add(listProduct.get(i * 2), inBasket.get(0));
                (inBasket.get(0)).click();
                wait.until(ExpectedConditions.invisibilityOf(inBasket.get(0)));
                numbers++;
            }
            if (driverManager.getDriver().findElements(By.xpath("//div[@data-widget='container']")).size() == 2) {
                driverManager.getDriver().findElements(By.xpath("//div[@data-widget='container']")).get(1).findElement(By.xpath("/../../../div/div/button")).click();
            }
            i++;
        }
        return this;
    }

    private void add(WebElement product, WebElement inBasket) {
        String title = product.findElement(By.xpath(".//a/span")).getText();
        List<WebElement> spans = inBasket.findElements(By.xpath("./../../../../../../..//span"));
        int value = 0;
        if (spans.get(0).getText().contains("−")) {
            value = Utils.convertToInteger(spans.get(1).getText());
        } else {
            value = Utils.convertToInteger(spans.get(0).getText());
        }
        dataManager.addProduct(new Product(title, value));
    }
}

