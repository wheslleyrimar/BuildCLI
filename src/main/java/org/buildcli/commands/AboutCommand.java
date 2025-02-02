package org.buildcli.commands;

import org.buildcli.domain.BuildCLICommand;
import picocli.CommandLine;

@CommandLine.Command(aliases = {"about", "a"}, description = "", mixinStandardHelpOptions = true)
public class AboutCommand implements BuildCLICommand {

  @Override
  public void run() {

  }
}
