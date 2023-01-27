package com.amount.intellij;

import com.amount.intellij.helper.ActionPerformer;
import com.amount.intellij.helper.GithubRepo;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;

public class GithubCopyAndOpenDevelopUrlForFile extends AnAction {
  @Override
  public void actionPerformed(AnActionEvent event) {
    Project project = event.getData(PlatformDataKeys.PROJECT);
    new ActionPerformer(new GithubRepo(project.getBasePath(), false, "develop")).actionPerformed(event);
  }
}
