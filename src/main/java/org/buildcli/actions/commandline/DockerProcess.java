package org.buildcli.actions.commandline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DockerProcess extends AbstractCommandLineProcess{
  private DockerProcess() {
    super("docker");
  }

  public static DockerProcess createBuildProcess(String tag) {
    return createBuildProcess(tag, null);
  }

  public static DockerProcess createBuildProcess(String tag, String fileName) {
    var process = new DockerProcess();

    process.commands.addAll(List.of("build", "-t", tag));

    if (Objects.isNull(fileName)) {
      process.commands.add(".");
    } else {
      process.commands.add("-f");
      process.commands.add(fileName);
    }

    return process;
  }

  public static DockerProcess createRunProcess(String tagName) {
    return createProcess("run", "-p", "8080:8080", tagName);
  }

  public static DockerProcess createProcess(String... args) {
    var process = new DockerProcess();

    process.commands.addAll(List.of(args));

    return process;
  }

  public static DockerProcess createGetVersionProcess() {
    return createProcess("-v");
  }

  public static DockerProcess createInfoProcess() {
    return createProcess("info");
  }
}
