package edu.kgu.recipe.access;

import java.sql.ResultSet;
import java.util.ArrayList;

import edu.kgu.log.LogLogger;
import edu.kgu.recipe.bean.IngredientInfoBean;

public class fooddescAccess  extends BaseAccess {
	public ArrayList<String> getfooddesc(String[] valueLst) {
		ArrayList<String> rtn = new ArrayList<String>();
		ResultSet result;
		
		dbConn = conn.getDBConnect();
		StringBuffer query = new StringBuffer();
		query.append(" SELECT Long_Desc ");
		query.append("   FROM fooddesc ");
		query.append("  WHERE Long_Desc LIKE '%" + valueLst[0] + "%'");
		for (int i = 1; i < valueLst.length; i++) {
			query.append("  AND Long_Desc LIKE '%" + valueLst[i] + "%'");
		}
		query.append("    AND (NOT FdGrp_Cd IN ('0300','0800'))");
		
//		query.append(" ORDER BY LENGTH(Long_Desc) ");
		query.append(" ORDER BY NDB_No ");
		
		try {
			result = dbConn.ExecuteQuery(query.toString());		   

			while (result.next()) {
				rtn.add(result.getString("Long_Desc"));
			}
		} catch (Exception e) {
			LogLogger.error(e);
		} finally {
			dbConn.Close();
		}
		
		return rtn;
	}
	
	public ArrayList<String> getfooddesclike(String value) {
		ArrayList<String> rtn = new ArrayList<String>();
		ResultSet result;
		
		dbConn = conn.getDBConnect();
		StringBuffer query = new StringBuffer();
		query.append(" SELECT Long_Desc ");
		query.append("   FROM fooddesc ");
		query.append("  WHERE Long_Desc LIKE '%" + value + "%'");
		query.append("    AND (NOT FdGrp_Cd IN ('0300','0800'))");
//		query.append(" ORDER BY LENGTH(Long_Desc) ");
		query.append(" ORDER BY NDB_No ");
		
		try {
			result = dbConn.ExecuteQuery(query.toString());		   

			while (result.next()) {
				rtn.add(result.getString("Long_Desc"));
			}
		} catch (Exception e) {
			LogLogger.error(e);
		} finally {
			dbConn.Close();
		}
		
		return rtn;
	}
	
	public ArrayList<String> getfooddescAll() {
		ArrayList<String> rtn = new ArrayList<String>();
		ResultSet result;
		
		dbConn = conn.getDBConnect();
		StringBuffer query = new StringBuffer();
		query.append(" SELECT Long_Desc ");
		query.append("   FROM fooddesc ");
		query.append("  WHERE NOT FdGrp_Cd IN ('0300','0800')");
		query.append(" ORDER BY NDB_No ");
		
		try {
			result = dbConn.ExecuteQuery(query.toString());		   

			while (result.next()) {
				rtn.add(result.getString("Long_Desc"));
			}
		} catch (Exception e) {
			LogLogger.error(e);
		} finally {
			dbConn.Close();
		}
		
		return rtn;
	}
}
