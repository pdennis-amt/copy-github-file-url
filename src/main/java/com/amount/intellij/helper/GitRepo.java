// Copyright 2013 Square, Inc.
package com.amount.intellij.helper;

import com.google.common.annotations.VisibleForTesting;

import java.io.*;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Basic interfacing with .git/config configurations.
 */
public abstract class GitRepo {
  private static Pattern INI_CATEGORY = Pattern.compile("\\[\\s*(\\w+)[\\s'\"]+(\\w+)[\\s'\"]+\\]");
  private static Pattern URL_VALUE = Pattern.compile("\\s*url\\s*=\\s*([^\\s]*)\\.git");
  private static Pattern HEAD_BRANCH = Pattern.compile("ref:\\s*(?:(?:\\w+\\/)+)+([^\\s]+)");
  private final File gitConfigFile;
  private final String gitHeadFilePath;
  private final Boolean useCurrentBranch;
  private final String branchName;

  public GitRepo(String projectRoot, Boolean useCurrentBranch, String branchName) {
    this(projectRoot, ".git/config", useCurrentBranch, branchName);
  }

  @VisibleForTesting
  public GitRepo(String projectRoot, String gitconfig, Boolean useCurrentBranch, String branchName) {
    String gitRoot = findDotGitFolder(new File(projectRoot));
    gitConfigFile = new File(gitRoot, gitconfig);
    this.useCurrentBranch = useCurrentBranch;
    this.branchName = branchName;
    gitHeadFilePath = useCurrentBranch ? String.valueOf(Paths.get(gitRoot, ".git/HEAD")) : "";
  }

  public abstract String brand();

  /* Implement for different repository systems. */
  abstract String buildUrlFor(String sanitizedUrlValue, Boolean useCurrentBranch, String gitHeadFilePath, String branchName);

  abstract String buildLineDomainPrefix();

  abstract String buildLineDomainSuffix();

  public String currentGitBranch() {
    File gitHeadFile = new File(gitHeadFilePath);
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(gitHeadFile));
      String line;
      while ((line = reader.readLine()) != null) {
        Matcher matcher = HEAD_BRANCH.matcher(line);
        if (matcher.matches()) {
          return matcher.group(1);
        }
      }
      throw new RuntimeException("Did not find [remote \"origin\"] url set in " + gitHeadFile);
    } catch (IOException e) {
      throw new RuntimeException("File " + gitHeadFile + " does not exist.", e);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException ignored) {
        }
      }
    }
  }

  public String repoUrlFor(String relativeFilePath) {
    return repoUrlFor(relativeFilePath, null, null);
  }

  public String repoUrlFor(String filePath, Integer lineStart, Integer lineEnd) {
    filePath = filePath.replaceFirst(gitConfigFile.getParentFile().getParent(), "");
    return gitBaseUrl() + filePath + (lineStart != null ? buildLineDomainPrefix() + lineStart : "")
        + ((lineEnd != null && lineEnd > lineStart) ? buildLineDomainSuffix() + lineEnd : "");
  }

  String findDotGitFolder(File absolutePath) {
    if (absolutePath.getParent() == null) {
      throw new RuntimeException(
          "Could not find parent .git/ folder. Maybe path is not in a git repo? " + absolutePath);
    }
    FileFilter gitFolderFinder = new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return pathname.getName().equals(".git");
      }
    };
    if (absolutePath.listFiles(gitFolderFinder).length == 1) {
      return absolutePath.getAbsolutePath();
    }
    return findDotGitFolder(absolutePath.getParentFile());
  }

  private String gitBaseUrl() {
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(gitConfigFile));
      String line;
      boolean inRemoteOriginSection = false;
      while ((line = reader.readLine()) != null) {
        if (line.matches("\\s*#")) continue;
        Matcher matcher = INI_CATEGORY.matcher(line);
        if (matcher.matches()) {
          inRemoteOriginSection = "remote".equals(matcher.group(1))
              && "origin".equals(matcher.group(2));
          continue;
        }
        matcher = URL_VALUE.matcher(line);
        if (inRemoteOriginSection && matcher.matches()) {
          return buildUrlFor(matcher.group(1)
              .replaceAll("ssh://|git://|git@|https://", "")
              .replaceAll(":", "/"), useCurrentBranch, gitHeadFilePath, branchName);
        }
      }
      throw new RuntimeException("Did not find [remote \"origin\"] url set in " + gitConfigFile);
    } catch (IOException e) {
      throw new RuntimeException("File " + gitConfigFile + " does not exist.", e);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException ignored) {
        }
      }
    }
  }
}
