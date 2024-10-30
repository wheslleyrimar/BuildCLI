package org.buildcli.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PomUtils {

    private static final Logger logger = Logger.getLogger(PomUtils.class.getName());
    private static final String file = "pom.xml";

    public static void addDependencyToPom(String[] dependency) {

        StringBuilder dependencyXml = new StringBuilder();
        for(String d : dependency) {
            String[] parts = d.split(":");

            switch (parts.length) {
                case 2:
                    parts = (d + ":LATEST").split(":");
                    break;
                case 3:
                    break;
                default:
                    logger.warning("Invalid dependency format. Use 'groupId:artifactId'" +
                            "or 'groupId:artifactId:version'.");
                    return;
            }

            dependencyXml.append( """
                            <!-- Added by BuildCLI-->
                            <dependency>
                                <groupId>%s</groupId>
                                <artifactId>%s</artifactId>
                                <version>%s</version>
                            </dependency>
                    """.formatted(parts[0], parts[1], parts[2]));
        }

        dependencyXml.append("""
                                </dependencies>\
                            """);

        try {
            String pomContent = new String(Files.readAllBytes(Paths.get(file)));
            pomContent = pomContent.replace("    </dependencies>", dependencyXml);
            Files.write(Paths.get(file), pomContent.getBytes());
            System.out.println("Dependency added to pom.xml.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error adding dependency to pom.xml", e);
        }
    }
}
