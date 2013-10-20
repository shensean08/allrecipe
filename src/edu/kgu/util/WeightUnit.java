package edu.kgu.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeightUnit {
	public static String getIngredientamount(String value) {
		String rtn = "";
		
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)*");
        Matcher matcher = pattern.matcher(value);
        
        if(matcher.find()){
        	rtn = matcher.group(0);
        }
        
		return rtn;
	}
	
	public static String getIngredientamountWithType(String value,String type) {
		String rtn = "";
		String regex = "\\d+(\\.\\d+)*\\s+";
        Pattern pattern = Pattern.compile(regex + type);
        Matcher matcher = pattern.matcher(value);
        
        if(matcher.find()){
        	rtn = matcher.group(0);
        }
        
		return rtn;
	}
	
	public static String getIngredientamountFraction(String value) {
		String rtn = "";
		
        Pattern pattern = Pattern.compile("(\\d+-)*\\d+/\\d+");
        Matcher matcher = pattern.matcher(value);
        
        if(matcher.find()) {
        	rtn = matcher.group(0);
        }
        
		return rtn;
	}
}
