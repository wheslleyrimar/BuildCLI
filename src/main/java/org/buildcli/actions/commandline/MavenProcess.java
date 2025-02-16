package org.buildcli.actions.commandline;

import org.buildcli.constants.MavenConstants;
import org.buildcli.utils.MavenInstaller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.buildcli.utils.tools.ToolChecks.checksMaven;

public class MavenProcess extends AbstractCommandLineProcess {
  private MavenProcess() {
    super(MavenConstants.MAVEN_CMD);
  }

  public static MavenProcess createProcessor(String... goals) {
    var processor = new MavenProcess();
    processor.commands.addAll(Arrays.asList(goals));
    return processor;
  }

  public static MavenProcess createPackageProcessor() {
    return createProcessor("clean", "package");
  }

  public static MavenProcess createCompileProcessor() {
    return createProcessor("clean", "compile");
  }

  public static MavenProcess createGetVersionProcessor() {
    return createProcessor("--version");
  }

}
