package org.buildcli;

import java.util.HashMap;

import org.buildcli.core.ProjectCompiler;
import org.buildcli.core.ProjectInitializer;
import org.buildcli.core.ProjectRunner;
import org.buildcli.core.ProjectTester;
import org.buildcli.core.ProjectUpdater;
import org.buildcli.exception.ThrowingComandExecutorWrapper;
import org.buildcli.utils.*;

public class OptionCommandMap extends HashMap<String, CommandExecutor> {

	private static final long serialVersionUID = 1L;

	public OptionCommandMap(OptionCommand optionCommand) {

		super();
		var wrapper = new ThrowingComandExecutorWrapper();

		this.put("-i", () -> wrapper.wrap(() -> new ProjectInitializer().initializeProject(optionCommand.projectName != null ? optionCommand.projectName : "buildcli")));
		this.put("--init", () -> wrapper.wrap(() -> new ProjectInitializer().initializeProject(optionCommand.projectName != null ? optionCommand.projectName : "buildcli")));
		this.put("-c", () -> new ProjectCompiler().compileProject());
		this.put("--compile", () -> new ProjectCompiler().compileProject());
		this.put("--add-dependency", () -> PomUtils.addDependencyToPom(optionCommand.dependency));
		this.put("--rm-dependency", () -> PomUtils.rmDependencyToPom(optionCommand.rmDependency));
		this.put("-p", () -> ProjectInitializer.createProfileConfig(optionCommand.profile));
		this.put("--profile", () -> ProjectInitializer.createProfileConfig(optionCommand.profile));
		this.put("-e", () -> EnvironmentConfigManager.setEnvironment(optionCommand.environment));
		this.put("--set-environment", () -> EnvironmentConfigManager.setEnvironment(optionCommand.environment));
		this.put("--run", () -> new ProjectRunner().runProject());
		this.put("-d", () -> CodeDocumenter.getDocumentationFromOllama(optionCommand.fileToDocument));
		this.put("--document-code", () -> CodeDocumenter.getDocumentationFromOllama(optionCommand.fileToDocument));
		this.put("-u", () -> new ProjectUpdater().execute());
		this.put("--update", () -> new ProjectUpdater().execute());
		this.put("--update-now", () -> new ProjectUpdater().updateNow(true).execute());
		this.put("-t", () -> new ProjectTester().execute());
		this.put("--test", () -> new ProjectTester().execute());
		this.put("-k", () -> new DockerManager().setupDocker());
		this.put("--dockerize", () -> new DockerManager().setupDocker());
		this.put("--docker-build", () -> new DockerBuildRunner().buildAndRunDocker());
		this.put("--semver", () -> new SemVerManager().manageVersion(optionCommand.semver));
		this.put("--release", () -> new ReleaseManager().automateRelease());
		this.put("--cicd-config", () -> {
			if (optionCommand.cicdTool == null || optionCommand.cicdTool.isBlank()) {
				throw new IllegalArgumentException("You must specify a CI/CD tool (e.g., github, gitlab, jenkins).");
			}
			new CICDManager().configureCICD(optionCommand.cicdTool);
		});
		this.put("--about", () -> new BuildCLIIntro().about());
		this.put("-a", () -> new BuildCLIIntro().about());
	}
}
