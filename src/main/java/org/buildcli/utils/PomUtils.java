package org.buildcli.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PomUtils {
    private static final Logger logger = Logger.getLogger(PomUtils.class.getName());

    public static void addDependencyToPom(String dependency) {
        String[] parts = dependency.split(":");
        if (parts.length != 2) {
            logger.warning("Invalid dependency format. Use 'groupId:artifactId'.");
            return;
        }

        String dependencyXml = String.format(
                        "    <dependency>\n" +
                        "        <groupId>%s</groupId>\n" +
                        "        <artifactId>%s</artifactId>\n" +
                        "        <version>LATEST</version>\n" +
                        "    </dependency>\n", parts[0], parts[1]);

        try {
            String pomContent = new String(Files.readAllBytes(Paths.get("pom.xml")));
            pomContent = pomContent.replace("</dependencies>", dependencyXml + "</dependencies>");
            Files.write(Paths.get("pom.xml"), pomContent.getBytes());
            System.out.println("Dependency added to pom.xml.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error adding dependency to pom.xml", e);
        }
    }
}
