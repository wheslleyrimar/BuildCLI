package org.buildcli;

import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.Spec;

public class OptionCommand {

    @Spec
    CommandSpec spec;

    @Option(names = {"-i", "--init"}, arity = "0..1", paramLabel = "<projectName>", description = "Initialize a new Java Project with an optional project name")
    String projectName;

    @Option(names = {"-c", "--compile"}, description = "Compile a Java Project")
    boolean compile;

    @Option(names = {"--add-dependency"}, split = ",", description = "Add a dependency in 'groupId:artifactId' or 'groupId:artifactId:version' format")
    String[] dependency;

    @Option(names = {"--rm-dependency"}, split = ",", description = "Remove a dependency in 'groupId:artifactId' format")
    String[] rmDependency;

    @Option(names = {"-p", "--profile"}, description = "Create a configuration profile")
    String profile;

    @Option(names = {"-e", "--set-environment"}, description = "Set the environment for the project (e.g., dev, test, prod)")
    String environment;

    @Option(names = {"--run"}, description = "Run the Java Project")
    boolean run;

    @Option(names = {"-d", "--document-code"}, description = "Automatically document a Java file or all Java files in a project directory")
    String fileToDocument;

    @Option(names = {"-u", "--update"}, description = "Check for dependency updates")
    boolean update;

    @Option(names = {"--update-now"}, description = "Update dependencies to latest versions")
    boolean updateNow;

    @Option(names = {"-t", "--test"}, description = "Run tests")
    boolean test;

    @Option(names = {"--dockerize"}, description = "Generate a Dockerfile for the project")
    boolean dockerize;

    @Option(names = {"--docker-build"}, description = "Build and run the Docker container")
    boolean dockerBuild;

    @Option(names = {"--semver"}, description = "Manage semantic versioning (major, minor, patch)")
    String semver;

    @Option(names = {"--release"}, description = "Automate release by creating a Git tag and changelog")
    boolean release;

    @Option(names = {"--cicd-config"}, description = "Configure CI/CD for the specified tool (e.g., github, gitlab, jenkins)")
    String cicdTool;

    @Option(names = {"-a","--about"}, description = "Displays project information, including its purpose, repository, and contributors.")
    boolean about;

    @Option(names = {"--cleanup"}, description = "Remove the target folder")
    boolean cleanup;

    @Option(names = {"--skip-maven-install", "--skip-mvn-install"}, description = "Skip maven installation")
    boolean skipMvnInstall;
}
