package org.buildcli.commands.doctor;

import org.buildcli.actions.tools.ToolChecker;
import org.buildcli.actions.tools.ToolCheckers;
import org.buildcli.domain.BuildCLICommand;
import picocli.CommandLine.Command;

@Command(
    name = "fix",
    description = "Scans the environment for required tools and dependencies, identifies issues, and attempts to automatically resolve them. This command ensures the build system is properly configured and ready for use.",
    mixinStandardHelpOptions = true
)
public class FixCommand implements BuildCLICommand {
  @Override
  public void run() {
    System.out.println("Attempting to fix issues...");
    ToolCheckers.all().stream().filter(FixCommand::isNotInstalled).forEach(checker -> {
      System.out.println("Fixing " + checker.name() + "...");
      checker.fixIssue();
    });
  }

  private static boolean isNotInstalled(ToolChecker checker) {
    return !checker.isInstalled();
  }
}
