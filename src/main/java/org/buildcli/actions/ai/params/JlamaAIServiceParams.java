package org.buildcli.actions.ai.params;

import org.buildcli.actions.ai.AIServiceParams;

import java.util.Optional;

public class JlamaAIServiceParams implements AIServiceParams {
  private final String model;

  public JlamaAIServiceParams(String model) {
    this.model = model;
  }

  @Override
  public Optional<String> model() {
    return Optional.ofNullable(model);
  }

  @Override
  public String vendor() {
    return "jlama";
  }
}
