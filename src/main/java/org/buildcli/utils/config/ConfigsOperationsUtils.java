package org.buildcli.utils.config;

import org.buildcli.domain.configs.BuildCLIConfig;
import org.buildcli.exceptions.ConfigException;
import org.buildcli.log.SystemOutLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.buildcli.constants.ConfigDefaultConstants.BUILD_CLI_CONFIG_FILE_NAME;
import static org.buildcli.constants.ConfigDefaultConstants.BUILD_CLI_CONFIG_GLOBAL_FILE;

class ConfigsOperationsUtils {

  private static Optional<BuildCLIConfig> loadConfig(File file, boolean isLocal) {
    if (!file.exists()) {
      return Optional.empty();
    }

    var buildCLIConfig = BuildCLIConfig.from(file);
    buildCLIConfig.setLocal(isLocal);
    return Optional.of(buildCLIConfig);
  }

  public static Optional<BuildCLIConfig> getLocal() {
    return loadConfig(new File(BUILD_CLI_CONFIG_FILE_NAME), true);
  }

  public static Optional<BuildCLIConfig> getGlobal() {
    return loadConfig(BUILD_CLI_CONFIG_GLOBAL_FILE.toFile(), false);
  }

  public static void set(BuildCLIConfig buildCLIConfig) {
    var properties = buildCLIConfig.getProperties();
    var file = buildCLIConfig.isLocal() ? new File(BUILD_CLI_CONFIG_FILE_NAME) : BUILD_CLI_CONFIG_GLOBAL_FILE.toFile();

    try {
      var builder = new StringBuilder();

      for (var entry : properties) {
        builder.append(entry.name()).append("=").append(entry.value()).append("\n");
      }

      File parent = file.getParentFile();
      if (parent != null && !parent.exists()) {
        SystemOutLogger.log("Creating global config directory...");
        parent.mkdirs();
      }

      Files.writeString(file.toPath(), builder.toString());

      SystemOutLogger.log("Config file saved at " + file.getAbsolutePath());

    } catch (IOException e) {
      throw new ConfigException("Error writing config file", e);
    }
  }
}
