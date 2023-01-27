package com.amount.intellij.helper;

/**
 * Url builder for github repos.
 */
public class GithubRepo extends GitRepo {
  public GithubRepo(String projectRoot, Boolean useCurrentBranch) {
    super(projectRoot, useCurrentBranch, "");
  }

  public GithubRepo(String projectRoot, Boolean useCurrentBranch, String branchName) {
    super(projectRoot, useCurrentBranch, branchName);
  }

  public GithubRepo(String projectRoot, String gitconfig, Boolean useCurrentBranch, String branchName) {
    super(projectRoot, gitconfig, useCurrentBranch, branchName);
  }

  @Override
  public String brand() {
    return "Github";
  }

  @Override
  protected String buildUrlFor(String sanitizedUrlValue, Boolean useCurrentBranch, String gitHeadFilePath, String branchName) {
    String branch = "";
    if (useCurrentBranch) { branch = currentGitBranch(); }
    else { branch = branchName; }
    return "https://" + sanitizedUrlValue + "/blob/" + branch;
  }

  @Override
  protected String buildLineDomainPrefix() {
    return "#L";
  }

  @Override
  protected String buildLineDomainSuffix() {
    return "-L";
  }
}
