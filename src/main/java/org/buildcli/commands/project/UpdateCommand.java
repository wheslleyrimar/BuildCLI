package org.buildcli.commands.project;

import org.buildcli.commands.project.update.DependencyCommand;
import org.buildcli.commands.project.update.VersionCommand;
import picocli.CommandLine.Command;

@Command(name = "update", aliases = {"up"},
 description = "Updates project dependencies and version. Alias: 'up'. Use this command to keep dependencies up to date and update the project version.", mixinStandardHelpOptions = true,
    subcommands = {VersionCommand.class, DependencyCommand.class}
)
public class UpdateCommand {
}
