package org.buildcli.commands.project;

import org.buildcli.actions.commandline.MavenProcess;
import org.buildcli.domain.BuildCLICommand;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.logging.Logger;

@Command(name = "build", aliases = {"b"}, description = "", mixinStandardHelpOptions = true)
public class BuildCommand implements BuildCLICommand {
  private final Logger logger = Logger.getLogger(BuildCommand.class.getName());

  @Option(names = {"--compileOnly", "--compile", "-c"}, description = "", defaultValue = "false")
  private boolean compileOnly;


  @Override
  public void run() {
    MavenProcess process;
    if (compileOnly) {
      process = MavenProcess.createCompileProcessor();
    } else {
      process = MavenProcess.createPackageProcessor();
    }

    int exitCode = process.run();

    if (exitCode == 0) {
      logger.info("Project compiled successfully. JAR file generated in target directory.");
    } else {
      logger.severe("Failed to compile project. Maven exited with code: " + exitCode);
    }
  }
}
