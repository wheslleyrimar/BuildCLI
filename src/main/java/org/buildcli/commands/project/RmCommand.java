package org.buildcli.commands.project;

import org.buildcli.commands.project.rm.DependencyCommand;
import picocli.CommandLine.Command;

@Command(aliases = {"remove", "rm"}, description = "", subcommands = {DependencyCommand.class}, mixinStandardHelpOptions = true)
public class RmCommand {

}
