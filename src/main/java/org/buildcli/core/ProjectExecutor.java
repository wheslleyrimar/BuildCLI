package org.buildcli.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ProjectExecutor {

	private static final Logger logger = Logger.getLogger(ProjectExecutor.class.getName());

	protected final List<String> command;

	protected abstract void addCommand();

	protected abstract String getErrorMessage();

	protected ProjectExecutor() {
		this.command = new ArrayList<>();
	}

    public void execute() {

    	this.addCommand();
    	
        try {
            var builder = new ProcessBuilder(this.command);
            builder.inheritIO();
            var process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, this.getErrorMessage(), e);
            Thread.currentThread().interrupt();
        }
    }
}
