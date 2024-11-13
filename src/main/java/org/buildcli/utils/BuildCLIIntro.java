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

	private BuildCLIIntro() { }

    public static void welcome(){
    	SystemOutLogger.log(",-----.          ,--.,--.   ,--. ,-----.,--.   ,--.");
    	SystemOutLogger.log("|  |) /_ ,--.,--.`--'|  | ,-|  |'  .--./|  |   |  |");
    	SystemOutLogger.log("|  .-.  \\|  ||  |,--.|  |' .-. ||  |    |  |   |  |       Built by the community, for the community");
    	SystemOutLogger.log("|  '--' /'  ''  '|  ||  |\\ `-' |'  '--'\\|  '--.|  |");
    	SystemOutLogger.log("`------'  `----' `--'`--' `---'  `-----'`-----'`--'");
    	SystemOutLogger.log("");
    }
}
