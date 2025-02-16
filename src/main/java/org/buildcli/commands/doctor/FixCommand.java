package org.buildcli.commands.doctor;

import org.buildcli.actions.tools.ToolChecker;
import org.buildcli.actions.tools.ToolCheckers;
import org.buildcli.domain.BuildCLICommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;

@Command(
    name = "fix",
    description = "Scans the environment for required tools and dependencies, identifies issues, and attempts to automatically resolve them. This command ensures the build system is properly configured and ready for use.",
    mixinStandardHelpOptions = true
)
public class FixCommand implements BuildCLICommand {
  private final Logger logger = LoggerFactory.getLogger("DoctorFixCommand");
  @Override
  public void run() {
    logger.warn("Fix command requires admin privileges.");
    logger.info("Scanning environment...");
    var notInstalledTools = ToolCheckers.all().stream().filter(FixCommand::isNotInstalled);

    if (notInstalledTools.findAny().isEmpty()) {
      logger.info("No issues found.");
      return;
    }

    logger.info("Attempting to fix issues...");
    ToolCheckers.all().stream().filter(FixCommand::isNotInstalled).forEach(checker -> {
      logger.info("Fixing {}...", checker.name());
      checker.fixIssue();
    });
  }

  private static boolean isNotInstalled(ToolChecker checker) {
    return !checker.isInstalled();
  }
}
