package org.buildcli.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReleaseManager {

    private static final Logger logger = Logger.getLogger(ReleaseManager.class.getName());
    private static final Path VERSION_FILE = Path.of("VERSION");
    private static final Path CHANGELOG_FILE = Path.of("CHANGELOG.md");

    public void automateRelease() {
        if (!isGitRepository()) {
            logger.severe("Not a Git repository. Run 'git init' to initialize and make at least one commit.");
            return;
        }

        try {
            String currentVersion = readVersionFile();

            if (!hasCommits()) {
                logger.severe("No commits found in the Git repository. Make at least one commit before releasing.");
                return;
            }

            if (tagExists(currentVersion)) {
                logger.severe(String.format("Tag v%s already exists. Delete it or use a different version.", currentVersion));
                return;
            }

            createGitTag(currentVersion);
            updateChangelog(currentVersion);

            logger.info(String.format("Release automation completed for version %s", currentVersion));
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "Failed to automate release", e);
        }
    }

    private boolean isGitRepository() {
        return runGitCommand("git", "rev-parse", "--is-inside-work-tree") == 0;
    }

    private boolean hasCommits() {
        return runGitCommand("git", "log", "--oneline") == 0;
    }

    private boolean tagExists(String version) {
        return runGitCommand("git", "rev-parse", "v" + version) == 0;
    }

    private String readVersionFile() throws IOException {
        if (!Files.exists(VERSION_FILE)) {
            throw new IOException("VERSION file not found. Please create a VERSION file with the current version.");
        }
        return Files.readString(VERSION_FILE).trim();
    }

    private void createGitTag(String version) throws IOException, InterruptedException {
        runGitCommandWithException("git", "tag", "-a", "v" + version, "-m", "Release " + version);
        runGitCommandWithException("git", "push", "origin", "v" + version);
    }

    private void updateChangelog(String version) throws IOException {
        String changelogEntry = String.format("## Version %s%n- Description of changes%n", version);

        if (!Files.exists(CHANGELOG_FILE)) {
            Files.writeString(CHANGELOG_FILE, "# Changelog\n\n", StandardOpenOption.CREATE);
        }

        Files.writeString(CHANGELOG_FILE, changelogEntry, StandardOpenOption.APPEND);
        logger.info(String.format("Changelog updated for version %s", version));
    }

    private int runGitCommand(String... command) {
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            Process process = builder.start();
            return process.waitFor();
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING, String.format("Git command '%s' failed.", String.join(" ", command)), e);
            Thread.currentThread().interrupt();
            return -1;
        }
    }

    private void runGitCommandWithException(String... command) throws IOException, InterruptedException {
        int exitCode = runGitCommand(command);
        if (exitCode != 0) {
            throw new IOException(String.format("Git command '%s' failed. Ensure you are in a Git repository and the command is valid.", String.join(" ", command)));
        }
    }
}
