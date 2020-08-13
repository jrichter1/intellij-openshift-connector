package org.jboss.tools.intellij.openshift.fixtures;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.data.RemoteComponent;
import com.intellij.remoterobot.fixtures.ComponentFixture;
import com.intellij.remoterobot.fixtures.ContainerFixture;
import com.intellij.remoterobot.fixtures.DefaultXpath;
import com.intellij.remoterobot.fixtures.FixtureName;
import org.jboss.tools.intellij.openshift.fixtures.ActionLink;
import org.jetbrains.annotations.NotNull;

import static com.intellij.remoterobot.search.locators.Locators.byXpath;


@DefaultXpath(by = "FlatWelcomeFrame type", xpath = "//div[@class='FlatWelcomeFrame']")
@FixtureName(name = "Welcome Frame")
public class WelcomeFrame extends ContainerFixture {
    public WelcomeFrame(@NotNull RemoteRobot remoteRobot, @NotNull RemoteComponent remoteComponent) {
        super(remoteRobot, remoteComponent);
    }

    public ActionLink createNewProjectLink() {
        return find(ActionLink.class, byXpath("//div[@text='Create New Project' and @class='ActionLink']"));
    }

    public ComponentFixture importProjectLink() {
        return find(ComponentFixture.class, byXpath("//div[@text='Import Project' and @class='ActionLink']"));
    }
}
