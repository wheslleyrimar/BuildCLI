package org.buildcli.core;

import org.buildcli.utils.SystemCommands;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ProjectExecutorTest {

    @Test
    public void shouldContainsMvnCommand(){
        TestMavenExecutor executor = new TestMavenExecutor();
        executor.execute();
        Assertions.assertTrue(executor.getCapturedCommand().contains(SystemCommands.MVN.getCommand()));
    }

    @Test
    public void shouldNotContainsMvnCommand(){
        TestNonMavenExecutor executor = new TestNonMavenExecutor();
        executor.execute();
        Assertions.assertFalse(executor.getCapturedCommand().contains(SystemCommands.MVN.getCommand()));
    }

    static class TestMavenExecutor extends ProjectExecutor {
        private List<String> capturedCommand;

        @Override
        protected void addCommand() {
            command.add(SystemCommands.MVN.getCommand());
            command.add("clean");
            command.add("install");
        }

        @Override
        protected String getErrorMessage() {
            return "Test error message";
        }

        @Override
        public void execute() {
            addCommand();
            capturedCommand = new ArrayList<>(command);
        }

        public List<String> getCapturedCommand() {
            return capturedCommand;
        }
    }

    static class TestNonMavenExecutor extends ProjectExecutor {
        private List<String> capturedCommand;

        @Override
        protected void addCommand() {
            command.add("clean");
            command.add("install");
        }

        @Override
        protected String getErrorMessage() {
            return "Test error message";
        }

        @Override
        public void execute() {
            addCommand();
            capturedCommand = new ArrayList<>(command);
        }

        public List<String> getCapturedCommand() {
            return capturedCommand;
        }
    }
}
