package ru.ibs.appline.framework.manager;

import org.apache.commons.exec.OS;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

import static ru.ibs.appline.framework.utils.PropsConst.*;

public class DriverManager {

    private WebDriver driver;

    private static DriverManager INSTANCE = null;

    private final TestPropManager props = TestPropManager.getINSTANCE();

    private DriverManager() {
    }

    public static DriverManager getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new DriverManager();
        }
        return INSTANCE;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    private void initDriver() {
        if (OS.isFamilyWindows()) {
            initDriverWindowsOsFamily();
        } else if (OS.isFamilyMac()) {
            initDriverMacOsFamily();
        } else if (OS.isFamilyUnix()) {
            initDriverUnixOsFamily();
        }
    }

    private void initDriverWindowsOsFamily() {
        switch (props.getProperty(TYPE_BROWSER)) {
            case "firefox":
                System.setProperty("webdriver.firefox.bin", props.getProperty(PATH_FIREFOX_BIN));
                System.setProperty("webdriver.gecko.driver", props.getProperty(PATH_GECKO_DRIVER_WIDOWS));
                driver = new FirefoxDriver();
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", props.getProperty(PATH_CHROME_DRIVER_WIDOWS));
                driver = new ChromeDriver();
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", props.getProperty(PATH_EDGE_DRIVER_WIDOWS));
                EdgeDriverService service = EdgeDriverService.createDefaultService();
                driver = new EdgeDriver(service);
                break;
            case "opera":
                System.setProperty("webdriver.opera.driver", props.getProperty(PATH_OPERA_DRIVER_WIDOWS));
                driver = new OperaDriver();
                break;
            default:
                Assertions.fail("Типа браузера " + props.getProperty(TYPE_BROWSER) + " не существует во фреймворке");
        }
    }


    private void initDriverMacOsFamily() {
        switch (props.getProperty(TYPE_BROWSER)) {
            case "firefox":
                System.setProperty("webdriver.firefox.bin", props.getProperty(PATH_FIREFOX_BIN));
                System.setProperty("webdriver.gecko.driver", props.getProperty(PATH_GECKO_DRIVER_MAC));
                driver = new FirefoxDriver();
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", props.getProperty(PATH_CHROME_DRIVER_MAC));
                driver = new ChromeDriver();
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", props.getProperty(PATH_EDGE_DRIVER_MAC));
                EdgeDriverService service = EdgeDriverService.createDefaultService();
                driver = new EdgeDriver(service);
                break;
            case "opera":
                System.setProperty("webdriver.opera.driver", props.getProperty(PATH_OPERA_DRIVER_MAC));
                driver = new OperaDriver();
                break;
            default:
                Assertions.fail("Типа браузера " + props.getProperty(TYPE_BROWSER) + " не существует во фреймворке");
        }
    }


    private void initDriverUnixOsFamily() {
        switch (props.getProperty(TYPE_BROWSER)) {
            case "firefox":
                System.setProperty("webdriver.firefox.bin", props.getProperty(PATH_FIREFOX_BIN));
                System.setProperty("webdriver.gecko.driver", props.getProperty(PATH_GECKO_DRIVER_UNIX));
                driver = new FirefoxDriver();
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", props.getProperty(PATH_CHROME_DRIVER_UNIX));
                driver = new ChromeDriver();
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", props.getProperty(PATH_EDGE_DRIVER_UNIX));
                EdgeDriverService service = EdgeDriverService.createDefaultService();
                driver = new EdgeDriver(service);
                break;
            case "opera":
                System.setProperty("webdriver.opera.driver", props.getProperty(PATH_OPERA_DRIVER_UNIX));
                driver = new OperaDriver();
                break;
            default:
                Assertions.fail("Типа браузера " + props.getProperty(TYPE_BROWSER) + " не существует во фреймворке");
        }
    }


    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

}
