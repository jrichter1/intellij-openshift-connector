package org.jboss.tools.intellij.openshift;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.fixtures.ComponentFixture;
import com.intellij.remoterobot.utils.Keyboard;
import org.jboss.tools.intellij.openshift.fixtures.NewProjectDialog;
import org.jboss.tools.intellij.openshift.fixtures.WelcomeFrame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.awt.event.KeyEvent;
import java.time.Duration;

import static com.intellij.remoterobot.search.locators.Locators.byXpath;
import static com.intellij.remoterobot.stepsProcessing.StepWorkerKt.step;

public class SimpleTest {

    private static RemoteRobot robot;

    @BeforeAll
    public static void connect() throws InterruptedException {
        robot = new RemoteRobot("http://127.0.0.1:8082");
        for (int i = 0; i < 60; i++) {
            try {
                robot.find(WelcomeFrame.class);
            } catch (Exception ex) {
                Thread.sleep(1000);
            }
        }
    }

    @Test
    public void basicTest() {
        createNewCommandLineProject(robot);
    }

    public void createNewCommandLineProject(RemoteRobot remoteRobot) {
        step("Create New Command Line Project", () -> {
            final WelcomeFrame welcomeFrame = remoteRobot.find(WelcomeFrame.class);
            welcomeFrame.createNewProjectLink().click();

             final NewProjectDialog newProjectDialog = welcomeFrame.find(NewProjectDialog.class, Duration.ofSeconds(20));
             newProjectDialog.findText("Java").click();
             newProjectDialog.find(ComponentFixture.class,
                     byXpath("FrameworksTree", "//div[@class='FrameworksTree']"))
                     .findText("Kotlin/JVM")
                     .click();
             Keyboard keyboard = new Keyboard(remoteRobot);
             keyboard.key(KeyEvent.VK_SPACE, Duration.ZERO);
             newProjectDialog.button("Next").click();
             newProjectDialog.button("Finish").click();
        });
    }

    // public void closeTipOfTheDay() {
    //     step("Close Tip of the Day if it appears", () -> {
    //         final IdeaFrame idea = remoteRobot.find(IdeaFrame.class);
    //         idea.dumbAware(() -> {
    //             try {
    //                 idea.find(DialogFixture.class, byTitle("Tip of the Day")).button("Close").click();
    //             } catch (Throwable ignore) {
    //             }
    //             return;
    //         });
    //     });
    // }
}