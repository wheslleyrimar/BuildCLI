package com.example;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Command(name = "BuildCLI", mixinStandardHelpOptions = true, version = "BuildCLI 0.0.1",
        description = "BuildCLI - A CLI for Java Project Management")
public class BuildCLI implements Runnable {

    @Option(names = {"-i", "--init"}, description = "Initialize a new Java Project")
    boolean init;

    @Option(names = {"-c", "--compile"}, description = "Compile a Java Project")
    boolean compile;

    @Option(names = {"--add-dependency"}, description = "Add a dependency in 'groupId:artifactId' format")
    String dependency;

    @Option(names = {"-p", "--profile"}, description = "Create a configuration profile")
    String profile;

    @Option(names = {"--run"}, description = "Run the Java Project")
    boolean run;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new BuildCLI()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            if (init) {
                initializeProject();
            } else if (compile) {
                compileProject();
            } else if (dependency != null) {
                addDependencyToPom(dependency);
            } else if (profile != null) {
                createProfileConfig(profile);
            } else if (run) {
                runProject();
            } else {
                System.out.println("Welcome to BuildCLI - Java Project Management!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeProject() throws IOException {
        // Cria os diretórios principais do projeto
        String[] dirs = {"src/main/java", "src/main/resources", "src/test/java"};
        for (String dir : dirs) {
            File directory = new File(dir);
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + dir);
            }
        }

        // Cria o arquivo README.md
        File readme = new File("README.md");
        if (readme.createNewFile()) {
            System.out.println("README.md file created.");
        }

        // Define o diretório com o pacote
        File packageDir = new File("src/main/java/com/example");
        if (packageDir.mkdirs()) {
            System.out.println("Package directory created: " + packageDir.getPath());
        }

        // Cria o arquivo Main.java dentro do pacote
        File javaClass = new File(packageDir, "Main.java");
        if (javaClass.createNewFile()) {
            try (FileWriter writer = new FileWriter(javaClass)) {
                writer.write("package com.example;\n\n" +
                        "public class Main {\n" +
                        "    public static void main(String[] args) {\n" +
                        "        System.out.println(\"Hello, World!\");\n" +
                        "    }\n" +
                        "}\n");
                System.out.println("Main.java file created with package and basic content.");
            } catch (IOException e) {
                System.out.println("Error writing to Main.java");
                e.printStackTrace();
            }
        }

        // Cria o arquivo pom.xml com configurações básicas e com plugin específico
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


    private void compileProject() {
        try {
            ProcessBuilder builder = new ProcessBuilder("mvn", "compile");
            builder.inheritIO();
            Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addDependencyToPom(String dependency) {
        String[] parts = dependency.split(":");
        if (parts.length != 2) {
            System.out.println("Invalid dependency. Use the format 'groupId:artifactId'");
            return;
        }

        String groupId = parts[0];
        String artifactId = parts[1];
        String dependencyXml = String.format(
                "    <dependency>\n" +
                        "        <groupId>%s</groupId>\n" +
                        "        <artifactId>%s</artifactId>\n" +
                        "        <version>LATEST</version>\n" +
                        "    </dependency>\n", groupId, artifactId);

        try {
            String pomContent = new String(Files.readAllBytes(Paths.get("pom.xml")));
            pomContent = pomContent.replace("</dependencies>", dependencyXml + "</dependencies>");
            Files.write(Paths.get("pom.xml"), pomContent.getBytes());
            System.out.println("Dependency added to pom.xml.");
        } catch (IOException e) {
            System.out.println("Error adding dependency to pom.xml");
            e.printStackTrace();
        }
    }

    private void createProfileConfig(String profile) {
        String fileName = "src/main/resources/application-" + profile + ".properties";
        File file = new File(fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("Configuration profile created: " + fileName);
            } else {
                System.out.println("Profile already exists: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runProject() {
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
