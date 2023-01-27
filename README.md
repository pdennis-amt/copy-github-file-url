# copy-github-file-url

## Summary

This is a plugin for IntelliJ IDEs (Rubymine, PhpStorm etc) that adds the ability to 
copy a file's Github URL to the clipboard and open the default browser to that 
location from a menu action. If you frequently refer others to Github URLs for your files,
hopefully this will save you time when doing so.

## Installation

1. Download the zip file in the [distributions](https://github.com/pdennis-amt/copy-github-file-url/tree/master/build/distributions) folder
2. Open your Rubymine (or other IntelliJ IDE) **Preferences** and open **Plugins**, click the gear icon and choose **Install Plugin from disk...**
3. Choose the zip file downloaded in step 1 and confirm installation.

## Usage

- To copy the URL of the file only (without line references), either right-click on the opened tab or on the file in the Project tree
- To copy the URL of the file with a single line highlighted in Github, right-click on the line in the editor 
- To copy the URL of the file with a range of lines highlighted in Github, select the entire range (the caret should be at the beginning of the line below the end of the range) and right-click on the range

There are 4 options to choose from:
 - Copy URL and Open Github (Develop)
 - Copy URL and Open Github (Main)
 - Copy URL and Open Github (Master)
 - Copy URL and Open Github (Current Branch)

All options above result in the URL being copied to the clipboard and opened in a new
tab of your default browser.  The only difference is the branch in the URL. Current Branch opens the currently checked-out branch.

## Extra Notes
 - Feel free to set a Keyboard Shortcut for any (or all) of these!  