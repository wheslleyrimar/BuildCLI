package org.buildcli.exception;

import java.io.IOException;

@FunctionalInterface
public interface ThrowingComandExecutor {
	void exec() throws IOException;
}
