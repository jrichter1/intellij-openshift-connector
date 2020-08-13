package org.jboss.tools.intellij.openshift.fixtures;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.data.RemoteComponent;
import com.intellij.remoterobot.fixtures.CommonContainerFixture;
import com.intellij.remoterobot.fixtures.DefaultXpath;
import com.intellij.remoterobot.fixtures.FixtureName;
import org.jetbrains.annotations.NotNull;

@DefaultXpath(by = "MyDialog type", xpath = "//div[@accessiblename='New Project' and @class='MyDialog']")
@FixtureName(name = "Dialog")
public class NewProjectDialog extends CommonContainerFixture {
    public NewProjectDialog(@NotNull RemoteRobot remoteRobot, @NotNull RemoteComponent remoteComponent) {
        super(remoteRobot, remoteComponent);
    }
}
