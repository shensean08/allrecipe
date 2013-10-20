package edu.kgu.util;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Read Properties File from file path
 *
 */
public class PropertiesFile {
	private Properties _properties ;
	private FileInputStream _fileInputStream ;

	public PropertiesFile(String file) throws Exception {
		this._properties = new Properties();
		this._fileInputStream = new FileInputStream(file);
		this._properties.load(this._fileInputStream);
		this._fileInputStream.close();

	}
	    
	public String getValue(String key) {
		String rtnValue = null;
		
		if (this._properties.containsKey(key)){
			rtnValue = this._properties.getProperty(key);
		}
		return rtnValue;
	}
}
