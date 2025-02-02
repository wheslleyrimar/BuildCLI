package org.buildcli.exceptions;

public class ThrowingComandExecutorWrapper {
	
    public void wrap(ThrowingComandExecutor throwingCommandExecutor) {
		try {
			throwingCommandExecutor.exec();
		} catch (Exception e) {
			throw new CommandExecutorRuntimeException(e);
		}
    }
}
