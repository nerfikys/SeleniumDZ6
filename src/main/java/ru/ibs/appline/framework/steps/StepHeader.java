package ru.ibs.appline.framework.steps;

import io.cucumber.java.bg.И;
import ru.ibs.appline.framework.manager.PageManager;
import ru.ibs.appline.framework.pages.BasePage;
import ru.ibs.appline.framework.pages.HomePage;

public class StepHeader {

    @И("^Отправка \"(.+)\" в строку поиска$")
    public StepHeader sendPoiskMessedge(String words) {
        PageManager.getINSTANCE().getPage(HomePage.class).getHeader().sendPoiskMessedge(words);
        return this;
    }

    @И("Распечатка отчёта")
    public void printListOfProduct() {
        PageManager.getINSTANCE().getPage(BasePage.class).printListOfProduct();
    }

    @И("Нажатие на кнопку поиска")
    public void clickFind() {
        PageManager.getINSTANCE().getPage(HomePage.class).getHeader().clickFind();
    }

    @И("Нажатие на корзину")
    public void clickOnBasket() {
        PageManager.getINSTANCE().getPage(HomePage.class).getHeader().clickOnBasket();
    }

}