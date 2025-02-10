package org.buildcli.commands.config;

import org.buildcli.commands.ConfigCommand;
import org.buildcli.domain.BuildCLICommand;
import org.buildcli.exceptions.ConfigException;
import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.buildcli.constants.ConfigDefaultConstants.BUILD_CLI_CONFIG_FILE_NAME;
import static org.buildcli.constants.ConfigDefaultConstants.BUILD_CLI_CONFIG_GLOBAL_FILE;

@Command(name = "clear", aliases = {"c"}, mixinStandardHelpOptions = true, description = "Clear config file")
public class ClearCommand implements BuildCLICommand {
  @ParentCommand
  private ConfigCommand configCommand;


  @Override
  public void run() {
    var isLocal = configCommand.isLocal();

    var propertyFile = isLocal ? Path.of(BUILD_CLI_CONFIG_FILE_NAME) : BUILD_CLI_CONFIG_GLOBAL_FILE;

    try {
      Files.deleteIfExists(propertyFile);
    } catch (IOException e) {
      throw new ConfigException("Occurred an error when try clear the file: %s".formatted(propertyFile), e);
    }
  }
}
