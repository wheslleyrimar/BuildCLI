package org.buildcli.utils.config;

import org.buildcli.domain.configs.BuildCLIConfig;
import org.buildcli.exceptions.ConfigException;

import java.io.*;
import java.util.Optional;

import static org.buildcli.constants.ConfigDefaultConstants.BUILD_CLI_CONFIG_FILE_NAME;
import static org.buildcli.constants.ConfigDefaultConstants.BUILD_CLI_CONFIG_GLOBAL_FILE;

class ConfigsOperationsUtils {
  public static Optional<BuildCLIConfig> getLocal() {
    var file = new File(BUILD_CLI_CONFIG_FILE_NAME);

    if (!file.exists()) {
      return Optional.empty();
    }

    var buildCLIConfig = BuildCLIConfig.from(file);
    buildCLIConfig.setLocal(true);

    return Optional.of(buildCLIConfig);
  }

  public static Optional<BuildCLIConfig> getGlobal() {
    var file = BUILD_CLI_CONFIG_GLOBAL_FILE.toFile();

    if (!file.exists()) {
      return Optional.empty();
    }

    var buildCLIConfig = BuildCLIConfig.from(file);
    buildCLIConfig.setLocal(false);

    return Optional.of(buildCLIConfig);
  }

  public static void set(BuildCLIConfig buildCLIConfig) {
    var properties = buildCLIConfig.getProperties();
    var file = buildCLIConfig.isLocal() ? new File(BUILD_CLI_CONFIG_FILE_NAME) : BUILD_CLI_CONFIG_GLOBAL_FILE.toFile();
    try (var fileWriter = new FileWriter(file)) {
      var builder = new StringBuilder();

      for (var entry : properties.entrySet()) {
        builder.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
      }

      fileWriter.write(builder.toString());

    } catch (IOException e) {
      throw new ConfigException(e.getMessage());
    }
  }
}
