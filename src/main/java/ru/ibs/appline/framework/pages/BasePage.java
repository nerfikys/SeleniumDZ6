package ru.ibs.appline.framework.pages;


import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ibs.appline.framework.manager.DataManager;
import ru.ibs.appline.framework.manager.DriverManager;
import ru.ibs.appline.framework.manager.PageManager;
import ru.ibs.appline.framework.manager.TestPropManager;
import ru.ibs.appline.framework.pages.blocks.Header;
import ru.ibs.appline.framework.utils.PropsConst;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class BasePage {

    protected final TestPropManager props = TestPropManager.getINSTANCE();
    protected DriverManager driverManager = DriverManager.getINSTANCE();
    protected DataManager dataManager = DataManager.getINSTANCE();
    protected PageManager pageManager = PageManager.getINSTANCE();
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(),
            Duration.ofSeconds(Integer.parseInt(props.getProperty(PropsConst.DURATION_TIMEOUT))));


    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    @FindBy(xpath = "//header")
    protected WebElement header;
    public Header getHeader() {
        return new Header(header);
    }

    @Step("Распечатка отчёта")
    @Attachment(value = "ProductList",type = "text/plain",fileExtension = ".txt")
    public byte[] printListOfProduct(){
       return dataManager.toString().getBytes(StandardCharsets.UTF_8);
    }

    protected WebElement waitToClickable(WebElement webElement) {
        WebElement x = wait.until(ExpectedConditions.elementToBeClickable(webElement));
        return x;
    }

    protected WebElement waitUtilElementToBeVisible(WebElement element) {
        WebElement x = wait.until(ExpectedConditions.visibilityOf(element));
        return x;
    }
    public static void waits(int milss){
        try {
            Thread.sleep(milss);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
