package org.buildcli.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectExecutorTest {

    private ProjectExecutor executor;

    @BeforeEach
    void setUp() {
        executor = new ProjectExecutor() {
            @Override
            protected void addMvnCommand() {
                // No-op for testing
            }

            @Override
            protected String getErrorMessage() {
                return "Error executing command";
            }

            @Override
            protected boolean isMavenRequired() {
                return false; // Override to test conditional logic
            }
        };
    }

    @Test
    void testMavenCommandNotAddedWhenNotRequired() {
        assertFalse(executor.command.contains("mvn"));
    }

    @Test
    void testMavenCommandAddedWhenRequired() {
        executor = new ProjectExecutor() {
            @Override
            protected void addMvnCommand() {
                // No-op for testing
            }

            @Override
            protected String getErrorMessage() {
                return "Error executing command";
            }

            @Override
            protected boolean isMavenRequired() {
                return true; // Override to test conditional logic
            }
        };
        assertTrue(executor.command.contains("mvn"));
    }
}
