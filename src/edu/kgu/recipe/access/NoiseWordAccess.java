package edu.kgu.recipe.access;

import java.sql.ResultSet;
import edu.kgu.log.LogLogger;

public class NoiseWordAccess extends BaseAccess {
	public boolean isNoiseWord(String Condition) {
		boolean rtn = false;
		ResultSet result;
		
		dbConn = conn.getDBConnect();
		StringBuffer query = new StringBuffer();
		query.append(" SELECT noiseWord ");
		query.append("   FROM NoiseWord ");
		query.append("  WHERE TRIM(noiseWord) = '" + Condition.trim() + "'");
		
		try {
			result = dbConn.ExecuteQuery(query.toString());		   

			if(result.next()) {
				rtn = true;
			}
		} catch (Exception e) {
			LogLogger.error(e);
		} finally {
			dbConn.Close();
		}
		
		return rtn;
	}
}
