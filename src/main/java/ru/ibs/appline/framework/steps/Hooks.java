
package ru.ibs.appline.framework.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import ru.ibs.appline.framework.manager.InitManager;


public class Hooks {

    @Before
    public void before() {
        InitManager.initFramework();
    }

    @After
    public static void After() {
        InitManager.quitFramework();
    }
}