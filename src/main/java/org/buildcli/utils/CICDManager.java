package org.buildcli.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CICDManager {

    private static final Logger logger = Logger.getLogger(CICDManager.class.getName());

    /**
     * Configura integração com a ferramenta CI/CD selecionada.
     *
     * @param toolName Nome da ferramenta de CI/CD (e.g., "github", "gitlab", "jenkins")
     */
    public void configureCICD(String toolName) {
        try {
            switch (toolName.toLowerCase()) {
                case "github":
                    createGitHubActionsConfig();
                    break;
                case "gitlab":
                    createGitLabCIConfig();
                    break;
                case "jenkins":
                    createJenkinsConfig();
                    break;
                default:
                    logger.warning("Unknown CI/CD tool: " + toolName);
                    return;
            }
            logger.info("CI/CD configuration created successfully for " + toolName);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to configure CI/CD for " + toolName, e);
        }
    }

    private void createGitHubActionsConfig() throws IOException {
        File workflowDir = new File(".github/workflows");
        if (!workflowDir.exists() && !workflowDir.mkdirs()) {
            throw new IOException("Failed to create .github/workflows directory.");
        }

        File workflowFile = new File(workflowDir, "ci.yml");
        try (FileWriter writer = new FileWriter(workflowFile)) {
            writer.write("""
                name: Build and Test

                on:
                  push:
                    branches:
                      - main
                  pull_request:
                    branches:
                      - main

                jobs:
                  build:
                    runs-on: ubuntu-latest

                    steps:
                      - name: Checkout code
                        uses: actions/checkout@v3

                      - name: Set up JDK 17
                        uses: actions/setup-java@v3
                        with:
                          java-version: '17'

                      - name: Build with Maven
                        run: mvn clean install

                      - name: Run tests
                        run: mvn test
                """);
        }
    }

    private void createGitLabCIConfig() throws IOException {
        File gitlabCIFile = new File(".gitlab-ci.yml");
        try (FileWriter writer = new FileWriter(gitlabCIFile)) {
            writer.write("""
                stages:
                  - build
                  - test

                build:
                  stage: build
                  script:
                    - mvn clean install

                test:
                  stage: test
                  script:
                    - mvn test
                """);
        }
    }

    private void createJenkinsConfig() throws IOException {
        File jenkinsFile = new File("Jenkinsfile");
        try (FileWriter writer = new FileWriter(jenkinsFile)) {
            writer.write("""
                pipeline {
                    agent any

                    stages {
                        stage('Build') {
                            steps {
                                sh 'mvn clean install'
                            }
                        }
                        stage('Test') {
                            steps {
                                sh 'mvn test'
                            }
                        }
                    }
                }
                """);
        }
    }
}
