package ru.ibs.appline.framework.manager;


import java.time.Duration;

import static ru.ibs.appline.framework.utils.PropsConst.*;

public class InitManager {

    private static final TestPropManager props = TestPropManager.getINSTANCE();
    private static final DriverManager driverManager = DriverManager.getINSTANCE();


    public static void initFramework() {
        driverManager.getDriver().manage().window().maximize();
        driverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(props.getProperty(IMPLICITLY_WAIT))));
        driverManager.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(props.getProperty(PAGE_LOAD_TIMEOUT))));
        driverManager.getDriver().get(TestPropManager.getINSTANCE().getProperty(BASE_URL));

    }

    public static void quitFramework() {
        driverManager.quitDriver();
        PageManager.getINSTANCE().dellPageManager();
        DataManager.getINSTANCE().dellDataManager();
    }
}