package org.buildcli.actions.ai.service;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.buildcli.actions.ai.AIChat;
import org.buildcli.actions.ai.AIService;
import org.buildcli.constants.AIConstants;

public class OllamaAIService implements AIService {
  private final OllamaChatModel model;

  private OllamaAIService() {
    this(null);
  }

  private OllamaAIService(OllamaChatModel model) {
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
    private final OllamaChatModel.OllamaChatModelBuilder builder;

    private Builder() {
      builder = new OllamaChatModel.OllamaChatModelBuilder();

      builder.temperature(.7).maxRetries(3);
    }

    public Builder modelName(String modelName) {
      builder.modelName(modelName);
      return this;
    }

    public Builder url(String url) {
      builder.baseUrl(url);
      return this;
    }

    public OllamaAIService build() {
      return new OllamaAIService(builder.build());
    }

  }
}
