package org.buildcli.commands.project.upgrade;

import org.buildcli.domain.BuildCLICommand;
import org.buildcli.utils.SemVerManager;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "version", aliases = {"ver", "v"}, description = "", mixinStandardHelpOptions = true)
public class VersionCommand implements BuildCLICommand {
  @CommandLine.Parameters(index = "0")
  private String upgradeVersionType;

  @Override
  public void run() {
    new SemVerManager().manageVersion(upgradeVersionType);
  }
}
