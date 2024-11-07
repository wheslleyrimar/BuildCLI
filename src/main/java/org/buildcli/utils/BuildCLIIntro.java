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
    	SystemOutLogger.log("|  .-.  \\|  ||  |,--.|  |' .-. ||  |    |  |   |  |       By Wheslley Rimar");
    	SystemOutLogger.log("|  '--' /'  ''  '|  ||  |\\ `-' |'  '--'\\|  '--.|  |");
    	SystemOutLogger.log("`------'  `----' `--'`--' `---'  `-----'`-----'`--'");
    	SystemOutLogger.log("");
    }
}
