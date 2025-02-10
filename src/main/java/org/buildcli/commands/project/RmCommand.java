package org.buildcli.commands.project;

import org.buildcli.commands.project.rm.DependencyCommand;
import org.buildcli.commands.project.rm.ProfileCommand;
import picocli.CommandLine.Command;

@Command(name = "remove", aliases = {"rm"}, description = "Removes dependencies and profiles from the project, alias: 'rm'.",
    subcommands = {DependencyCommand.class, ProfileCommand.class},
    mixinStandardHelpOptions = true
)
public class RmCommand {

}
