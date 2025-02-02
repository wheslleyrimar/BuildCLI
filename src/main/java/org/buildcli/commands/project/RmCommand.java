package org.buildcli.commands.project;

import org.buildcli.commands.project.rm.DependencyCommand;
import org.buildcli.commands.project.rm.ProfileCommand;
import picocli.CommandLine.Command;

@Command(aliases = {"remove", "rm"}, description = "",
    subcommands = {DependencyCommand.class, ProfileCommand.class},
    mixinStandardHelpOptions = true
)
public class RmCommand {

}
