package org.buildcli.commands;

import org.buildcli.commands.project.*;
import picocli.CommandLine.Command;

@Command(name = "project", aliases = {"p"}, description = "Manage and create Java projects, alias: 'p'.",
    subcommands = {
        AddCommand.class, RmCommand.class, BuildCommand.class, SetCommand.class,
        TestCommand.class, RunCommand.class, InitCommand.class, CleanupCommand.class,
        UpdateCommand.class, DocumentCodeCommand.class
    },
    mixinStandardHelpOptions = true
)
public class ProjectCommand {

}
