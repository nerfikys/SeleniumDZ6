package ru.ibs.appline.framework.manager;

import ru.ibs.appline.framework.pages.BasketPage;
import ru.ibs.appline.framework.pages.HomePage;
import ru.ibs.appline.framework.pages.PoiskPage;

public class PageManager {

    private static PageManager INSTANCE = null;

    private HomePage homePage;
    private BasketPage basketPage;
    private PoiskPage poiskPage;

    private PageManager() {
    }


    public static PageManager getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new PageManager();
        }
        return INSTANCE;
    }

    public void dellPageManager() {
        INSTANCE = null;
    }

    public HomePage getHomePage() {
        if (homePage == null) {
            homePage = new HomePage();
        }
        return homePage;
    }

    public PoiskPage getPoiskPage() {
        if (poiskPage == null) {
            poiskPage = new PoiskPage();
        }
        return poiskPage;
    }

    public BasketPage getBasketPage() {
        if (basketPage == null) {
            basketPage = new BasketPage();
        }
        return basketPage;
    }
}
