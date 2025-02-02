package org.buildcli.commands;

import org.buildcli.commands.project.AddCommand;
import org.buildcli.commands.project.RmCommand;
import picocli.CommandLine;

@CommandLine.Command(aliases = {"project", "p"}, description = "", subcommands = {AddCommand.class, RmCommand.class}, mixinStandardHelpOptions = true)
public class ProjectCommand {

}
