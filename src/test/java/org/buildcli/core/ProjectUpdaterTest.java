package org.buildcli.core;

import java.util.List;

import org.junit.jupiter.api.Test;

class ProjectUpdaterTest {

	private ProjectUpdater updater;
	
	public ProjectUpdaterTest() {
		this.updater = new ProjectUpdater();
	}

	@Test
	void shouldUpdatePomDependencies() {
		this.updater.setAdditionalParameters(List.of("-f", "src/test/resources/pom-core-test/pom.xml"));
		this.updater.updateNow(true).execute();
	}
}
