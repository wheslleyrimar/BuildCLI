package org.buildcli.exceptions;

import java.io.IOException;

@FunctionalInterface
public interface ThrowingComandExecutor {
	void exec() throws IOException;
}
