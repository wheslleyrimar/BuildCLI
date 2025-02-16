package org.buildcli.commands.doctor;

import org.buildcli.actions.tools.DockerChecker;
import org.buildcli.actions.tools.ToolCheckers;
import org.buildcli.domain.BuildCLICommand;
import picocli.CommandLine.Command;

@Command(
    name = "scan",
    description = "Performs a comprehensive scan of the environment to check for required tools, their installation status, versions, and readiness (e.g., running state for Docker). Provides detailed instructions for installing missing tools.",
    mixinStandardHelpOptions = true
)
public class ScanCommand implements BuildCLICommand {
  @Override
  public void run() {
    ToolCheckers.all().forEach(toolChecker -> {
      System.out.println("Running environment scan...");
      System.out.println("Checking " + toolChecker.name() + "...");

      if (toolChecker.isInstalled()) {
        System.out.println("  Installed, version: " + toolChecker.version());
        if (toolChecker instanceof DockerChecker c && !c.isRunning()) {
          System.out.println("  Docker is installed but not running.");
        }
      } else {
        System.out.println("  Not installed. " + toolChecker.installInstructions());
      }
    });
  }
}
