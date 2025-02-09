package org.buildcli.commands;

import org.buildcli.BuildCLI;
import org.buildcli.domain.BuildCLICommand;
import picocli.CommandLine;

@CommandLine.Command(name = "version", aliases = {"v"}, description = "Displays the current version of the CLI.", mixinStandardHelpOptions = true)
public class VersionCommand implements BuildCLICommand {

  @Override
  public void run() {
    new CommandLine(new BuildCLI()).execute("-V");
  }
}
