package org.buildcli.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReleaseManager {

    private static final Logger logger = Logger.getLogger(ReleaseManager.class.getName());
    private static final Path CHANGELOG_FILE = Path.of("CHANGELOG.md");

    public void automateRelease() {
        try {
            String currentVersion = Files.readString(Path.of("VERSION")).trim();
            createGitTag(currentVersion);
            generateChangelog(currentVersion);

            System.out.printf("Release automation completed for version %s%n", currentVersion);
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "Failed to automate release", e);
        }
    }

    private void createGitTag(String version) throws IOException, InterruptedException {
        ProcessBuilder tagBuilder = new ProcessBuilder("git", "tag", "-a", "v" + version, "-m", "Release " + version);
        tagBuilder.inheritIO().start().waitFor();
        ProcessBuilder pushBuilder = new ProcessBuilder("git", "push", "origin", "v" + version);
        pushBuilder.inheritIO().start().waitFor();
    }

    private void generateChangelog(String version) throws IOException {
        String changelogEntry = String.format("## Version %s%n- Description of changes%n", version);
        if (!Files.exists(CHANGELOG_FILE)) {
            Files.writeString(CHANGELOG_FILE, "# Changelog\n\n");
        }
        Files.writeString(CHANGELOG_FILE, changelogEntry, java.nio.file.StandardOpenOption.APPEND);

        System.out.printf("Changelog updated for version %s%n", version);
    }
}
