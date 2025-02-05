package org.buildcli.commands.project;

import org.buildcli.domain.BuildCLICommand;
import org.buildcli.utils.CodeDocumenter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Command(name = "document-code", aliases = {"docs"}, description = "", mixinStandardHelpOptions = true)
public class DocumentCodeCommand implements BuildCLICommand {
  private final ExecutorService executorService = Executors.newCachedThreadPool();

  @Parameters
  private List<String> files;

  @Override
  public void run() {
    if (files == null || files.isEmpty()) {
      return;
    }

    var execsAsync = new CompletableFuture[files.size()];

    for (int i = 0; i < files.size(); i++) {
      final var finalI = i;
      execsAsync[i] = CompletableFuture.runAsync(() -> {
        CodeDocumenter.getDocumentationFromOllama(files.get(finalI));
      }, executorService);
    }

    CompletableFuture.allOf(execsAsync).join();
  }
}
