package org.buildcli.utils;

import picocli.AutoComplete;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class AutoCompleteManager {

    public void setupAutocomplete() {
        String commandName = "buildcli";
        String fullyQualifiedClassName = "org.buildcli.OptionCommand";

        try {
            // Paths to save the autocomplete scripts.
            Path bashCompletionPath = Path.of(System.getProperty("user.home"), "." + commandName + "-completion.bash");
            Path zshCompletionPath = Path.of(System.getProperty("user.home"), "." + commandName + "-completion.zsh");
            Path fishCompletionPath = Path.of(System.getProperty("user.home"), "." + commandName + "-completion.fish");

            // Generate autocomplete scripts for each shell, automatically using --force if needed
            generateAutoComplete(fullyQualifiedClassName, commandName, bashCompletionPath);
            generateAutoComplete(fullyQualifiedClassName, commandName, zshCompletionPath);
            generateAutoComplete(fullyQualifiedClassName, commandName, fishCompletionPath);

            System.out.println("Autocomplete scripts generated successfully!");

            // Automate shell configuration.
            configureShell("bash", bashCompletionPath, "~/.bashrc");
            configureShell("zsh", zshCompletionPath, "~/.zshrc");
            configureShell("fish", fishCompletionPath, "~/.config/fish/config.fish");

        } catch (Exception e) {
            System.err.println("Failed to set up autocomplete: " + e.getMessage());
        }
    }

    private void generateAutoComplete(String fullyQualifiedClassName, String commandName, Path completionPath) {
        try {
            // If the file already exists, automatically add --force.
            if (Files.exists(completionPath)) {
                System.out.println("Existing autocomplete script detected for " + commandName + ". Overwriting...");
                AutoComplete.main(new String[]{fullyQualifiedClassName, "-n=" + commandName, "-o=" + completionPath.toString(), "--force"});
            } else {
                AutoComplete.main(new String[]{fullyQualifiedClassName, "-n=" + commandName, "-o=" + completionPath.toString()});
            }
        } catch (Exception e) {
            System.err.println("Error generating autocomplete script for " + commandName + ": " + e.getMessage());
        }
    }

    private void configureShell(String shell, Path completionPath, String configFile) {
        try {
            Path configFilePath = Path.of(configFile.replace("~", System.getProperty("user.home")));

            if (!Files.exists(configFilePath)) {
                System.out.printf("Configuration file for %s not found: %s%n", shell, configFilePath);
                return;
            }

            String sourceCommand = "source " + completionPath + "\n";

            // Check if the command has already been added to the configuration file.
            String configContent = Files.readString(configFilePath);
            if (configContent.contains(sourceCommand)) {
                System.out.printf("Autocomplete already configured for %s in %s%n", shell, configFilePath);
                return;
            }

            // Add the command to the configuration file.
            Files.writeString(configFilePath, sourceCommand, StandardOpenOption.APPEND);
            System.out.printf("Autocomplete configured for %s. Please restart your terminal or run 'source %s'%n", shell, configFilePath);
        } catch (IOException e) {
            System.err.printf("Failed to configure autocomplete for %s: %s%n", shell, e.getMessage());
        }
    }
}
