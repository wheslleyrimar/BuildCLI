package org.buildcli.log;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemOutLogger {

	private static final Logger logger = Logger.getAnonymousLogger();
	
	static {
		var handler = new ConsoleHandler();
		handler.setFormatter(new SystemOutFormatter());
		logger.addHandler(handler);
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.ALL);
	}
	
	private SystemOutLogger() { }
	
	public static void log(String message) {
		logger.info(message);
		logger.info("\n");
	}
}
