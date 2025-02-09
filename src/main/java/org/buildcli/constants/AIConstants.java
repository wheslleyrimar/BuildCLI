package org.buildcli.constants;

public abstract class AIConstants {
  public static final String COMMENT_CODE_PROMPT = """
            You are a helpful assistant. Document the following Java code following these guidelines:
            1. Add concise and meaningful Javadoc comments.
            2. Avoid adding redundant explanations or suggestions within the code.
            3. Focus on good practices for getters and setters, such as returning the attribute directly without additional logic or comments.
            4. Do not include 'TODO' or hypothetical improvement suggestions.
            5. Preserve the original structure of the code.
      """;
}
