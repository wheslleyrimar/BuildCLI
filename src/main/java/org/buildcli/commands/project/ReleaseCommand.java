package org.buildcli.commands.project;

import org.buildcli.domain.BuildCLICommand;
import org.buildcli.utils.ReleaseManager;
import picocli.CommandLine.Command;

@Command(name = "release", description = "", mixinStandardHelpOptions = true)
public class ReleaseCommand implements BuildCLICommand {
  @Override
  public void run() {
    new ReleaseManager().automateRelease();
  }
}
