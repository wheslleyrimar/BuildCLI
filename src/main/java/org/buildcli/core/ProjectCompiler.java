package org.buildcli.core;

import org.buildcli.utils.SystemCommands;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectCompiler {

    private static final Logger logger = Logger.getLogger(ProjectCompiler.class.getName());

    public void compileProject() {
        try {
            ProcessBuilder builder = new ProcessBuilder(SystemCommands.MVN.getCommand(), "compile");
            builder.inheritIO();
            Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "Failed to compile project", e);
        }
    }
}
