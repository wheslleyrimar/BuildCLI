package org.buildcli.commands.project;

import org.buildcli.actions.ai.AIChat;
import org.buildcli.actions.ai.AIServiceParams;
import org.buildcli.actions.ai.factories.GeneralAIServiceFactory;
import org.buildcli.actions.ai.params.JlamaAIServiceParams;
import org.buildcli.actions.ai.params.OllamaAIServiceParams;
import org.buildcli.constants.ConfigDefaultConstants;
import org.buildcli.domain.BuildCLICommand;
import org.buildcli.domain.configs.BuildCLIConfig;
import org.buildcli.utils.config.ConfigContextLoader;
import org.buildcli.utils.filesystem.FindFilesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Command(name = "document-code", aliases = {"docs"}, description = "", mixinStandardHelpOptions = true)
public class DocumentCodeCommand implements BuildCLICommand {
  private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
  private final Logger logger = LoggerFactory.getLogger("DocumentCodeCommand");

  @Parameters
  private List<String> files;

  private final BuildCLIConfig allConfigs = ConfigContextLoader.getAllConfigs();


  @Override
  public void run() {
    if (files == null || files.isEmpty()) {
      return;
    }

    logger.info("Loading files...");
    var targetFiles = files.parallelStream()
        .map(File::new)
        .map(FindFilesUtils::searchJavaFiles)
        .flatMap(List::stream)
        .toList();
    logger.info("Found {} files.", targetFiles.size());

    var execsAsync = new CompletableFuture[targetFiles.size()];

    logger.info("Commenting files {}...", targetFiles.size());
    for (int i = 0; i < targetFiles.size(); i++) {
      execsAsync[i] = supplyAsync(createCodeDocumenter(targetFiles.get(i)), executorService)
          .thenAccept(saveSourceCodeDocumented(targetFiles.get(i)))
          .exceptionally(catchAnyError(targetFiles.get(i)));
    }

    CompletableFuture.allOf(execsAsync).join();
  }


  private Supplier<String> createCodeDocumenter(File source) {
    try {
      logger.info("Reading source file: {}", source.getAbsolutePath());
      var sourceCode = Files.readAllLines(source.toPath());
      logger.info("Source file read: {}", source.getAbsolutePath());

      var aiParams = createAIParamsFromConfigs();
      var iaService = new GeneralAIServiceFactory().create(aiParams);

      logger.info("Commenting with IA...");
      return () -> iaService.generate(new AIChat("Document this code: \n\n%s".formatted(sourceCode)));

    } catch (IOException e) {
      return () -> {
        logger.warn("Could not read source file: {}", source.getAbsolutePath());
        throw new RuntimeException("Unable to read source file: " + source, e);
      };
    }
  }

  private AIServiceParams createAIParamsFromConfigs() {
    var aiVendor = allConfigs.getProperty(ConfigDefaultConstants.AI_VENDOR).orElse("jlama");

    return switch (aiVendor.toLowerCase()) {
      case "ollama" -> {
        var url = allConfigs.getProperty(ConfigDefaultConstants.AI_URL).orElse(null);
        var model = allConfigs.getProperty(ConfigDefaultConstants.AI_MODEL).orElse(null);

        yield new OllamaAIServiceParams(url, model);
      }
      case "jlama" -> new JlamaAIServiceParams(allConfigs.getProperty(ConfigDefaultConstants.AI_MODEL).orElse(null));
      default -> throw new IllegalStateException("Unexpected AI Vendor: " + aiVendor);
    };
  }

  private Consumer<String> saveSourceCodeDocumented(File file) {
    return sourceCode -> {
      try {
        Files.writeString(file.toPath(), sourceCode);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    };
  }

  private Function<Throwable, Void> catchAnyError(File file) {
    return throwable -> {
      var message = "Occurred an error when try comment code, file: %s".formatted(file);
      logger.error(message, throwable);

      return null;
    };
  }
}
