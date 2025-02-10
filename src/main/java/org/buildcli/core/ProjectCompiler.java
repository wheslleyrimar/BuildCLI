package org.buildcli.core;

import org.buildcli.utils.SystemCommands;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectCompiler {

    private static final Logger logger = Logger.getLogger(ProjectCompiler.class.getName());

    public void compileProject() {
        compileProject(false);
    }

    public void compileProject(boolean recomp) {
        try {
            // Altere o comando para "package" em vez de "compile"
            ProcessBuilder builder;
            if (recomp) {
                builder = new ProcessBuilder(SystemCommands.MVN.getCommand(), "clean", "package");
            } else {
                builder = new ProcessBuilder(SystemCommands.MVN.getCommand(), "package");
            }
            builder.inheritIO();
            Process process = builder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                logger.info("Project compiled successfully. JAR file generated in target directory.");
            } else {
                logger.severe("Failed to compile project. Maven exited with code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "Failed to compile project", e);
            Thread.currentThread().interrupt();
        }
    }
}
