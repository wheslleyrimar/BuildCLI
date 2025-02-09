package org.buildcli.commands.project;

import org.buildcli.domain.BuildCLICommand;
import org.buildcli.utils.filesystem.FindFilesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Command(name = "document-code", aliases = {"docs"}, description = "", mixinStandardHelpOptions = true)
public class DocumentCodeCommand implements BuildCLICommand {
  private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
  private final Logger logger = LoggerFactory.getLogger("DocumentCodeCommand");

  @Parameters
  private List<String> files;

  @Override
  public void run() {
    if (files == null || files.isEmpty()) {
      return;
    }

    var targetFiles = files.parallelStream()
        .map(File::new)
        .map(FindFilesUtils::searchJavaFiles)
        .flatMap(List::stream)
        .toList();

    var execsAsync = new CompletableFuture[targetFiles.size()];

    for (int i = 0; i < targetFiles.size(); i++) {
      int finalI = i;
      execsAsync[i] = CompletableFuture.supplyAsync(() -> "")
          .thenAccept(s -> {
          })
          .exceptionally(throwable -> {
            var message = "Occurred an error when try comment code, file: %s".formatted(targetFiles.get(finalI));
            logger.error(message, throwable);

            return null;
          });
    }

    CompletableFuture.allOf(execsAsync).join();
  }
}
