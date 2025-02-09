package org.buildcli.actions.ai;

import java.util.UUID;

public class AIChat {
  private final UUID chatId = UUID.randomUUID();
  private final String message;

  public AIChat(String message) {
    this.message = message;
  }

  public UUID getChatId() {
    return chatId;
  }

  public String getMessage() {
    return message;
  }
}
