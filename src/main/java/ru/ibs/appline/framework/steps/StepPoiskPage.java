package ru.ibs.appline.framework.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.bg.И;
import ru.ibs.appline.framework.manager.PageManager;
import ru.ibs.appline.framework.pages.PoiskPage;

import java.util.List;

public class StepPoiskPage {

    @И("Установка значений фильтров:")
    public void setUpFilter(DataTable dataTable) {
        List<List<String>> table = dataTable.asLists();
        for (List<String> x : table) {
            PageManager.getINSTANCE().getPage(PoiskPage.class).setUpFilter(x.get(0),
                    x.get(1).equals("none") ? "" : x.get(1),
                    x.get(2));
        }
    }


    @И("^Выбор (\\d+) продуктов с выбранной опцией: \"(.+)\"$")
    public void turnProduct(int number,String option) {
        PageManager.getINSTANCE().getPage(PoiskPage.class).turnProduct(option, number);
    }

}

