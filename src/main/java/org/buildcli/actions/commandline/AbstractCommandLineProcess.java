package org.buildcli.actions.commandline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommandLineProcess implements CommandLineProcess {
  protected final List<String> commands = new ArrayList<>();
  private BufferedReader reader = null;

  protected AbstractCommandLineProcess(String mainCommand) {
    commands.add(mainCommand);
  }

  @Override
  public int run() {
    try {
      var process = new ProcessBuilder(commands).start();

      var result = process.waitFor();

      if (result != 0) {
        reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
      } else {
        reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      }

      return result;
    } catch (InterruptedException | IOException e) {
      return -1;
    }
  }

  @Override
  public List<String> output() {
    var lines = new ArrayList<String>();

    try {
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return lines;
  }
}
