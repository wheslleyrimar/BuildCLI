package org.buildcli.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ProjectInitializer {

    public void initializeProject() throws IOException {
        String[] dirs = {"src/main/java", "src/main/resources", "src/test/java"};
        for (String dir : dirs) {
            File directory = new File(dir);
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + dir);
            }
        }

        createReadme();
        createMainClass();
        createPomFile();
    }

    private void createReadme() throws IOException {
        File readme = new File("README.md");
        if (readme.createNewFile()) {
            System.out.println("README.md file created.");
        }
    }

    private void createMainClass() throws IOException {
        File packageDir = new File("src/main/java/org/buildcli");
        packageDir.mkdirs();
        File javaClass = new File(packageDir, "Main.java");
        if (javaClass.createNewFile()) {
            try (FileWriter writer = new FileWriter(javaClass)) {
                writer.write("package org.buildcli;\n\n" +
                        "public class Main {\n" +
                        "    public static void main(String[] args) {\n" +
                        "        System.out.println(\"Hello, World!\");\n" +
                        "    }\n" +
                        "}\n");
                System.out.println("Main.java file created with package and basic content.");
            }
        }
    }

    private void createPomFile() throws IOException {
        File pomFile = new File("pom.xml");
        if (pomFile.createNewFile()) {
            try (FileWriter writer = new FileWriter(pomFile)) {
                writer.write("<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                        "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                        "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://www.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                        "    <modelVersion>4.0.0</modelVersion>\n" +
                        "\n" +
                        "    <groupId>com.example</groupId>\n" +
                        "    <artifactId>GeneratedApp</artifactId>\n" +
                        "    <version>1.0-SNAPSHOT</version>\n" +
                        "\n" +
                        "    <!-- Propriedades do projeto -->\n" +
                        "    <properties>\n" +
                        "        <maven.compiler.source>17</maven.compiler.source>\n" +
                        "        <maven.compiler.target>17</maven.compiler.target>\n" +
                        "        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" +
                        "    </properties>\n" +
                        "\n" +
                        "    <!-- Dependências do projeto -->\n" +
                        "    <dependencies>\n" +
                        "        <!-- Dependência para JUnit 5 (para testes) -->\n" +
                        "        <dependency>\n" +
                        "            <groupId>org.junit.jupiter</groupId>\n" +
                        "            <artifactId>junit-jupiter-engine</artifactId>\n" +
                        "            <version>5.8.1</version>\n" +
                        "            <scope>test</scope>\n" +
                        "        </dependency>\n" +
                        "    </dependencies>\n" +
                        "\n" +
                        "    <!-- Plugins do projeto -->\n" +
                        "    <build>\n" +
                        "        <plugins>\n" +
                        "            <!-- Plugin para configurar a versão do Java -->\n" +
                        "            <plugin>\n" +
                        "                <groupId>org.apache.maven.plugins</groupId>\n" +
                        "                <artifactId>maven-compiler-plugin</artifactId>\n" +
                        "                <version>3.8.1</version>\n" +
                        "                <configuration>\n" +
                        "                    <source>${maven.compiler.source}</source>\n" +
                        "                    <target>${maven.compiler.target}</target>\n" +
                        "                </configuration>\n" +
                        "            </plugin>\n" +

                        // Descomente a opção desejada abaixo (Spring Boot ou Exec Maven Plugin)
                        // Opção 1: Plugin do Spring Boot

                        "            <plugin>\n" +
                        "                <groupId>org.springframework.boot</groupId>\n" +
                        "                <artifactId>spring-boot-maven-plugin</artifactId>\n" +
                        "                <version>3.0.0</version>\n" +
                        "            </plugin>\n" +

                        // Opção 2: Plugin Exec Maven para rodar projetos Java genéricos

                        /*
                        "            <plugin>\n" +
                        "                <groupId>org.codehaus.mojo</groupId>\n" +
                        "                <artifactId>exec-maven-plugin</artifactId>\n" +
                        "                <version>3.0.0</version>\n" +
                        "                <configuration>\n" +
                        "                    <mainClass>com.example.Main</mainClass>\n" +
                        "                </configuration>\n" +
                        "            </plugin>\n" +
                        */

                        "        </plugins>\n" +
                        "    </build>\n" +
                        "</project>");
                System.out.println("pom.xml file created with default configuration.");
            }
        }
    }

    public static void createProfileConfig(String profile) {
        String fileName = "src/main/resources/application-" + profile + ".properties";
        try {
            if (new File(fileName).createNewFile()) {
                System.out.println("Configuration profile created: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
