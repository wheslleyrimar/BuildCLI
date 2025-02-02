package org.buildcli;

import org.buildcli.commands.AboutCommand;
import org.buildcli.commands.ProjectCommand;
import org.buildcli.commands.VersionCommand;
import picocli.CommandLine.Command;

@Command(name = "BuildCLI", mixinStandardHelpOptions = true,
    version = "BuildCLI 0.0.8",
    description = "BuildCLI - A CLI for Java Project Management",
    subcommands = {AboutCommand.class, ProjectCommand.class, VersionCommand.class}
)
public class BuildCLI {

}
