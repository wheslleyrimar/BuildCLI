package org.buildcli;

import org.buildcli.commands.AboutCommand;
import org.buildcli.commands.ProjectCommand;
import org.buildcli.commands.VersionCommand;
import picocli.CommandLine.Command;

@Command(name = "BuildCLI", mixinStandardHelpOptions = true,
         version = "BuildCLI 0.0.8",
         description = "BuildCLI - A CLI for Java Project Management")
public class BuildCLI implements Runnable {

    @Mixin
    private OptionCommand optionCommand;

    public static void main(String[] args) {
        LoggingConfig.configure();

        int exitCode = new CommandLine(new BuildCLI()).execute(args);
        System.exit(exitCode);
    }

}
