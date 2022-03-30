package ru.ibs.appline.framework.steps;

import io.cucumber.java.bg.И;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.ibs.appline.framework.manager.PageManager;
import ru.ibs.appline.framework.pages.HomePage;

public class StepHomePage {

    @FindBy(xpath = "//div[@data-widget='promoNavigation']")
    WebElement promo;

    @И("Проверка что эта страница является домашней")
    public void checkOpenPage() {
        PageManager.getINSTANCE().getPage(HomePage.class).checkOpenPage();
    }
}
