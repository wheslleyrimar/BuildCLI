package org.buildcli.actions.ai;

import java.util.Optional;

public interface AIServiceParams {
  Optional<String> model();
  String vendor();
}
