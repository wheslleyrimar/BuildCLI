package org.buildcli.utils.config;

import org.buildcli.domain.configs.BuildCLIConfig;

public abstract class ConfigContextLoader {
  private static BuildCLIConfig localConfig = null;
  private static BuildCLIConfig globalConfig = null;

  public static BuildCLIConfig getLocalConfig() {
    if (localConfig == null) {
      localConfig = ConfigsOperationsUtils.getLocal().orElse(null);
    }

    return localConfig;
  }

  public static BuildCLIConfig getGlobalConfig() {
    if (globalConfig == null) {
      globalConfig = ConfigsOperationsUtils.getGlobal().orElse(null);
    }

    return globalConfig;
  }

  public static void saveLocalConfig(BuildCLIConfig localConfig) {
    localConfig.setLocal(true);
    ConfigsOperationsUtils.set(localConfig);
  }

  public static void saveGlobalConfig(BuildCLIConfig globalConfig) {
    globalConfig.setLocal(false);
    ConfigsOperationsUtils.set(globalConfig);
  }
}
