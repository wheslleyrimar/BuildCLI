package org.buildcli.actions.ai.factories;

import org.buildcli.actions.ai.params.JlamaAIServiceParams;
import org.buildcli.actions.ai.service.JLamaAIService;

public class JLamaAIServiceFactory implements AIServiceFactory<JLamaAIService, JlamaAIServiceParams> {

  @Override
  public JLamaAIService create(JlamaAIServiceParams params) {

    return JLamaAIService.builder().modelName(params.model().orElse("tjake/Qwen2.5-0.5B-Instruct-JQ4")).build();
  }
}
