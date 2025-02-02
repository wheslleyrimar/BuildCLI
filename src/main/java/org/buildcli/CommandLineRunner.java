package org.buildcli;

import org.buildcli.log.config.LoggingConfig;
import picocli.CommandLine;

public class CommandLineRunner {
  public static void main(String[] args) {
    LoggingConfig.configure();

    var commandLine = new CommandLine(new BuildCLI());

    int exitCode = commandLine.execute(args);

    System.exit(exitCode);
  }
}
