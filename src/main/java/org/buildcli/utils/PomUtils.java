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
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.buildcli.exception.ExtractionRuntimeException;
import org.buildcli.log.SystemOutLogger;
import org.buildcli.model.DependencyHashMap;

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

        File pomFile = new File(Paths.get(pomPath.isPresent() ? pomPath.get() : FILE)
        		.toFile().getAbsolutePath());
        
        try (var reader = new FileReader(pomFile); var br = new BufferedReader(reader)) {

            while (br.ready()) {
            	
                String readLine = br.readLine();
                
                if (readLine.contains("<dependencyManagement>")) {
                	do {
                		readLine = br.readLine();
                	} while (!readLine.contains("</dependencyManagement>"));
                }
                
                if (readLine.contains("<dependencies>")) {
                	
                    String line = br.readLine();
                    
                    while (!line.contains("</dependencies>")) {
                    	
                        if (line.contains("<dependency>")) {
                        	
                            var dependency = new DependencyHashMap();
                            
                            while (!line.contains("</dependency>")) {
                            	
                            	String element = br.readLine();
                            	
                            	Consumer<String> addElementToDependency = e ->
                            		dependency.put(e, element.replace(String.format("<%s>", e), "")
                            				.replace(String.format("</%s>", e), "").strip());
                            		
                                dependency.keySet().stream()
                                	.filter(k -> element.contains(String.format("<%s>", k)))
                                	.findFirst()
                                	.ifPresent(addElementToDependency);
                                
                                line = element;
                            }
                            
                            newPom.addDependencyFile(dependency.get("groupId"),dependency.get("artifactId"),
                                    dependency.get("version"), dependency.get("type"), dependency.get("scope"),
                                    dependency.get("optional"));
                            line = br.readLine();
                        } else {
                            line = br.readLine();
                        }
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
