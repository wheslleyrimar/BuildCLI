package org.buildcli.commands.project;

import org.buildcli.commands.project.add.DependencyCommand;
import org.buildcli.commands.project.add.DockerfileCommand;
import org.buildcli.commands.project.add.PipelineCommand;
import org.buildcli.commands.project.add.ProfileCommand;
import picocli.CommandLine.Command;

@Command(name = "add", aliases = {"a"}, description = "",
    subcommands = {DependencyCommand.class, PipelineCommand.class, ProfileCommand.class, DockerfileCommand.class},
    mixinStandardHelpOptions = true
)
public class AddCommand {

}
