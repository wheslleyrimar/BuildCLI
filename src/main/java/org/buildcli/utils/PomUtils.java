package org.buildcli.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PomUtils {

    private static final Logger logger = Logger.getLogger(PomUtils.class.getName());
    private static final String file = "pom.xml";
    private static StringBuilder pomData =  new StringBuilder();
    private static Pom pom = new Pom();

    public static void addDependencyToPom(String[] dependency) {
        extractPomFile();
        for(String d : dependency)
            pom.addDependency(d);
        try {
            String pomContent = pomData.toString()
                    .replace("##dependencies##", pom.getDependencyFormatted());
            Files.write(Paths.get(file), pomContent.getBytes());
            System.out.println("Dependency added to pom.xml.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error adding dependency to pom.xml", e);
        }
    }

    public static void rmDependencyToPom(String[] rmDependency) {
        extractPomFile();
        for(String d : rmDependency)
            pom.rmDependency(d);

        try {
            String pomContent = pomData.toString()
                    .replace("##dependencies##", pom.getDependencyFormatted());
            Files.write(Paths.get(file), pomContent.getBytes());
            System.out.println("Dependency removed from pom.xml.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error adding dependency to pom.xml", e);
        }
    }

    private static void extractPomFile(){
        Pom newPom = new Pom();

        StringBuilder newPomData = new StringBuilder();

        File pomFile = new File(Paths.get(file).toFile().getAbsolutePath());
        try(Reader reader = new FileReader(pomFile);
            BufferedReader br = new BufferedReader(reader)) {

            fileWhile: while(br.ready()) {
                String readLine = br.readLine();
                if(readLine.contains("<dependencies>")) {
                    String line = br.readLine();
                    while(!line.contains("</dependencies>")) {
                        if(line.contains("<dependency>")) {
                            Map<String,String> dependency = new HashMap<>();
                            while(!line.contains("</dependency>")) {
                                line = br.readLine();
                                if(line.contains("<groupId>")) {
                                    dependency.put("groupId",
                                            line.replace("<groupId>", "")
                                                    .replace("</groupId>", "")
                                                    .strip());
                                    continue;
                                }
                                if(line.contains("<artifactId>")) {
                                    dependency.put("artifactId",
                                            line.replace("<artifactId>", "")
                                                    .replace("</artifactId>", "")
                                                    .strip());
                                    continue;
                                }
                                if(line.contains("<version>")) {
                                    dependency.put("version", line.replace("<version>", "")
                                            .replace("</version>", "")
                                            .strip());
                                    continue;
                                }
                                if(line.contains("<type>")) {
                                    dependency.put("type", line.replace("<type>", "")
                                            .replace("</type>", "")
                                            .strip());
                                    continue;
                                }
                                if(line.contains("<scope>")) {
                                    dependency.put("scope", line.replace("<scope>", "")
                                            .replace("</scope>", "")
                                            .strip());
                                    continue;
                                }
                                if(line.contains("<optional>"))
                                    dependency.put("optional", line.replace("<optional>", "")
                                            .replace("</optional>", "")
                                            .strip());
                            }
                            newPom.addDependencyFile(dependency.get("groupId"),dependency.get("artifactId"),
                                    dependency.get("version"), dependency.get("type"), dependency.get("scope"),
                                    dependency.get("optional"));
                            //continue fileWhile;
                            line = br.readLine();
                        } else
                            line = br.readLine();
                    }
                    newPomData.append("##dependencies##").append(System.lineSeparator());
                } else
                    newPomData.append(readLine).append(System.lineSeparator());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        pomData = newPomData;
        pom = newPom;
    }
}
