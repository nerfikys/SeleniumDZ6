package ru.ibs.appline.framework.base;

import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.ibs.appline.framework.manager.DriverManager;

public class Screenshot implements AfterTestExecutionCallback {

    @Attachment(value = "ScreenShotOfFail", type = "image/png", fileExtension = ".png")
    public byte[] getScreenShot() {
        return ((TakesScreenshot) DriverManager.getINSTANCE().getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        if (extensionContext.getExecutionException().isPresent()) {
            getScreenShot();
        }
    }
}