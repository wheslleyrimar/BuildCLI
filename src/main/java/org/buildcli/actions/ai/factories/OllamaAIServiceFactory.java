package org.buildcli.actions.ai.factories;

import org.buildcli.actions.ai.params.OllamaAIServiceParams;
import org.buildcli.actions.ai.service.OllamaAIService;

public class OllamaAIServiceFactory implements AIServiceFactory<OllamaAIService, OllamaAIServiceParams>{
  @Override
  public OllamaAIService create(OllamaAIServiceParams params) {
    return OllamaAIService.builder()
        .url(params.url())
        .modelName(params.model().orElse("llama3.2"))
        .build();
  }
}
