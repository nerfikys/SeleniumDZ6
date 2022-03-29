package ru.ibs.appline.framework.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.ibs.appline.framework.manager.InitManager;
import ru.ibs.appline.framework.manager.PageManager;

@ExtendWith(Screenshot.class)
public class BaseTests {

    protected PageManager app = PageManager.getINSTANCE();

    @BeforeEach
    public void before() {
        InitManager.initFramework();
    }

    @AfterEach
    public void after() {
      //  InitManager.quitFramework();
    }

}
