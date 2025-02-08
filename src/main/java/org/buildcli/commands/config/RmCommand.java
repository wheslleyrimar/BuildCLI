package org.buildcli.commands.config;

import org.buildcli.commands.ConfigCommand;
import org.buildcli.domain.BuildCLICommand;
import org.buildcli.domain.configs.BuildCLIConfig;
import org.buildcli.exceptions.ConfigException;
import org.buildcli.log.SystemOutLogger;
import org.buildcli.utils.config.ConfigContextLoader;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

import java.util.List;

import static org.buildcli.utils.config.ConfigContextLoader.getGlobalConfig;
import static org.buildcli.utils.config.ConfigContextLoader.getLocalConfig;

public class RmCommand implements BuildCLICommand {
  @ParentCommand
  private ConfigCommand configCommand;

  @Parameters
  private List<String> configs;

  @Override
  public void run() {
    if (configCommand.isGlobal() == configCommand.isLocal())
      throw new ConfigException("Config scope cannot be local and global in same time");

    var buildCliConfig = configCommand.isLocal() ? getLocalConfig() : getGlobalConfig();
    var total = 0;

    for (var config : configs) {
      if(buildCliConfig.removeProperty(config)) {
        total++;
      }
    }

    SaveConfig command = configCommand.isLocal() ? ConfigContextLoader::saveLocalConfig : ConfigContextLoader::saveGlobalConfig;

    command.save(buildCliConfig);

    SystemOutLogger.log("Removed %d properties with successfully".formatted(total));
  }

  @FunctionalInterface
  private interface SaveConfig {
    void save(BuildCLIConfig buildConfig);
  }
}
