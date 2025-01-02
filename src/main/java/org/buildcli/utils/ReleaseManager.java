package org.buildcli.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
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

            createReleaseBranch(currentVersion);
            updateChangelog(currentVersion);
            createGitTag(currentVersion);
            pushReleaseBranch(currentVersion);

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

    private void createReleaseBranch(String version) throws IOException, InterruptedException {
        String branchName = "release/v" + version;
        runGitCommandWithException("git", "checkout", "-b", branchName);
        logger.info(String.format("Created and switched to branch %s", branchName));
    }

    private void createGitTag(String version) throws IOException, InterruptedException {
        runGitCommandWithException("git", "tag", "-a", "v" + version, "-m", "Release " + version);
        logger.info(String.format("Created tag v%s", version));
    }

    private void pushReleaseBranch(String version) throws IOException, InterruptedException {
        String branchName = "release/v" + version;
        runGitCommandWithException("git", "push", "-u", "origin", branchName);
        runGitCommandWithException("git", "push", "origin", "v" + version);
        logger.info(String.format("Pushed branch %s and tag v%s to remote", branchName, version));
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

    private String runGitCommandAndShowOutput(String... command) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        Process process = builder.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException(String.format("Git command '%s' failed with exit code %d.", String.join(" ", command), exitCode));
        }

        return output.toString();
    }


    public String showContributors() {

        String localGit = "--git-dir=" + findGitRepository() +"/.git";
        String workTree = "--work-tree=" +findGitRepository();;

        String[] command = {
                "sh", "-c",
                String.format("git %s %s log --format='- %%aN <%%aE>' | sort | uniq",
                        localGit, workTree)
        };

        try {
            return runGitCommandAndShowOutput(command);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String getBuildCLIBuildDirectory() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF")) {
            if (inputStream == null) {
                throw new IllegalStateException("Manifest not found.");
            }
            Manifest manifest = new Manifest(inputStream);
            Attributes attributes = manifest.getMainAttributes();
            String buildDirectory = attributes.getValue("Build-Directory");

            if (buildDirectory == null) {
                throw new IllegalStateException("Build-Directory not found in the Manifest.");
            }

            return buildDirectory;
        } catch (Exception e) {
            throw new RuntimeException("Error reading the Manifest.", e);
        }
    }

    private String findGitRepository() {
        try {
            File dir = new File(getBuildCLIBuildDirectory());
            while (dir != null && dir.exists()) {
                if (new File(dir, ".git").exists()) {
                    return dir.getCanonicalPath();
                }
                dir = dir.getParentFile();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error locating the Git repository.", e);
        }
    }
}
