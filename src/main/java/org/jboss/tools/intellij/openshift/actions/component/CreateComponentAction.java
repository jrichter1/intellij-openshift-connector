package org.jboss.tools.intellij.openshift.actions.component;

import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jboss.tools.intellij.openshift.actions.application.OdoAction;
import org.jboss.tools.intellij.openshift.tree.application.ApplicationNode;
import org.jboss.tools.intellij.openshift.tree.application.ApplicationTreeModel;
import org.jboss.tools.intellij.openshift.ui.component.CreateComponentDialog;
import org.jboss.tools.intellij.openshift.utils.ExecHelper;
import org.jboss.tools.intellij.openshift.utils.UIHelper;
import org.jetbrains.annotations.Nullable;

import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class CreateComponentAction extends OdoAction {
  public CreateComponentAction() {
    super(ApplicationNode.class);
  }

  @Override
  public void actionPerformed(AnActionEvent anActionEvent, TreePath path, Object selected, String odo) {
    ApplicationNode applicationNode = (ApplicationNode) selected;
    CompletableFuture.runAsync(() -> {
      try {
        CreateComponentDialog dialog = UIHelper.executeinUI(() -> {
          try {
            return showDialog(loadComponentTypes(ExecHelper.execute(odo, "catalog", "list", "components")));
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
        if (dialog.isOK()) {
          createComponent(odo, applicationNode.toString(), dialog);
          applicationNode.reload();
          ((ApplicationTreeModel)getTree(anActionEvent).getModel()).treeStructureChanged(path, new int[0], new Object[0]);
        }
      } catch (IOException e) {
        UIHelper.executeInUI(() -> JOptionPane.showMessageDialog(null, "Error: " + e.getLocalizedMessage(), "Create component", JOptionPane.ERROR_MESSAGE));
      }
    });
  }

  @Nullable
  protected void createComponent(String odo, String appName, CreateComponentDialog dialog) throws IOException {
      ExecHelper.execute(odo, "app", "set", appName);
      createComponent(odo, dialog);
  }

  private void createComponent(String odo, CreateComponentDialog dialog) {
    if (dialog.getSourceType() == 0) {
      ExecHelper.executeWithTerminal(odo, "create", dialog.getComponentType() + ':' + dialog.getComponentVersion(), dialog.getName(),
        "--local", dialog.getSource());
    } else {
      ExecHelper.executeWithTerminal(odo, "create", dialog.getComponentType() + ':' + dialog.getComponentVersion(), dialog.getName(),
        "--git", dialog.getSource());
    }
  }

  protected CreateComponentDialog showDialog(List<String[]> types) {
    CreateComponentDialog dialog = new CreateComponentDialog(null);
    dialog.setComponentTypes(types.toArray(new String[types.size()][]));
    dialog.show();
    return dialog;
  }

  private List<String[]> loadComponentTypes(String output) throws IOException {
    try (BufferedReader reader = new BufferedReader(new StringReader(output))) {
      return reader.lines().skip(1).map(s -> s.replaceAll("\\s{1,}", "|"))
        .map(s -> s.split("\\|"))
        .map(s -> {
          return new String[] {s[0], s[2]};
        })
        .collect(Collectors.toList());
    }
  }
}