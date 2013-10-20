package edu.kgu.recipe.access;

import java.util.ArrayList;
import java.sql.ResultSet;
import edu.kgu.recipe.bean.IngredientInfoBean;
import edu.kgu.log.LogLogger;

public class IngredientInfoAccess extends BaseAccess {
	
	public boolean insertIngredientInfo(IngredientInfoBean condition) {
		boolean rtn = false;
		
		dbConn = conn.getDBConnect();
		dbConn.beginTransaction();
		
		StringBuffer query = new StringBuffer();
		query.append(" INSERT INTO ingredientInfo ");
		query.append("   	 (ingredientName ");
		query.append("  	 ,ingredientWeight ");
		query.append("       ,chkflg)");
		query.append(" VALUES('" + condition.getIngredientName() + "'");
		query.append("       ,0 ");
		query.append("       ,'0') ");
		try {
			rtn = dbConn.Execute(query.toString());		
			dbConn.Commit();
			rtn = true;
		} catch (Exception e) {
			dbConn.RollBack();
			LogLogger.error(e);
		} finally {
			dbConn.Close();
		}
		
		return rtn;
	}
	
	public String getIngredientInfo(String Condition) {
		String rtn = "";
		ResultSet result;
		
		dbConn = conn.getDBConnect();
		StringBuffer query = new StringBuffer();
		query.append(" SELECT ingredientName ");
		query.append("       ,ingredientWeight ");
		query.append("   FROM ingredientInfo ");
		query.append("  WHERE ingredientName = '" + Condition + "'");
		query.append("    AND chkflg = '1' ");
		
		try {
			result = dbConn.ExecuteQuery(query.toString());		   

			if (result.next()) {
				rtn = result.getString("ingredientName");
			}
		} catch (Exception e) {
			LogLogger.error(e);
		} finally {
			dbConn.Close();
		}
		
		return rtn;
	}
	
	public ArrayList<IngredientInfoBean> getIngredientInfoLike(String Condition) {
		ArrayList<IngredientInfoBean> rtn = new ArrayList<IngredientInfoBean>();
		ResultSet result;
		
		dbConn = conn.getDBConnect();
		StringBuffer query = new StringBuffer();
		query.append(" SELECT ingredientName ");
		query.append("       ,ingredientWeight ");
		query.append("   FROM ingredientInfo ");
		query.append("  WHERE ingredientName LIKE '%" + Condition + "%'");
		query.append("    AND chkflg = '1' ");
		
		try {
			result = dbConn.ExecuteQuery(query.toString());		   

			while (result.next()) {
				IngredientInfoBean bean = new IngredientInfoBean();
				bean.setIngredientName(result.getString("ingredientName"));
				bean.setIngredientWeight(result.getString("ingredientWeight"));
				rtn.add(bean);
			}
		} catch (Exception e) {
			LogLogger.error(e);
		} finally {
			dbConn.Close();
		}
		
		return rtn;
	}
}