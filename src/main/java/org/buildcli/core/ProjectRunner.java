package org.buildcli.core;

import org.buildcli.utils.ProfileManager;
import org.buildcli.utils.SystemCommands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectRunner {
    private static final Logger logger = Logger.getLogger(ProjectRunner.class.getName());
    private final ProfileManager profileManager;

    public ProjectRunner() {
        this.profileManager = new ProfileManager();
    }

    public void runProject() {
        try {
            // Carregar o perfil ativo
            String activeProfile = profileManager.getActiveProfile();
            if (activeProfile == null) {
                logger.warning("No active profile set. Using default profile.");
                activeProfile = "default";
            }

            // Carregar as propriedades do perfil ativo
            Properties properties = loadProfileProperties(activeProfile);
            String profileMessage = properties.getProperty("app.message", "Running with no specific profile");

            // Exibir a mensagem do perfil ativo no console
            System.out.println("Active Profile: " + activeProfile);
            System.out.println(profileMessage);

            // Executar o projeto com o perfil ativo
            ProcessBuilder builder = new ProcessBuilder(
                    SystemCommands.MVN.getCommand(),
                    "spring-boot:run",
                    "-q"
            );
            builder.environment().put("ACTIVE_PROFILE", activeProfile); // Define o perfil como variável de ambiente
            builder.inheritIO();
            Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "Failed to run project", e);
            Thread.currentThread().interrupt();
        }
    }

    private Properties loadProfileProperties(String profile) {
        Properties properties = new Properties();
        String propertiesFile = "src/main/resources/application-" + profile + ".properties";
        try (InputStream input = Files.newInputStream(Paths.get(propertiesFile))) {
            properties.load(input);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load profile properties file: " + propertiesFile, e);
        }
        return properties;
    }
}
