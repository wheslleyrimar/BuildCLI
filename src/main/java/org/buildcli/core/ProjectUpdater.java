package org.buildcli.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectUpdater extends ProjectExecutor {

	private static final String MVN_CHECK_UPDATES = "versions:display-dependency-updates";
	private static final String MVN_LATEST_RELEASES = "versions:use-latest-releases";
	
	private final List<String> command;
	private boolean isUpdateNow;
	
	public ProjectUpdater() {
		this.command = new ArrayList<>();
	}
	
	@Override
	protected List<String> getMvnCommand() {
		this.command.add(this.isUpdateNow ? MVN_LATEST_RELEASES : MVN_CHECK_UPDATES);
		return command;
	}

	@Override
	protected String getErrorMessage() {
		return "Failed to check/update dependencies";
	}

	public ProjectUpdater setAdditionalParameters(List<String> additionalParameters) {
		
		if (Objects.nonNull(additionalParameters)) {
			this.command.addAll(additionalParameters);
		}
		
		return this;
	}
	
	public ProjectUpdater updateNow(boolean update) {
		this.isUpdateNow = update;
		return this;
	}
}
