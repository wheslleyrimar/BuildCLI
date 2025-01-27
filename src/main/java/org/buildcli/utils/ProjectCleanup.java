package org.buildcli.utils;

import org.buildcli.log.SystemOutLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class ProjectCleanup {

  public static void cleanup() {
    var targetPath = new File("target").toPath();

    if (!Files.exists(targetPath)) {
      SystemOutLogger.log("The 'target' directory does not exist.");
      return;
    }

    try {
      Files.walkFileTree(targetPath, new SimpleFileVisitor<>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          Files.delete(file);
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
          Files.delete(dir);
          return FileVisitResult.CONTINUE;
        }
      });
      SystemOutLogger.log("The 'target' directory was successfully cleaned.");
    } catch (IOException e) {
      SystemOutLogger.log("Error clearing 'target' directory': " + e.getMessage());
    }
  }
}
