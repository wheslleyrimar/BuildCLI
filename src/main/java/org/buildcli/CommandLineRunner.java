package org.buildcli;

import org.buildcli.log.config.LoggingConfig;
import org.buildcli.utils.BuildCLIIntro;
import picocli.CommandLine;

public class CommandLineRunner {
  public static void main(String[] args) {
    LoggingConfig.configure();
    BuildCLIIntro.welcome();

    var commandLine = new CommandLine(new BuildCLI());

    int exitCode = commandLine.execute(args);
    BuildCLIIntro.checkUpdates();

    System.exit(exitCode);
  }
}
