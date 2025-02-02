package org.buildcli.commands.project;

import org.buildcli.commands.project.upgrade.VersionCommand;
import picocli.CommandLine.Command;

@Command(name = "upgrade", aliases = {"up"}, description = "", mixinStandardHelpOptions = true,
    subcommands = {VersionCommand.class}
)
public class UpgradeCommand {
}
