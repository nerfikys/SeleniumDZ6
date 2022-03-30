package ru.ibs.appline.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(xpath = "//div[@data-widget='promoNavigation']")
    WebElement promo;

    public HomePage checkOpenPage() {
        Assertions.assertEquals("Есть промокод?", waitUtilElementToBeVisible(promo).findElement(By.xpath("./p")).getText(), "Не был найден элемент с необходимым текстом, присущий домашней странице");
        return this;
    }
}
