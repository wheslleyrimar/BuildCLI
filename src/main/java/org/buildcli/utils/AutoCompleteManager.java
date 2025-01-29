package org.buildcli.utils;

import picocli.AutoComplete;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class AutoCompleteManager {

    public void setupAutocomplete() {
        String commandName = "buildcli"; // Nome do comando principal
        String fullyQualifiedClassName = "org.buildcli.OptionCommand"; // Classe principal do comando

        try {
            // Caminhos para salvar os scripts de autocomplete
            Path bashCompletionPath = Path.of(System.getProperty("user.home"), "." + commandName + "-completion.bash");
            Path zshCompletionPath = Path.of(System.getProperty("user.home"), "." + commandName + "-completion.zsh");
            Path fishCompletionPath = Path.of(System.getProperty("user.home"), "." + commandName + "-completion.fish");

            // Gerar os scripts de autocomplete para cada shell
            AutoComplete.main(new String[]{fullyQualifiedClassName, "-n=" + commandName, "-o=" + bashCompletionPath.toString()});
            AutoComplete.main(new String[]{fullyQualifiedClassName, "-n=" + commandName, "-o=" + zshCompletionPath.toString()});
            AutoComplete.main(new String[]{fullyQualifiedClassName, "-n=" + commandName, "-o=" + fishCompletionPath.toString()});

            System.out.println("Autocomplete scripts generated successfully!");

            // Automatizar a configuração do shell
            configureShell("bash", bashCompletionPath, "~/.bashrc");
            configureShell("zsh", zshCompletionPath, "~/.zshrc");
            configureShell("fish", fishCompletionPath, "~/.config/fish/config.fish");

        } catch (Exception e) {
            System.err.println("Failed to set up autocomplete: " + e.getMessage());
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

            // Verificar se o comando já foi adicionado ao arquivo de configuração
            String configContent = Files.readString(configFilePath);
            if (configContent.contains(sourceCommand)) {
                System.out.printf("Autocomplete already configured for %s in %s%n", shell, configFilePath);
                return;
            }

            // Adicionar o comando ao arquivo de configuração
            Files.writeString(configFilePath, sourceCommand, StandardOpenOption.APPEND);
            System.out.printf("Autocomplete configured for %s. Please restart your terminal or run 'source %s'%n", shell, configFilePath);
        } catch (IOException e) {
            System.err.printf("Failed to configure autocomplete for %s: %s%n", shell, e.getMessage());
        }
    }
}
