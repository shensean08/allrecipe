package edu.kgu.recipe.access;

import java.sql.ResultSet;
import java.util.ArrayList;

import edu.kgu.log.LogLogger;
import edu.kgu.recipe.bean.*;
import edu.kgu.util.NumberProcess;

public class recipeDetailAccess extends BaseAccess {
	public boolean insertRecipeDetail(recipeDetailBean condition) {
		boolean rtn = false;
		
		dbConn = conn.getDBConnect();
		dbConn.beginTransaction();
		
		StringBuffer query = new StringBuffer();
		query.append(" INSERT INTO recipeDetail ");
		query.append("   	 (recipeName ");
		query.append("  	 ,ingredientName ");
		query.append("  	 ,ingredientAmount ");
		query.append("       ,chkflg)");
		query.append(" VALUES('" + condition.getRecipeName() + "'");
		query.append("       ,'" + condition.getIngredientName() + "' ");
		query.append("       ,'" + condition.getIngredientAmount() + "' ");
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
	
	public void udpateRecipeDetail(String recipeName) {
		dbConn = conn.getDBConnect();
		dbConn.beginTransaction();
		
		StringBuffer query = new StringBuffer();
		query.append(" UPDATE recipeDetail ");
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
	
	public ArrayList<recipeDetailBean> getRecipeDetail(String recipeName) {
		ArrayList<recipeDetailBean> rtn = new ArrayList<recipeDetailBean>();
		ResultSet result;
		
		dbConn = conn.getDBConnect();
		StringBuffer query = new StringBuffer();
		query.append(" SELECT recipeName ");
		query.append("       ,ingredientName ");
		query.append("       ,ingredientAmount ");
		query.append("   FROM recipeDetail ");
		query.append("  WHERE recipeName = '" + recipeName + "'");

		try {
			result = dbConn.ExecuteQuery(query.toString());		   

			while (result.next()) {
				recipeDetailBean bean = new recipeDetailBean();
				bean.setRecipeName(result.getString("recipeName"));
				bean.setIngredientName(result.getString("ingredientName"));
				bean.setIngredientAmount(result.getString("ingredientAmount"));
				rtn.add(bean);
			}
		} catch (Exception e) {
			LogLogger.error(query);
		} finally {
			dbConn.Close();
		}
		
		return rtn;
	}
	
	public ArrayList<recipeDetailBean> getRecipeDetailByIngredientName(recipeDetailBean value) {
		ArrayList<recipeDetailBean> rtn = new ArrayList<recipeDetailBean>();
		ResultSet result;
		
		dbConn = conn.getDBConnect();
		StringBuffer query = new StringBuffer();
		query.append(" SELECT recipeName ");
		query.append("       ,ingredientName ");
		query.append("       ,ingredientAmount ");
		query.append("   FROM recipeDetail ");
		query.append("  WHERE recipeName = '" + value.getRecipeName() + "'");
		query.append("    AND ingredientName = '" + value.getIngredientName() + "'");
		
		try {
			result = dbConn.ExecuteQuery(query.toString());		   

			if (result.next()) {
				recipeDetailBean bean = new recipeDetailBean();
				bean.setRecipeName(result.getString("recipeName"));
				bean.setIngredientName(result.getString("ingredientName"));
				bean.setIngredientAmount(result.getString("ingredientAmount"));
				rtn.add(bean);
			}
		} catch (Exception e) {
			LogLogger.error(query);
		} finally {
			dbConn.Close();
		}
		
		return rtn;
	}
	
	public double getIngredientAmoutSumByCountry(String ingredientname,ArrayList<String> countryLst) {
		double rtn = 0;
		ResultSet result;
		
		dbConn = conn.getDBConnect();
		StringBuffer query = new StringBuffer();
		query.append(" SELECT  ");
		query.append("       IFNULL(SUM(ingredientAmount),0) ingredientAmount ");
		query.append("   FROM recipeDetail ");
		query.append("  WHERE ingredientName = '" + ingredientname + "'");
		if (countryLst.size() > 0) {
			query.append(" AND recipeName IN (");
			query.append(" SELECT recipeName ");
			query.append("   FROM recipeMaster ");
			query.append("  WHERE country IN (");
			for (int i = 0; i < countryLst.size(); i++) {
				query.append("'" + countryLst.get(i).trim() + "',");
			}
			query.delete(query.length() - 1, query.length());
			query.append("))");
		}
		
		try {
			result = dbConn.ExecuteQuery(query.toString());		   

			if (result.next()) {
				rtn = Double.parseDouble(result.getString("ingredientAmount"));
			}
		} catch (Exception e) {
			LogLogger.error(query);
		} finally {
			dbConn.Close();
		}
		
		return rtn;
	}
}
