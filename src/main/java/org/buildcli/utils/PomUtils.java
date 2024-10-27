package org.buildcli.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PomUtils {

    public static void addDependencyToPom(String dependency) {
        String[] parts = dependency.split(":");
        if (parts.length != 2) {
            System.out.println("Invalid dependency format. Use 'groupId:artifactId'.");
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
            System.out.println("Error adding dependency to pom.xml.");
            e.printStackTrace();
        }
    }
}
