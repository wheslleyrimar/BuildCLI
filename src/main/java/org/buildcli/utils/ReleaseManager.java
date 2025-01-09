package org.buildcli.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
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

    private String runGitCommandAndShowOutput(String... command) {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output.toString();
    }


    public String showContributors(String gitPath) {

        String localGit = getGitDir(gitPath);
        String workTree = getWorkTree(gitPath);

        String[] command = {
                "sh", "-c",
                String.format("git %s %s log --format='- %%aN <%%aE>' | sort | uniq",
                        localGit, workTree)
        };

        return runGitCommandAndShowOutput(command);
    }

    private String getDirectory() {
            return System.getProperty("user.dir");
    }

    protected String findGitRepository(String path) {
        try {
            File dir = new File(path);
            while (dir != null && dir.exists()) {
                if (new File(dir, ".git").exists()) {
                    return dir.getCanonicalPath();
                }
                dir = dir.getParentFile();
            }
            return null;
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Git repository not found!");
            throw new RuntimeException(e);
        }
    }


    protected boolean checkIfLocalRepositoryIsUpdated(String gitPath){
        String gitDir = getGitDir(gitPath), workTree = getWorkTree(gitPath);

        runGitCommand("sh", "-c", String.format("git %s %s fetch origin/main", gitDir, workTree));

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Callable<String> gitStatusTask = () -> runGitCommandAndShowOutput("git", gitDir, workTree, "status -s");
            Callable<String> gitDiffTask = () -> runGitCommandAndShowOutput("git", gitDir, workTree, "diff --exit-code");

            var gitStatusFuture = executor.submit(gitStatusTask);
            var gitDiffFuture = executor.submit(gitDiffTask);

            String gitStatusResult = gitStatusFuture.get();
            String gitDiffResult = gitDiffFuture.get();

            return gitDiffResult.isBlank() && gitStatusResult.isBlank();

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String getGitDir(String localRepository){
        return "--git-dir=" + localRepository +"/.git";
    }

    private String getWorkTree(String localRepository){
        return  "--work-tree=" +localRepository;
    }
}
