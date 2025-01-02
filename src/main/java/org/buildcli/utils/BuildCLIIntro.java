package org.buildcli.utils;

import org.buildcli.log.SystemOutLogger;

/*
*
*
,-----.          ,--.,--.   ,--. ,-----.,--.   ,--.
|  |) /_ ,--.,--.`--'|  | ,-|  |'  .--./|  |   |  |
|  .-.  \|  ||  |,--.|  |' .-. ||  |    |  |   |  |
|  '--' /'  ''  '|  ||  |\ `-' |'  '--'\|  '--.|  |
`------'  `----' `--'`--' `---'  `-----'`-----'`--'

*
* */
public class BuildCLIIntro {

	public BuildCLIIntro() { }

    public static void welcome(){
    	SystemOutLogger.log(",-----.          ,--.,--.   ,--. ,-----.,--.   ,--.");
    	SystemOutLogger.log("|  |) /_ ,--.,--.`--'|  | ,-|  |'  .--./|  |   |  |");
    	SystemOutLogger.log("|  .-.  \\|  ||  |,--.|  |' .-. ||  |    |  |   |  |       Built by the community, for the community");
    	SystemOutLogger.log("|  '--' /'  ''  '|  ||  |\\ `-' |'  '--'\\|  '--.|  |");
    	SystemOutLogger.log("`------'  `----' `--'`--' `---'  `-----'`-----'`--'");
    	SystemOutLogger.log("");
    }

	public static void about() {
		SystemOutLogger.log("BuildCLI is a command-line interface (CLI) tool for managing and automating common tasks in Java project development.\n" +
				"It allows you to create, compile, manage dependencies, and run Java projects directly from the terminal, simplifying the development process.\n");
		SystemOutLogger.log("Visit the repository for more details: https://github.com/wheslleyrimar/BuildCLI\n");

		String contributors = new ReleaseManager().showContributors();

		SystemOutLogger.log("Contributors:\n");
		SystemOutLogger.log(contributors);


	}

}
