package ru.ibs.appline.framework.utils;


import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestStepFinished;
import io.qameta.allure.Allure;
import io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm;
import ru.ibs.appline.framework.manager.DriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class CucumberListenerWithScreenshot extends AllureCucumber5Jvm {
    @Override
    public void setEventPublisher(EventPublisher publisher) {
        EventHandler<TestStepFinished> testStepFinishedEventHandler = new EventHandler<TestStepFinished>() {
            @Override
            public void receive(TestStepFinished testStepFinished) {
                if (testStepFinished.getResult().getStatus().is(Status.FAILED)) {
                    BufferedImage image = new AShot()
                            .shootingStrategy(ShootingStrategies.viewportPasting(100))
                            .takeScreenshot(DriverManager.getINSTANCE().getDriver()).getImage();
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        ImageIO.write(image, "png", baos);
                        Allure.getLifecycle().addAttachment("Fail", "image/png", ".png", baos.toByteArray());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        publisher.registerHandlerFor(TestStepFinished.class, testStepFinishedEventHandler);
        super.setEventPublisher(publisher);
    }
}
