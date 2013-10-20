package edu.kgu.recipe.access;

import edu.kgu.sql.DBConnect;

public abstract class BaseAccess {
	
	protected ConnectAccess conn = new ConnectAccess();
	
	protected DBConnect dbConn;
}
