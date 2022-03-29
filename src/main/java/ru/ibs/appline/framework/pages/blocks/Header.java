package ru.ibs.appline.framework.pages.blocks;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ibs.appline.framework.manager.DriverManager;
import ru.ibs.appline.framework.manager.PageManager;
import ru.ibs.appline.framework.manager.TestPropManager;
import ru.ibs.appline.framework.pages.BasketPage;
import ru.ibs.appline.framework.pages.PoiskPage;
import ru.ibs.appline.framework.utils.PropsConst;


import java.time.Duration;

public class Header implements WrapsElement {

    private final String webElementBasket = ".//a[@href='/cart']";

    private final String poisk = ".//input[@type]";

    private final String clickPoisk = ".//input[@type]/../..//button";


    private final WebDriverWait wait;


    private final WebElement element;

    public Header(WebElement element) {
        this.element = element;
        WebDriver driver = DriverManager.getINSTANCE().getDriver();
        this.wait = new WebDriverWait(driver,
                Duration.ofSeconds(Integer.parseInt(TestPropManager.getINSTANCE().getProperty(PropsConst.DURATION_TIMEOUT))));
    }
    @Step("Отправка текста в строку поиска")
    public Header SendPoiskMessedge(String words) {
        element.findElement(By.xpath(poisk)).clear();
        element.findElement(By.xpath(poisk)).sendKeys(words);
        return this;
    }
    @Step("Нажатие на кнопку поиска")
    public PoiskPage clickFind() {
        waitToClickable(element.findElement(By.xpath(clickPoisk))).click();
        return PageManager.getINSTANCE().getPoiskPage();
    }
    @Step("Нажатие на корзину")
    public BasketPage clickOnBasket() {
        waitToClickable(element.findElement(By.xpath(webElementBasket))).click();
        return PageManager.getINSTANCE().getBasketPage();
    }

    public WebElement waitToClickable(WebElement webElement) {
        WebElement x = wait.until(ExpectedConditions.elementToBeClickable(webElement));
        return x;
    }

    @Override
    public WebElement getWrappedElement() {
        return null;
    }


}