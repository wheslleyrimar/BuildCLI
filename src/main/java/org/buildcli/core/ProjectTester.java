package org.buildcli.core;

import org.buildcli.utils.SystemCommands;

public class ProjectTester extends ProjectExecutor {

	@Override
	protected void addCommand() {
		this.command.add(SystemCommands.MVN.getCommand());
		this.command.add("test");
	}

	@Override
	protected String getErrorMessage() {
		return "Failed to run tests";
	}

}
