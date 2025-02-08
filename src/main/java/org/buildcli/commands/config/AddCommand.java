package org.buildcli.commands.config;

import org.buildcli.commands.ConfigCommand;
import org.buildcli.domain.BuildCLICommand;
import org.buildcli.domain.configs.BuildCLIConfig;
import org.buildcli.exceptions.ConfigException;
import org.buildcli.utils.config.ConfigContextLoader;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

import java.util.List;
import java.util.regex.Pattern;

import static org.buildcli.utils.config.ConfigContextLoader.getGlobalConfig;
import static org.buildcli.utils.config.ConfigContextLoader.getLocalConfig;

public class AddCommand implements BuildCLICommand {
  @ParentCommand
  private ConfigCommand configCommand;

  @Parameters
  private List<String> configs;

  private final Pattern pattern = Pattern.compile("^[A-Za-z0-9-]+(.[A-Za-z0-9])*=([A-Za-z0-9-]+(.[A-Za-z0-9])*)*$");

  @Override
  public void run() {
    if (configCommand.isGlobal() == configCommand.isLocal())
      throw new ConfigException("Config scope cannot be local and global in same time");

    var buildCliConfig = configCommand.isLocal() ? getLocalConfig() : getGlobalConfig();

    for (var config : configs) {
      if (!pattern.matcher(config).matches()) throw new ConfigException("Config property must have <key>=<value>");

      var splitValue = config.split("=", 2);
      buildCliConfig.addOrSetProperty(splitValue[0], splitValue[1]);

    }

    SaveConfig command = configCommand.isLocal() ? ConfigContextLoader::saveLocalConfig : ConfigContextLoader::saveGlobalConfig;

    command.save(buildCliConfig);

  }

  @FunctionalInterface
  private interface SaveConfig {
    void save(BuildCLIConfig config) throws ConfigException;
  }
}
