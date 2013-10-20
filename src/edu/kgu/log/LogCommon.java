package edu.kgu.log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * LogCommon
 * 
 * @author seanshen
 *
 */
public class LogCommon {
	/** init logger */
	public Logger logger = Logger.getLogger(LogLogger.class.getClass());  

	/**
	 * Set the log4j settingfile
	 */
    public LogCommon() {
    	String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		PropertyConfigurator.configure(path + "WSDir/config/log4j.properties");
    }
}
