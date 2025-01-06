package org.buildcli.utils;

import org.buildcli.log.SystemOutLogger;

import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

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

	private static final ReleaseManager releaseManager =new ReleaseManager();
	private static final String localRepository = releaseManager.findGitRepository(getBuildCLIBuildDirectory());

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

		String contributors = releaseManager.showContributors(localRepository);

		SystemOutLogger.log("Contributors:\n");
		SystemOutLogger.log(contributors);
	}

	private static String getBuildCLIBuildDirectory() {
		try (InputStream inputStream = BuildCLIIntro.class.getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF")) {
			if (inputStream == null) {
				throw new IllegalStateException("Manifest not found.");
			}
			Manifest manifest = new Manifest(inputStream);
			Attributes attributes = manifest.getMainAttributes();
			String buildDirectory = attributes.getValue("Build-Directory");

			if (buildDirectory == null) {
				throw new IllegalStateException("Build-Directory not found in the Manifest.");
			}

			return buildDirectory;
		} catch (Exception e) {
			throw new RuntimeException("Error reading the Manifest.", e);
		}
	}

}
