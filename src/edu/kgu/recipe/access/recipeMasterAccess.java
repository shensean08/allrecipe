package edu.kgu.recipe.access;

import java.sql.ResultSet;
import java.util.ArrayList;

import edu.kgu.log.LogLogger;
import edu.kgu.recipe.bean.*;

public class recipeMasterAccess extends BaseAccess {
	public String getRecipeMaster(String condition) {
		String rtn = "";
		ResultSet result;
		
		dbConn = conn.getDBConnect();
		StringBuffer query = new StringBuffer();
		query.append(" SELECT recipeName ");
		query.append("   FROM recipeMaster ");
		query.append("  WHERE recipeName = '" + condition + "'");
		
		try {
			result = dbConn.ExecuteQuery(query.toString());		   

			if (result.next()) {
				rtn = result.getString("recipeName");
			}
		} catch (Exception e) {
			LogLogger.error(query);
		} finally {
			dbConn.Close();
		}
		
		return rtn;
	}
	
	public boolean insertRecipeMaster(recipeMasterBean condition) {
		boolean rtn = false;
		
		dbConn = conn.getDBConnect();
		dbConn.beginTransaction();
		
		StringBuffer query = new StringBuffer();
		query.append(" INSERT INTO recipeMaster ");
		query.append("   	 (recipeName ");
		query.append("  	 ,link ");
		query.append("  	 ,country ");
		query.append("       ,chkflg)");
		query.append(" VALUES('" + condition.getRecipeName() + "'");
		query.append("       ,'" + condition.getLink() + "' ");
		query.append("       ,'" + condition.getCountry() + "' ");
		query.append("       ,'0') ");
		try {
			rtn = dbConn.Execute(query.toString());		   
			dbConn.Commit();
			rtn = true;
		} catch (Exception e) {
			dbConn.RollBack();
			LogLogger.error(query);
		} finally {
			dbConn.Close();
		}
		
		return rtn;
	}
	
	public void udpateRecipeMaster(String recipeName) {
		dbConn = conn.getDBConnect();
		dbConn.beginTransaction();
		
		StringBuffer query = new StringBuffer();
		query.append(" UPDATE recipeMaster ");
		query.append("   	 set chkflg = '1' ");
		query.append(" WHERE recipeName = '" + recipeName + "'");
		try {
			dbConn.Execute(query.toString());		   
			dbConn.Commit();
		} catch (Exception e) {
			dbConn.RollBack();
			LogLogger.error(query);
		} finally {
			dbConn.Close();
		}
	}
	
	public ArrayList<recipeMasterBean> getRecipeMasterAll() {
		ArrayList<recipeMasterBean> rtn = new ArrayList<recipeMasterBean>();
		ResultSet result;
		
		dbConn = conn.getDBConnect();
		StringBuffer query = new StringBuffer();
		query.append(" SELECT recipeName ");
		query.append("       ,link ");
		query.append("       ,country ");
		query.append("   FROM recipeMaster where country = 'Chinese' ");

		try {
			result = dbConn.ExecuteQuery(query.toString());		   

			while (result.next()) {
				recipeMasterBean bean = new recipeMasterBean();
				bean.setRecipeName(result.getString("recipeName"));
				bean.setLink(result.getString("link"));
				bean.setCountry(result.getString("country"));
				rtn.add(bean);
			}
		} catch (Exception e) {
			LogLogger.error(query);
		} finally {
			dbConn.Close();
		}
		
		return rtn;
	}
	
	public ArrayList<recipeMasterBean> getRecipeMasterByCountry(ArrayList<String> countryLst) {
		ArrayList<recipeMasterBean> rtn = new ArrayList<recipeMasterBean>();
		ResultSet result;
		
		dbConn = conn.getDBConnect();
		StringBuffer query = new StringBuffer();
		query.append(" SELECT recipeName ");
		query.append("       ,link ");
		query.append("       ,country ");
		query.append("   FROM recipeMaster");
		if (countryLst.size() > 0) {
			query.append("  where country IN (");
			for(int i = 0; i < countryLst.size(); i++) {
				query.append("'" + countryLst.get(i).trim() + "',");
			}
			query.delete(query.length() - 1, query.length());
			query.append(")");
		}
		
		try {
			result = dbConn.ExecuteQuery(query.toString());		   

			while (result.next()) {
				recipeMasterBean bean = new recipeMasterBean();
				bean.setRecipeName(result.getString("recipeName"));
				bean.setLink(result.getString("link"));
				bean.setCountry(result.getString("country"));
				rtn.add(bean);
			}
		} catch (Exception e) {
			LogLogger.error(query);
		} finally {
			dbConn.Close();
		}
		
		return rtn;
	}
}
