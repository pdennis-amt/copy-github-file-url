package com.amount.intellij;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.amount.intellij.helper.ActionPerformer;
import com.amount.intellij.helper.GithubRepo;

public class GithubCopyAndOpenUrlForFile extends AnAction {
  @Override
  public void actionPerformed(AnActionEvent event) {
    Project project = event.getData(PlatformDataKeys.PROJECT);
    new ActionPerformer(new GithubRepo(project.getBasePath())).actionPerformed(event);
  }
}
