package org.buildcli.actions.ai.service;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.jlama.JlamaChatModel;
import dev.langchain4j.model.jlama.JlamaChatModel.JlamaChatModelBuilder;
import org.buildcli.actions.ai.AIChat;
import org.buildcli.actions.ai.AIService;
import org.buildcli.constants.AIConstants;

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
    var response = model.generate(new SystemMessage(AIConstants.COMMENT_CODE_PROMPT), new UserMessage(chat.getMessage()));

    return response.content().text();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private final JlamaChatModelBuilder builder;

    private Builder() {
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
