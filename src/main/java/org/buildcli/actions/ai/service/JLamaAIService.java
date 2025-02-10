package org.buildcli.actions.ai.service;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.jlama.JlamaChatModel;
import dev.langchain4j.model.jlama.JlamaChatModel.JlamaChatModelBuilder;
import org.buildcli.actions.ai.AIChat;
import org.buildcli.actions.ai.AIService;

import java.nio.file.Path;
import java.util.Arrays;

import static org.buildcli.utils.ia.CodeUtils.endCode;
import static org.buildcli.utils.ia.CodeUtils.startCode;

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
    var aiMessageResponse = model.generate(
        new SystemMessage(chat.getSystemMessage()),
        new UserMessage(chat.getUserMessage())
    );

    var content = aiMessageResponse.content().text();

    int codeStart = startCode(content);
    int codeEnd = endCode(content);

    if (codeStart != -1 && codeEnd != -1) {
      content = content.substring(codeStart, codeEnd).trim();
    }

    return content;
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
