package org.buildcli.core;

import java.util.List;

public class ProjectUpdater extends ProjectExecutor {

	@Override
	protected List<String> getMvnCommand() {
		return List.of("versions:display-dependency-updates");
	}

	@Override
	protected String getErrorMessage() {
		return "Failed to check dependency updates";
	}
}
