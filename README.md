```
,-----.          ,--.,--.   ,--. ,-----.,--.   ,--.
|  |) /_ ,--.,--.`--'|  | ,-|  |'  .--./|  |   |  |
|  .-.  \|  ||  |,--.|  |' .-. ||  |    |  |   |  |       Built by the community, for the community
|  '--' /'  ''  '|  ||  |\ `-' |'  '--'\|  '--.|  |
`------'  `----' `--'`--' `---'  `-----'`-----'`--'

Welcome to BuildCLI - Java Project Management!
```

# BuildCLI

**BuildCLI** is a command-line interface (CLI) tool for managing and automating common tasks in Java project development. It allows you to create, compile, manage dependencies, and run Java projects directly from the terminal, simplifying the development process.

- **Repository:** [https://github.com/wheslleyrimar/buildcli](https://github.com/wheslleyrimar/buildcli)
- **License:** [MIT](https://opensource.org/licenses/MIT)

---

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Examples](#examples)
- [Contribution](#contribution)
- [License](#license)

---

## Features

- **Initialize Project**: Creates the basic structure of directories and files for a Java project.
- **Compile Project**: Compiles the project source code using Maven.
- **Add Dependency**: Adds new dependencies to the `pom.xml`.
- **Remove Dependency**: Remove dependencies from `pom.xml`.
- **Document Code**: [Beta] Generates documentation for a Java file using AI.
- **Manage Configuration Profiles**: Creates specific configuration files for profiles (`application-dev.properties`, `application-test.properties`, etc.).
- **Run Project**: Starts the project directly from the CLI using Spring Boot.
- **Dockerize Project**: Generates a Dockerfile for the project, allowing easy containerization.
- **Build and Run Docker Container**: Builds and runs the Docker container using the generated Dockerfile.
- **CI/CD Integration**: Automatically generates configuration files por CI/CD tools (e.g., Jenkins, Github Actions) and triggers pipelines based on project changes.

---

## Installation

1. **Script Installation**:
Just download the .sh or .bat file and execute.

     - On a Unix-like system (Linux, macOS), simply give execution permission to `install.sh` and run it:  

     ```bash
     sudo chmod +x install.sh  
     ./install.sh  
     ```

     - On Windows: Run `install.bat` by double-clicking it or executing the following command in the Command Prompt (cmd):  

     ```cmd
     install.bat
     ```

Now `BuildCLI` is ready to use. Test the `buildcli` command in the terminal.

---

## Usage

We made a major refactor of the `BuildCLI` architecture. Please use the `buildcli help` command to see all available options. Also, refer to issue [#89](https://github.com/wheslleyrimar/BuildCLI/issues/89) and pull request [#79](https://github.com/wheslleyrimar/BuildCLI/pull/79) for more details.

---

## Examples

### 1. Initialize a New Project

Creates the basic Java project structure, including `src/main/java`, `pom.xml`, and `README.md`.
You can specify a project name to dynamically set the package structure and project artifact.

#### Example Commands

- To initialize a project with a specific name:

```bash
buildcli project init MyProject
```

This will create the project structure with `MyProject` as the base package name, resulting in a directory like `src/main/java/org/myproject`.

- To initialize a project without specifying a name:

```bash
buildcli project init
```

This will create the project structure with `buildcli` as the base package name, resulting in a directory like `src/main/java/org/buildcli`.

### 2. Compile the Project

Compiles the Java project using Maven:

```bash
buildcli project build --compile
```

### 3. Add a Dependency to `pom.xml`

Adds a dependency to the project in the `groupId:artifactId` format. You can also specify a version using the format `groupId:artifactId:version`. If no version is specified, the dependency will default to the latest version available.

#### Example Commands

- To add a dependency with the latest version:

```bash
  buildcli project add dependency org.springframework:spring-core
```

- To add a dependency with a specified version:

```bash
  buildcli p a d org.springframework:spring-core:5.3.21
```

After executing these commands, the dependency will be appended to your pom.xml file under the `<dependencies>` section.

### 4. Create a Configuration Profile

Creates a configuration file with the specified profile, for example, `application-dev.properties`:

```bash
buildcli project add profile dev
```

### 5. Run the Project

Runs the Java project using Spring Boot:

```bash
buildcli project run
```

### 6. Generate Documentation for Java Code

Automatically generates inline documentation for a Java file using AI:

```bash
buildcli project document-code File.java
```

This command sends the specified Java file to the local Ollama server, which generates documentation and comments directly within the code. The modified file with documentation will be saved back to the same location.

### 7. Set Active Environment Profile

Sets the active environment profile, saving it to the `environment.config` file. The profile is referenced during project execution, ensuring that the correct configuration is loaded.

```bash
buildcli p set env dev
```

After running this command, the active profile is set to dev, and the `environment.config` file is updated accordingly.

#### Active Profile Display During Project Execution

With the `--set-environment` functionality, you can set the active environment profile. When running the project with `buildcli --run`, the active profile will be displayed in the terminal.

### 8. Dockerize Command

This command generates a `Dockerfile` for your Java project, making it easier to containerize your application.

```bash
buildcli p add dockerfile
```

### 9. Docker Build Command

This command automatically builds and runs the Docker container for you. After running the command, the Docker image will be created, and your project will run inside the container.

```bash
buildcli project run docker
```

### 10. Set Up CI/CD Integration

Generates configuration files for CI/CD tools and prepares the project for automated pipelines. Supports Jenkins, Gitlab and Github Actions.

```bash
buildcli project add pipeline github
```

```bash
buildcli project add pipeline gitlab
```

```bash
buildcli project add pipeline jenkins
```

---

## Prerequisites

### Local Ollama API

Ensure you have the Ollama server running locally, as the `docs` functionality relies on an AI model accessible via a local API.

- [Download Ollama](https://ollama.com/download)

You can start the Ollama server by running:

```bash
ollama run llama3.2
```

### Prerequisites for CI/CD Integration

- **Jenkins**: Ensure Jenkins is installed and accessible in your environment.
- **GitHub Actions**: Ensure your repository is hosted on GitHub with Actions enabled.

---

## Contribution

Contributions are welcome! Feel free to open **Issues** and submit **Pull Requests**.
See the [CONTRIBUTING.md](CONTRIBUTING.md) file for more details.

Quick steps to contribute:

1. Fork the project.
2. Create a branch for your changes:

   ```bash
   git checkout -b feature/my-feature
   ```

3. Commit your changes:

   ```bash
   git commit -m "My new feature"
   ```

4. Push to your branch:

   ```bash
   git push origin feature/my-feature
   ```

5. Open a Pull Request in the main repository.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
