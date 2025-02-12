package org.buildcli.commands;

import org.buildcli.domain.BuildCLICommand;
import org.buildcli.utils.AutoCompleteManager;
import picocli.CommandLine.Command;

@Command(name = "autocomplete", description = "Sets up autocomplete for BuildCLI commands to enhance user experience by suggesting commands as you type.", mixinStandardHelpOptions = true)
public class AutocompleteCommand implements BuildCLICommand {
  @Override
  public void run() {
    new AutoCompleteManager().setupAutocomplete();
  }
}
