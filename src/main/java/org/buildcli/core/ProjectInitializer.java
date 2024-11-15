package org.buildcli.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.buildcli.log.SystemOutLogger;

public class ProjectInitializer {

    public void initializeProject() throws IOException {
        String[] dirs = {"src/main/java", "src/main/resources", "src/test/java"};
        for (String dir : dirs) {
            File directory = new File(dir);
            if (directory.mkdirs()) {
            	SystemOutLogger.log("Directory created: " + dir);
            }
        }

        createReadme();
        createMainClass();
        createPomFile();
    }

    private void createReadme() throws IOException {
        File readme = new File("README.md");
        if (readme.createNewFile()) {
        	SystemOutLogger.log("README.md file created.");
        }
    }

    private void createMainClass() throws IOException {
        File packageDir = new File("src/main/java/org/buildcli");
        if(!packageDir.mkdirs())
            throw new IOException("Could not create package directory: " + packageDir);
        File javaClass = new File(packageDir, "Main.java");
        if (javaClass.createNewFile()) {
            try (FileWriter writer = new FileWriter(javaClass)) {
                writer.write("""
                        package org.buildcli;

                        public class Main {
                            public static void main(String[] args) {
                                System.out.println("Hello, World!");
                            }
                        }""");
                SystemOutLogger.log("Main.java file created with package and basic content.");
            }
        }
    }

    private void createPomFile() throws IOException {
        File pomFile = new File("pom.xml");
        if (pomFile.createNewFile()) {
            try (FileWriter writer = new FileWriter(pomFile)) {
                writer.write("""
                        <project xmlns="http://maven.apache.org/POM/4.0.0"
                                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://www.apache.org/xsd/maven-4.0.0.xsd">
                            <modelVersion>4.0.0</modelVersion>

                            <groupId>com.example</groupId>
                            <artifactId>GeneratedApp</artifactId>
                            <version>1.0-SNAPSHOT</version>

                            <!-- Propriedades do projeto -->
                            <properties>
                                <maven.compiler.source>17</maven.compiler.source>
                                <maven.compiler.target>17</maven.compiler.target>
                                <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
                            </properties>

                            <!-- Dependências do projeto -->
                            <dependencies>
                                <dependency>
                                    <groupId>org.junit.jupiter</groupId>
                                    <artifactId>junit-jupiter-engine</artifactId>
                                    <version>5.8.1</version>
                                    <scope>test</scope>
                                </dependency>
                            </dependencies>

                            <!-- Plugins do projeto -->
                            <build>
                                <plugins>
                                    <!-- Plugin para configurar a versão do Java -->
                                    <plugin>
                                        <groupId>org.apache.maven.plugins</groupId>
                                        <artifactId>maven-compiler-plugin</artifactId>
                                        <version>3.8.1</version>
                                        <configuration>
                                            <source>${maven.compiler.source}</source>
                                            <target>${maven.compiler.target}</target>
                                        </configuration>
                                    </plugin>
                                    <!-- Plugin para gerar um JAR executável -->
                                    <plugin>
                                        <groupId>org.apache.maven.plugins</groupId>
                                        <artifactId>maven-jar-plugin</artifactId>
                                        <version>3.2.0</version>
                                        <configuration>
                                            <archive>
                                                <manifest>
                                                    <mainClass>org.buildcli.Main</mainClass>
                                                </manifest>
                                            </archive>
                                        </configuration>
                                    </plugin>
                                </plugins>
                            </build>

                            <profiles>
                                <profile>
                                    <id>dev</id>
                                    <properties>
                                        <spring.profiles.active>dev</spring.profiles.active>
                                    </properties>
                                </profile>

                                <profile>
                                    <id>test</id>
                                    <properties>
                                        <spring.profiles.active>test</spring.profiles.active>
                                    </properties>
                                </profile>

                                <profile>
                                    <id>prod</id>
                                    <properties>
                                        <spring.profiles.active>prod</spring.profiles.active>
                                    </properties>
                                </profile>
                            </profiles>
                        </project>""");
                SystemOutLogger.log("pom.xml file created with default configuration.");
            }
        }
    }


    /**
     * Creates a configuration file for the specified profile with predefined content.
     *
     * @param profile the name of the profile (e.g., dev, prod, test)
     */
    public static void createProfileConfig(String profile) {
        String fileName = "src/main/resources/application-" + profile + ".properties";
        File profileFile = new File(fileName);

        String content = """
                # Configurações do perfil de %s
                app.name=BuildCLI %s Environment
                app.message=Running in %s Mode
                logging.level=DEBUG
                """.formatted(profile, profile, profile);

        try {
            if (profileFile.createNewFile()) {
                try (FileWriter writer = new FileWriter(profileFile)) {
                    writer.write(content);
                }
                System.out.println("Configuration profile created: " + fileName);
            } else {
                System.out.println("Configuration profile already exists: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
