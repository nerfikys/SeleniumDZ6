package ru.ibs.appline.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.ibs.appline.framework.data.Product;
import ru.ibs.appline.framework.utils.Utils;

import java.time.Duration;
import java.util.List;

public class PoiskPage extends BasePage {
    @FindBy(xpath = "//div[contains(@class,'filter')]")
    List<WebElement> filters;

    @FindBy(xpath = "//div[contains(@class,'search-result')]/div/div")
    List<WebElement> listProduct;

    @FindBy(xpath = "//div[@data-widget='searchResultsSort']")
    WebElement placeWithFilters;

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
                        oneClickFilter(element);
                    } else {
                        oneOfManyClickFilter(element, valueName);
                    }
                    flagFindValue = true;
                } else if (valueName.equals("от") || valueName.equals("до")) {
                    putNumber(element, valueName, value);
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
        checkWait(nameOfFilter, valueName, value);
        return this;
    }

    private void checkWait(String nameOfFilter, String valueName, String value) {
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(filters)));
        if (value.equals("click")) {
            if (valueName.equals("")) {
                wait.until(ExpectedConditions.textToBePresentInElement(placeWithFilters, nameOfFilter));
            } else {
                wait.until(textToBePresentInTwoPart(placeWithFilters, nameOfFilter, valueName));
            }
        } else if (valueName.equals("от") || valueName.equals("до")) {
            wait.until(textToBePresentPart3(placeWithFilters, nameOfFilter, valueName, value));
        }
    }

    private void oneClickFilter(WebElement element) {
        waitToClickable(element.findElement(By.xpath(".//span"))).click();
    }

    private void oneOfManyClickFilter(WebElement element, String valueName) {
        if (element.getText().contains("Посмотреть все")) {
            waitToClickable(element.findElement(By.xpath(".//span/span"))).click();
            waitUtilElementToBeVisible(element.findElement(By.xpath(".//input"))).sendKeys(valueName);
            waitToClickable(element.findElement(By.xpath(".//span[contains(text(),'" + valueName + "')]"))).click();
        } else {
            waitToClickable(element.findElement(By.xpath(".//*[contains(text(),'" + valueName + "')]"))).click();
        }
    }

    private void putNumber(WebElement element, String valueName, String value) {
        WebElement inputBox = element.findElement(By.xpath(".//p[contains(text(),'" + valueName + "')]/../input"));
        inputBox.sendKeys(Keys.chord(Keys.CONTROL, "a"), "" + value, Keys.ENTER);
    }

    @Step("Выбор {number} продуктов с выбранной опцией: {option}")
    public PoiskPage turnProduct(String option, int number) {
        Assertions.assertTrue(number >= 0, "Указанно не корректное число: " + number);
        if (option.equals("Чет")) {
            turnProductFromList(1, number);
        } else if (option.equals("Нечет")) {
            turnProductFromList(2, number);
        } else if (option.equals("Все")) {
            turnProductFromList(0, number);
        } else Assertions.fail("Была указана не известная функция ожидалось Чет/Нечет/Все, а полученна: " + option);
        return this;
    }

    private void turnProductFromList(int znach, int howMuch) {
        int lastChet = 0;
        do {
            int i = 0;
            while (i < listProduct.size()) {
                if((dataManager.getNumber() == howMuch)&&howMuch!=0){return;}
                if ((znach == 1 && ((i+lastChet) % 2 == 0)) || (znach == 2 && ((i+lastChet) % 2 == 1))) {
                    i++;
                    continue;
                }
                clickBasket(listProduct.get(i));
                i++;
            }
            lastChet = (i+lastChet) % 2;
        } while (buttonNext());
    }

    private void clickBasket(WebElement elementProduct) {
        List<WebElement> inBasket = elementProduct.findElements(By.xpath(".//span[text()='В корзину']"));
        if (inBasket.size() == 2) {
          goodClick(inBasket.get(1),elementProduct);
        } else if (inBasket.size() == 1) {
            if (!inBasket.get(0).findElement(By.xpath("./../../../../..//b")).getText().contains("час")) {
                goodClick(inBasket.get(0),elementProduct);
            }
        }
    }

    private void goodClick(WebElement button,WebElement elementProduct){
        Duration temp = driverManager.getDriver().manage().timeouts().getImplicitWaitTimeout();
        driverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        (button).click();
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(getHeader().getWebElementBasketCount(), (dataManager.getNumber() + 1) + ""));
        }catch (TimeoutException e){
            (button).click();
            wait.until(ExpectedConditions.textToBePresentInElement(getHeader().getWebElementBasketCount(), (dataManager.getNumber() + 1) + ""));
        }
        wait.withTimeout(temp);
        add(elementProduct);
    }

    private boolean buttonNext() {
        try {
            driverManager.getDriver().findElement(By.xpath("//div[contains(text(),'Дальше')]")).click();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    private void add(WebElement product) {
        String title = product.findElement(By.xpath(".//a/span")).getText();
        List<WebElement> spans = product.findElements(By.xpath("./div/div/span[contains(text(),'₽')]"));
        int value = 0;
        if (spans.get(0).getText().contains("−")) {
            value = Utils.convertToInteger(spans.get(1).getText());
        } else {
            value = Utils.convertToInteger(spans.get(0).getText());
        }
        dataManager.addProduct(new Product(title, value));
    }
}

