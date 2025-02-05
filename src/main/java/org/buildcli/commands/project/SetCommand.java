package org.buildcli.commands.project;

import org.buildcli.commands.project.set.EnvironmentCommand;
import picocli.CommandLine.Command;

@Command(name = "set", description = "",
    subcommands = {EnvironmentCommand.class},
    mixinStandardHelpOptions = true
)
public class SetCommand {
}
