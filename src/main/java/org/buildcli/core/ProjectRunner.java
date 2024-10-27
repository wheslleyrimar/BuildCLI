package org.buildcli.core;

import java.io.IOException;

public class ProjectRunner {

    public void runProject() {
        try {
            ProcessBuilder builder = new ProcessBuilder("mvn", "spring-boot:run");
            builder.inheritIO();
            Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
