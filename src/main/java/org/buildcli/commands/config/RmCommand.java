package org.buildcli.commands.config;

import org.buildcli.commands.ConfigCommand;
import org.buildcli.domain.BuildCLICommand;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

import java.util.List;

public class RmCommand implements BuildCLICommand {
  @ParentCommand
  private ConfigCommand configCommand;

  @Parameters
  private List<String> configs;

  @Override
  public void run() {

  }
}
