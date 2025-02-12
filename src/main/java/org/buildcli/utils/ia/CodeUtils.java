package org.buildcli.utils.ia;

public abstract class CodeUtils {
  public static int endCode(String content) {
    return content.indexOf("```", startCode(content) + 3);
  }

  public static int startCode(String content) {
    if (content == null) {
      return -1;
    }

    if (content.contains("```java")) {
      return content.indexOf("```java") + 7;
    } else if (content.contains("```kotlin")) {
      return content.indexOf("```kotlin") + 9;
    } else if (content.contains("```scala")) {
      return content.indexOf("```scala") + 8;
    } else if (content.contains("```groovy")) {
      return content.indexOf("```groovy") + 9;
    }

    return -1;
  }
}
