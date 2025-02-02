package org.buildcli.commands;

import org.buildcli.commands.project.AddCommand;
import org.buildcli.commands.project.BuildCommand;
import org.buildcli.commands.project.RmCommand;
import picocli.CommandLine.Command;

@Command(name = "project", aliases = {"p"}, description = "",
    subcommands = {AddCommand.class, RmCommand.class, BuildCommand.class},
    mixinStandardHelpOptions = true
)
public class ProjectCommand {

}
