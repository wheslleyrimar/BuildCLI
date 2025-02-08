# Installation

> [!IMPORTANT] Pre-requisites
> Before installing BuildCLI, ensure that you have the following prerequisites installed on your system:
> - [Git](https://git-scm.com/downloads)
> - [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
> - [Apache Maven](https://maven.apache.org/download.cgi)

BuildCLI is a command-line interface (CLI) tool for managing and automating common tasks in Java project development. It
allows you to create, compile, manage dependencies, and run Java projects directly from the terminal, simplifying the
development process.

## Install from source

::: code-group

```bash [Windows]
git clone https://github.com/wheslleyrimar/buildcli.git
cd BuildCLI
mvn package
copy target\buildcli C:\path\to\your\bin\buildcli
```

```bash [Linux]
git clone https://github.com/wheslleyrimar/buildcli.git
cd BuildCLI
mvn package
cp target/buildcli ~/bin/buildcli
chmod +x ~/bin/buildcli
```

```bash [Mac]
git clone https://github.com/wheslleyrimar/buildcli.git
cd BuildCLI
mvn package
cp target/buildcli /usr/local/bin/buildcli
chmod +x /usr/local/bin/buildcli
```

:::

Now `BuildCLI` is ready to use. Test the `buildcli` command in the terminal.