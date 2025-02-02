package org.buildcli.commands;

import org.buildcli.domain.BuildCLICommand;
import picocli.CommandLine;

@CommandLine.Command(aliases = {}, description = "", subcommands = {})
public class VersionCommand implements BuildCLICommand {

  @Override
  public void run() {

  }
}
