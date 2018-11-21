package org.jboss.tools.intellij.openshift.actions.cluster;

import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jboss.tools.intellij.openshift.tree.application.ApplicationsRootNode;
import org.jboss.tools.intellij.openshift.utils.OdoHelper;
import org.jboss.tools.intellij.openshift.utils.UIHelper;

import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class LogoutAction extends LoggedInClusterAction {
  @Override
  public void actionPerformed(AnActionEvent anActionEvent, TreePath path, Object selected, OdoHelper odo) {
    ApplicationsRootNode clusterNode = (ApplicationsRootNode) selected;
    CompletableFuture.runAsync(() -> {
        try {
          odo.logout();
          clusterNode.setLogged(false);
          clusterNode.reload();
        } catch (IOException e) {
          UIHelper.executeInUI(() -> JOptionPane.showMessageDialog(null, "Error: " + e.getLocalizedMessage(), "Logout", JOptionPane.ERROR_MESSAGE));
        }
      });
  }
}