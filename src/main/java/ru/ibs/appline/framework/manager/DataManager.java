package ru.ibs.appline.framework.manager;

import org.junit.jupiter.api.Assertions;
import ru.ibs.appline.framework.data.Product;

import java.util.ArrayList;

public class DataManager {

    public DataManager() {
        this.productArrayList = new ArrayList<>();
    }

    static DataManager INSTANCE = null;

    private ArrayList<Product> productArrayList;


    public static DataManager getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public void dellDataManager() {
        INSTANCE = null;

    }

    public int maxPriceOfBasket() {
        return productArrayList.stream().mapToInt((product) -> product.getPrice()).max().orElse(0);
    }

    public Product getProductByName(String name) {
        Product product = productArrayList.stream().filter(product1 -> product1.getTitle().contains(name)).findFirst().orElse(null);
        if (product == null)
            Assertions.fail("В списке продуктов не был найден продукт с именем " + name);
        return product;
    }

    public void addProduct(Product product) {
        productArrayList.add(product);
    }

    public ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }

    @Override
    public String toString() {
        int max = maxPriceOfBasket();
        String result = "";
        for (Product x : productArrayList) {
            if (x.getPrice().equals(max)) {
                result = result.concat("Этот продукт с наибольшей ценой! Название: " + x.getTitle() + ". Цена: " + x.getPrice() + "\n");
            } else {
                result = result.concat("Название: " + x.getTitle() + ". Цена: " + x.getPrice() + "\n");
            }
        }
        return result;
    }
}
