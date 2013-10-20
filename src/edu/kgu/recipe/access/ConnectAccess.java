package edu.kgu.recipe.access;

import edu.kgu.log.LogLogger;
import edu.kgu.sql.DBConnect;
import edu.kgu.util.PropertiesFile;

public class ConnectAccess {

	private DBConnect _dbCon;
	
	private String _driver;
	private String _url;
	private String _userName;
	private String _password;
	
	public ConnectAccess(){
		CreateDBConnect();
	}
	
	private void CreateDBConnect() {
		PropertiesFile pFile;
		try {
			String cfgFile = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			cfgFile = cfgFile + "WSDir/config/db.properties";
			
			pFile = new PropertiesFile(cfgFile);
			this._driver = pFile.getValue("driver");
			this._url = pFile.getValue("url");
			this._userName = pFile.getValue("userName");
			this._password = pFile.getValue("password");	
					
		} catch (Exception e) {
			LogLogger.error(e);
		}
	}
	
	public DBConnect getDBConnect() {
		this._dbCon = new DBConnect(_driver,_url,_userName,_password);
		this._dbCon.Connect();
		return this._dbCon;
	}
}
