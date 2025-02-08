package org.buildcli.commands.config;

import org.buildcli.commands.ConfigCommand;
import org.buildcli.domain.BuildCLICommand;
import org.buildcli.domain.configs.BuildCLIConfig;
import org.buildcli.utils.config.ConfigContextLoader;
import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "init", aliases = {"i", "create"}, description = "", mixinStandardHelpOptions = true)
public class InitCommand implements BuildCLICommand {
  @ParentCommand
  private ConfigCommand configCommand;

  @Override
  public void run() {
    var buildConfig = BuildCLIConfig.empty();
    buildConfig.setLocal(configCommand.isLocal());

    SaveConfig saveConfig = configCommand.isLocal() ? ConfigContextLoader::saveLocalConfig : ConfigContextLoader::saveGlobalConfig;

    saveConfig.save(buildConfig);
  }

  @FunctionalInterface
  private interface SaveConfig {
    void save(BuildCLIConfig buildConfig);
  }
}
