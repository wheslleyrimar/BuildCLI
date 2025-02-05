package org.buildcli.commands.project.update;

import org.buildcli.core.ProjectUpdater;
import org.buildcli.domain.BuildCLICommand;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "dependency", aliases = {"d"}, description = "", mixinStandardHelpOptions = true)
public class DependencyCommand implements BuildCLICommand {
  @Option(names = {"--checkOnly"}, description = "", defaultValue = "false")
  private boolean checkOnly;

  @Override
  public void run() {
    ProjectUpdater updater = new ProjectUpdater();
    updater.updateNow(!checkOnly).execute();
  }
}
