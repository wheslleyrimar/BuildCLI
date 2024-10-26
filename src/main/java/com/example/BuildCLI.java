package com.example;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Command(name = "buildcli", mixinStandardHelpOptions = true, version = "1.0",
        description = "BuildCLI - Um CLI para Gerenciamento de Projetos Java")
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
                System.out.println("Bem-vindo ao BuildCLI - Gerenciamento de Projetos Java!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeProject() throws IOException {
        // Diretórios padrão do projeto
        String[] dirs = {"src/main/java", "src/main/resources", "src/test/java"};
        for (String dir : dirs) {
            File directory = new File(dir);
            if (directory.mkdirs()) {
                System.out.println("Criado o diretório: " + dir);
            }
        }

        // Arquivo README.md
        File readme = new File("README.md");
        if (readme.createNewFile()) {
            System.out.println("Arquivo README.md criado.");
        }

        // Arquivo pom.xml com conteúdo básico (ou pode ser criado para build.gradle, se preferir)
        File pomFile = new File("pom.xml");
        if (pomFile.createNewFile()) {
            try (FileWriter writer = new FileWriter(pomFile)) {
                writer.write("<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                        "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                        "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                        "    <modelVersion>4.0.0</modelVersion>\n" +
                        "    <groupId>com.example</groupId>\n" +
                        "    <artifactId>BuildCLI</artifactId>\n" +
                        "    <version>1.0-SNAPSHOT</version>\n" +
                        "</project>");
                System.out.println("Arquivo pom.xml criado.");
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
            System.out.println("Dependência inválida. Use o formato 'groupId:artifactId'");
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
            System.out.println("Dependência adicionada ao pom.xml.");
        } catch (IOException e) {
            System.out.println("Erro ao adicionar dependência ao pom.xml");
            e.printStackTrace();
        }
    }

    private void createProfileConfig(String profile) {
        String fileName = "src/main/resources/application-" + profile + ".properties";
        File file = new File(fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("Perfil de configuração criado: " + fileName);
            } else {
                System.out.println("O perfil já existe: " + fileName);
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
