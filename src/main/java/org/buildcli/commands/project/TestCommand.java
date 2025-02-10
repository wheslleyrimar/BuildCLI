package org.buildcli.commands.project;

import org.buildcli.core.ProjectTester;
import org.buildcli.domain.BuildCLICommand;
import picocli.CommandLine.Command;

@Command(name = "test", aliases = {"t"}, description = "Executes the project tests, alias: 't'.", mixinStandardHelpOptions = true)
public class TestCommand implements BuildCLICommand {
  @Override
  public void run() {
    new ProjectTester().execute();
  }
}
