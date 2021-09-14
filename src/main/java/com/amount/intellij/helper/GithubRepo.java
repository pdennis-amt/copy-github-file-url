package com.amount.intellij.helper;

/**
 * Url builder for github repos.
 */
public class GithubRepo extends GitRepo {
  public GithubRepo(String projectRoot, Boolean useCurrentBranch) {
    super(projectRoot, useCurrentBranch);
  }

  public GithubRepo(String projectRoot, String gitconfig, Boolean useCurrentBranch) {
    super(projectRoot, gitconfig, useCurrentBranch);
  }

  @Override
  public String brand() {
    return "Github";
  }

  @Override
  protected String buildUrlFor(String sanitizedUrlValue, Boolean useCurrentBranch, String gitHeadFilePath) {
    String branch = "";
    if (useCurrentBranch) { branch = currentGitBranch(); }
    else { branch = "master"; }
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
