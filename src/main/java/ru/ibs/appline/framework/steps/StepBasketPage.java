package ru.ibs.appline.framework.steps;

import io.cucumber.java.bg.И;
import ru.ibs.appline.framework.manager.PageManager;
import ru.ibs.appline.framework.pages.BasketPage;

public class StepBasketPage {

    @И("Обязательная проверка при первом входе на наличие всплывающего окна и его закрытие")
    public void checkAlert() {
        PageManager.getINSTANCE().getPage(BasketPage.class).checkAlert();
    }

    @И("Проверка что всё из списка присутствует в корзине")
    public void checkBasketPull() {
        PageManager.getINSTANCE().getPage(BasketPage.class).checkBasketPull();
    }

    @И("Проверяем текст \"Ваша корзина N товаров\"")
    public void checkTextBasketProducts() {
        PageManager.getINSTANCE().getPage(BasketPage.class).checkTextBasketProducts();
    }

    @И("Удалить всё из корзины")
    public void dellAllFromBasket() {
        PageManager.getINSTANCE().getPage(BasketPage.class).dellAllFromBasket();
    }

    @И("Проверка что корзина пуста")
    public void checkEmpty() {
        PageManager.getINSTANCE().getPage(BasketPage.class).checkEmpty();
    }
}
