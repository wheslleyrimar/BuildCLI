package org.buildcli.actions.ai.factories;

import org.buildcli.actions.ai.AIService;
import org.buildcli.actions.ai.AIServiceParams;

public interface AIServiceFactory<S extends AIService,T extends AIServiceParams> {
  S create(T params);
}
