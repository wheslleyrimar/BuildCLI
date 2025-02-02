package org.buildcli;

import org.buildcli.log.config.LoggingConfig;
import picocli.CommandLine;

public class CommandLineRunner {
  public static void main(String[] args) {
    LoggingConfig.configure();

    int exitCode = new CommandLine(new BuildCLI()).execute(args);

    System.exit(exitCode);
  }
}
