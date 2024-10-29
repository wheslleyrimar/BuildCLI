package org.buildcli.utils;

public enum SystemCommands {
    MVN {
        public String getCommand(){
            boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
            String command = "mvn";
            if(isWindows)
                command += ".cmd";
            return command;
        }
    };
    public abstract String getCommand();
}
