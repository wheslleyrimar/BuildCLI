package org.buildcli.core;

import org.buildcli.utils.SystemCommands;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectRunner {
    private static final Logger logger = Logger.getLogger(ProjectRunner.class.getName());

    public void runProject() {
        try {
            ProcessBuilder builder = new ProcessBuilder(SystemCommands.MVN.getCommand(), "spring-boot:run", "-q");
            builder.inheritIO();
            Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "Failed to run project", e);
            Thread.currentThread().interrupt();
        }
    }
}
