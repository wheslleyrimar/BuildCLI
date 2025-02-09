package org.buildcli.commands.project;

import org.buildcli.commands.project.update.DependencyCommand;
import org.buildcli.commands.project.update.VersionCommand;
import picocli.CommandLine.Command;

@Command(name = "update", aliases = {"up"},
 description = "Updates project dependencies and settings. Alias: 'up'. This command allows updating dependencies and configuration settings to keep the project up to date.", mixinStandardHelpOptions = true,
    subcommands = {VersionCommand.class, DependencyCommand.class}
)
public class UpdateCommand {
}
