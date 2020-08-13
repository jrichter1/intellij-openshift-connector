package org.jboss.tools.intellij.openshift.fixtures;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.data.RemoteComponent;
import com.intellij.remoterobot.fixtures.ComponentFixture;
import com.intellij.remoterobot.fixtures.FixtureName;
import org.jetbrains.annotations.NotNull;


@FixtureName(name = "Action Link")
public class ActionLink extends ComponentFixture {
    public ActionLink(@NotNull RemoteRobot remoteRobot, @NotNull RemoteComponent remoteComponent) {
        super(remoteRobot, remoteComponent);
    }

    public void click() {
        runJs("const offset = component.getHeight()/2;\n" +
                "robot.click(component, new Point(offset, offset), MouseButton.LEFT_BUTTON, 1);"
        );
    }
}
