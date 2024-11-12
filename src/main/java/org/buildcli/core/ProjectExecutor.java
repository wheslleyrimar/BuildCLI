package org.buildcli.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.buildcli.utils.SystemCommands;

public abstract class ProjectExecutor {

	private static final Logger logger = Logger.getLogger(ProjectExecutor.class.getName());
	
	protected abstract List<String> getMvnCommand();
	
	protected abstract String getErrorMessage();
	
    public void execute() {
    	
    	var command = new ArrayList<String>();
    	command.add(SystemCommands.MVN.getCommand());
    	command.addAll(this.getMvnCommand());
    	
        try {
            var builder = new ProcessBuilder(command);
            builder.inheritIO();
            var process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, this.getErrorMessage(), e);
            Thread.currentThread().interrupt();
        }
    }
}
