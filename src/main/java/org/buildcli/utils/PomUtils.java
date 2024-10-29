package org.buildcli.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PomUtils {

    private static final Logger logger = Logger.getLogger(PomUtils.class.getName());
    private static final String file = "pom.xml";

    public static void addDependencyToPom(String dependency) {
        String[] parts = dependency.split(":");
        if (parts.length != 2) {
            logger.warning("Invalid dependency format. Use 'groupId:artifactId'.");
            return;
        }

        String dependencyXml = """
                                <--! Add by BuildCLI-->
                                <dependency>
                                    <groupId>%s</groupId>
                                    <artifactId>%s</artifactId>
                                    <version>LATEST</version>
                                </dependency>
                            </dependencies>\
                        """.formatted(parts[0], parts[1]);

        try {
            String pomContent = new String(Files.readAllBytes(Paths.get(file)));
            pomContent = pomContent.replace("</dependencies>", dependencyXml);
            Files.write(Paths.get(file), pomContent.getBytes());
            System.out.println("Dependency added to pom.xml.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error adding dependency to pom.xml", e);
        }
    }
}
