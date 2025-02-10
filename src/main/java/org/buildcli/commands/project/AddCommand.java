package org.buildcli.commands.project;

import org.buildcli.commands.project.add.DependencyCommand;
import org.buildcli.commands.project.add.DockerfileCommand;
import org.buildcli.commands.project.add.PipelineCommand;
import org.buildcli.commands.project.add.ProfileCommand;
import picocli.CommandLine.Command;

@Command(name = "add", aliases = {"a"}, description = "Adds a new item to the project. This command "
        + "allows adding dependencies, pipelines, profiles, and Dockerfiles.",
        subcommands = {DependencyCommand.class, PipelineCommand.class, ProfileCommand.class, DockerfileCommand.class},
        mixinStandardHelpOptions = true
)
public class AddCommand {

}
