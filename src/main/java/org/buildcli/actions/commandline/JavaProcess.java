package org.buildcli.actions.commandline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaProcess extends AbstractCommandLineProcess {
  private JavaProcess() {
    super("java");
  }

  public static JavaProcess createRunJarProcess(String jarName) {
    return createProcess("-jar", jarName);
  }

  public static JavaProcess createGetVersionProcess() {
    return createProcess("--version");
  }

  public static JavaProcess createProcess(String... args) {
    var process = new JavaProcess();

    process.commands.addAll(List.of(args));

    return process;
  }
}
