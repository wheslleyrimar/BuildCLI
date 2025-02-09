package org.buildcli.actions.ai;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.model.jlama.JlamaChatModel;
import dev.langchain4j.model.jlama.JlamaChatModel.JlamaChatModelBuilder;

import java.nio.file.Path;

public class JLamaAIService implements AIService {
  private final JlamaChatModel model;

  private JLamaAIService() {
    this(null);
  }

  private JLamaAIService(JlamaChatModel model) {
    this.model = model;
  }

  @Override
  public String generate(AIChat chat) {

    var response = model.generate(new SystemMessage(""));

    return response.content().text();
  }

  public static class Builder {
    private final JlamaChatModelBuilder builder;

    public Builder() {
      builder = new JlamaChatModelBuilder();
      var path = Path.of(System.getProperty("user.home"), ".buildcli", "ai", "jlama");

      builder.workingDirectory(path).modelCachePath(path).temperature(.7f);
    }

    public Builder modelName(String modelName) {
      builder.modelName(modelName);
      return this;
    }

    public JLamaAIService build() {
      return new JLamaAIService(builder.build());
    }
  }
}
