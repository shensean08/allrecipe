package edu.kgu.sql;

import java.io.InputStream;
import java.sql.*;

import edu.kgu.log.LogLogger;

public class DBConnect {
	private String _driver;
	private String _url;
	private String _userName;
	private String _passWord;
	
	private Connection _conn = null;
    private Statement _stat = null;
    private PreparedStatement pstmt = null;
    
    public DBConnect(String driver,  String url,
    		         String userName,String passWord) {
    	this._driver = driver;
    	this._url = url;
    	this._userName = userName;
    	this._passWord = passWord;
    }
    
    public void Connect()  {
    	try {
			Class.forName(this._driver);
			this._conn = DriverManager.getConnection(this._url,this._userName,this._passWord);
	    	this._stat = this._conn.createStatement();
		} catch (Exception e) {
			LogLogger.error(e);
		}    	
    }

    public void Close()  {
    	try {
    		this._stat.close();
			this._conn.close();
		} catch (SQLException e) {
			LogLogger.error(e);
		}
    }
    
    public boolean Execute(String sql) {
    	boolean rtnValue;
    	
    	try {
    		rtnValue = this._stat.execute(sql);
		} catch (SQLException e) {
			rtnValue = false;
		}
		
		return rtnValue;
    }
    
    public ResultSet ExecuteQuery(String sql) {
    	ResultSet rtnRs = null;
    	try {
    		rtnRs = this._stat.executeQuery(sql);
		} catch (SQLException e) {
			LogLogger.error(e);
		}
		
		return rtnRs;
    }
    
    public void PrepareExecute(String sql) {
    	try {
			pstmt = this._conn.prepareStatement(sql);
		} catch (SQLException e) {
			LogLogger.error(e);
		}
    }
    
    public void setExecuteParam(int index,InputStream value) {
    	try {
			this.pstmt.setBinaryStream(index, value, value.available());
		} catch (Exception e) {
			LogLogger.error(e);
		}
    }
    
    public void setExecuteParam(int index,String value) {
    	try {
			this.pstmt.setString(index, value);
		} catch (SQLException e) {
			LogLogger.error(e);
		}
    }
    
    public boolean ExecuteUpdate() {
    	boolean rtn = false;
    	
    	try {
			this.pstmt.executeUpdate();
			
			rtn = true;
		} catch (SQLException e) {
			LogLogger.error(e);
		}
    	return rtn;
    }
    
    public void BeginTransaction() {
    	try {
			this._conn.setAutoCommit(false);
		} catch (SQLException e) {
			LogLogger.error(e);
		}
    }
    
    public void Commit() {
    	try {
			this._conn.commit();
		} catch (SQLException e) {
			LogLogger.error(e);
		}
    }
    
    public void RollBack() {
    	try {
			this._conn.rollback();
		} catch (SQLException e) {
			LogLogger.error(e);
		}
    }
}
