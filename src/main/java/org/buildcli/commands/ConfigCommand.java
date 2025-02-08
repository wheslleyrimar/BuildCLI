package org.buildcli.commands;

import org.buildcli.commands.config.AddCommand;
import org.buildcli.commands.config.RmCommand;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "config", aliases = {"c"}, description = "", mixinStandardHelpOptions = true,
    subcommands = {AddCommand.class, RmCommand.class})
public class ConfigCommand {
  @Option(names = {"--global", "-g"}, description = "", defaultValue = "false")
  private boolean global;
  @Option(names = {"--local", "-l"}, description = "", defaultValue = "true")
  private boolean local;

  public boolean isGlobal() {
    return global;
  }

  public boolean isLocal() {
    return local;
  }
}
