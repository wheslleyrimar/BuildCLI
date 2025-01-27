package org.buildcli.utils;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class MavenInstaller {
  private MavenInstaller() {
  }

  private static final String MAVEN_NAME = "apache-maven-3.9.9";
  private static final String MAVEN_DOWNLOAD_URL = "https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/%s-bin.%s";

  public static void downloadMaven() throws IOException, InterruptedException {
    var request = HttpRequest.newBuilder().uri(URI.create(MAVEN_DOWNLOAD_URL)).GET().build();

    try (var client = HttpClient.newHttpClient()) {
      var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

      if (response.statusCode() != 200) {
        throw new IOException("Failed to download maven artifact: " + response.statusCode());
      }

      var contentLength = response.headers().firstValue("Content-Length").map(MavenInstaller::parseContentLengthToLong).orElse(0L);

      if (contentLength == 0L) {
        throw new IOException("Failed to download maven artifact: " + response.statusCode());
      }

      var mavenInstallDir = new File(MAVEN_NAME);

      if (mavenInstallDir.exists()) {

      }

      try (var bodyStream = response.body()) {
        var bytes = bodyStream.readAllBytes();

        try (var fos = new FileOutputStream(mavenInstallDir)) {
          fos.write(bytes);
        }
      }

      if (!mavenInstallDir.exists()) {
        throw new IOException("Failed to create maven download directory: " + mavenInstallDir);
      }


    }


  }

  private static Long parseContentLengthToLong(String s) {
    return s.isEmpty() ? 0 : Long.parseLong(s);
  }
}
