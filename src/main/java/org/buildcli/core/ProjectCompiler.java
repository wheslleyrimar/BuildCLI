package org.buildcli.core;

import java.io.IOException;

public class ProjectCompiler {

    public void compileProject() {
        try {
            ProcessBuilder builder = new ProcessBuilder("mvn", "compile");
            builder.inheritIO();
            Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
