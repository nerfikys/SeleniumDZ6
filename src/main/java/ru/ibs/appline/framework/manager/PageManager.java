package ru.ibs.appline.framework.manager;

import ru.ibs.appline.framework.pages.BasePage;

import java.util.HashMap;
import java.util.Map;

public class PageManager {

    private static PageManager INSTANCE = null;
    private Map<String, BasePage> mapPages = new HashMap<>();

    private PageManager() {
    }

    public static PageManager getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new PageManager();
        }
        return INSTANCE;
    }

    public void dellPageManager() {
        mapPages.clear();
    }

    public <T extends BasePage> T getPage(Class<T> tClass) {
        if (mapPages.isEmpty() || mapPages.get(tClass.getName()) == null) {
            try {
                mapPages.put(tClass.getName(), tClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (T) mapPages.get(tClass.getName());
    }
}
