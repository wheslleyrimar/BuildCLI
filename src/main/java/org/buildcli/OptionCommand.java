package org.buildcli;

import picocli.CommandLine.Option;
import picocli.CommandLine.Spec;
import picocli.CommandLine.Model.CommandSpec;

public class OptionCommand {

	@Spec	
	CommandSpec spec;
	
    @Option(names = {"-i", "--init"}, description = "Initialize a new Java Project")
    boolean init;

    @Option(names = {"-c", "--compile"}, description = "Compile a Java Project")
    boolean compile;

    @Option(names = {"--add-dependency"}, split = ",", description = "Add a dependency in 'groupId:artifactId' or 'groupId:artifactId:version' format")
    String[] dependency;
    
	@Option(names = {"--rm-dependency"}, split = ",", description = "Remove a dependency in 'groupId:artifactId' format")
    String[] rmDependency;
	
    @Option(names = {"-p", "--profile"}, description = "Create a configuration profile")
    String profile;

    @Option(names = {"--run"}, description = "Run the Java Project")
    boolean run;

    @Option(names = {"-d", "--document-code"}, description = "Automatically document the Java code in the specified file")
    String fileToDocument;
    
    @Option(names = {"-u", "--updates"}, description = "Check for dependency updates")
    boolean updates;
}
