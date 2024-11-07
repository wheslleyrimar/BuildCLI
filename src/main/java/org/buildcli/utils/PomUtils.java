package org.buildcli.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.buildcli.exception.ExtractionRuntimeException;
import org.buildcli.log.SystemOutLogger;

public class PomUtils {

    private static final Logger logger = Logger.getLogger(PomUtils.class.getName());
    private static final String FILE = "pom.xml";
    private static final String DEPENDENCIES_PATTERN = "##dependencies##";
    private static StringBuilder pomData =  new StringBuilder();
    private static Pom pom = new Pom();

    private PomUtils() { }
    
    public static Pom addDependencyToPom(String pomPath, String[] dependencies) {
    	extractPomFile(Optional.of(pomPath));
    	Stream.of(dependencies).forEach(pom::addDependency);
        return pom;
    }
    
    public static void addDependencyToPom(String[] dependencies) {
        extractPomFile(Optional.empty());
        Stream.of(dependencies).forEach(pom::addDependency);
        applyChangesToPom("Dependency added to pom.xml.", "Error adding dependency to pom.xml");
    }
    
    public static Pom rmDependencyToPom(String pomPath, String[] dependencies) {
    	extractPomFile(Optional.of(pomPath));
    	Stream.of(dependencies).forEach(pom::rmDependency);
        return pom;
    }
    
    public static void rmDependencyToPom(String[] dependencies) {
        extractPomFile(Optional.empty());
        Stream.of(dependencies).forEach(pom::rmDependency);
        applyChangesToPom("Dependency removed from pom.xml.", "Error removing dependency from pom.xml");
    }

    private static void applyChangesToPom(String successMessage, String failureMessage) {
    	
    	try {
            String pomContent = pomData.toString()
                    .replace(DEPENDENCIES_PATTERN, pom.getDependencyFormatted());
            Files.write(Paths.get(FILE), pomContent.getBytes());
            SystemOutLogger.log(successMessage);
        } catch (IOException e) {
            logger.log(Level.SEVERE, failureMessage, e);
        }
    }
    
    private static void extractPomFile(Optional<String> pomPath){
    	
        Pom newPom = new Pom();

        StringBuilder newPomData = new StringBuilder();

        File pomFile = new File(Paths.get(pomPath.isPresent() ? pomPath.get() : FILE).toFile().getAbsolutePath());
        try(Reader reader = new FileReader(pomFile);
            BufferedReader br = new BufferedReader(reader)) {

            while(br.ready()) {
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
                            line = br.readLine();
                        } else
                            line = br.readLine();
                    }
                    newPomData.append(DEPENDENCIES_PATTERN).append(System.lineSeparator());
                } else
                    newPomData.append(readLine).append(System.lineSeparator());
            }
        } catch (Exception e) {
            throw new ExtractionRuntimeException(e);
        }

        pomData = newPomData;
        pom = newPom;
    }
}
