package ru.ibs.appline.framework.pages;


import io.qameta.allure.Allure;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
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

    public byte[] printListOfProduct() {
        Allure.getLifecycle().addAttachment("ProductList", "text/plain", ".txt", dataManager.toString().getBytes(StandardCharsets.UTF_8));
        return dataManager.toString().getBytes(StandardCharsets.UTF_8);
    }

    protected WebElement waitToClickable(WebElement webElement) {
        return wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    protected WebElement waitUtilElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static ExpectedCondition<Boolean> textToBePresentWithoutSpace(final WebElement element,
                                                                         final String text) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    String elementText = element.getText().replaceAll(" ", "");
                    return elementText.contains(text);
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            }

            @Override
            public String toString() {
                return String.format("text ('%s') to be present in element %s", text, element);
            }
        };
    }

    public static ExpectedCondition<Boolean> textToBePresentInTwoPart(final WebElement element,
                                                                      final String text, final String text2) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    String elementText = element.getText();
                    return elementText.contains(text) && elementText.contains(text2);
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            }

            @Override
            public String toString() {
                return String.format("text ('%s','%s') to be present in element %s", text, text2, element);
            }
        };
    }

    public static ExpectedCondition<Boolean> textToBePresentPart3(final WebElement element,
                                                                  final String text, final String text2, final String text3) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    String elementText = element.getText();
                    return elementText.contains(text) && elementText.contains(text2) && elementText.replaceAll(" ", "").contains(text3);
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            }

            @Override
            public String toString() {
                return String.format("text ('%s','%s','%s') to be present in element %s", text, text2, text3, element);
            }
        };
    }

}
