package edu.kgu.log;

/**
 * logLogger
 * 
 * @author seanshen
 *
 */
public final class LogLogger {

	protected static LogCommon logCom = new LogCommon();  

    public static void info(Object message) {
    	logCom.logger.info(message);
    }

    public static void debug(Object message) {
    	logCom.logger.debug(message);
    }

    public static void error(Object message) {
    	logCom.logger.error(message);
    }

    public static void fatal(Object message) {
    	logCom.logger.fatal(message);
    }
}
