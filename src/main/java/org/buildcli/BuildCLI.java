package org.buildcli;

import java.util.Objects;

import org.buildcli.log.SystemOutLogger;
import org.buildcli.log.config.LoggingConfig;
import org.buildcli.utils.BuildCLIIntro;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

@Command(name = "BuildCLI", mixinStandardHelpOptions = true,
         version = "BuildCLI 0.0.8",
         description = "BuildCLI - A CLI for Java Project Management")
public class BuildCLI implements Runnable {

    @Mixin
    private OptionCommand optionCommand;

    @Override
    public void run() {

        BuildCLIIntro.welcome();

    	var options = optionCommand.spec.commandLine().getParseResult().originalArgs();

    	if (Objects.isNull(options) || options.isEmpty()) {
    		SystemOutLogger.log("Welcome to BuildCLI - Java Project Management!");
    	} else {
    		var optionsMap = new OptionCommandMap(this.optionCommand);
    		optionsMap.get(options.iterator().next()).exec();
    	}

        BuildCLIIntro.checkUpdates();
    }
}
