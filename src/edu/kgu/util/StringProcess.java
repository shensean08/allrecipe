package edu.kgu.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringProcess {
	public static String EraseHtmlCharacter(String value) {
		String rtn = value;
		rtn = rtn.replaceAll("&nbsp;","");
		rtn = rtn.replaceAll("<br/>","");
		return rtn;
	}
	
	public static String EraseInterpunction(String value,String replace) {
		String rtn = value.replaceAll("(\\.)+|(\\,)|(\\-)+|(\\?)+|(\\%)+|(\\')+|(\\@)+", replace);
		return rtn;
	}
	
	public static String[] ArrayCopy(String[] src) {
		String[] rtn = new String[src.length];
		System.arraycopy(src, 0, rtn, 0, src.length);
		return rtn;
	}
	
	public static String[] DelStringListItem(String[] value,int index) {
		String[] rtn = new String[value.length - 1];
		System.arraycopy(value, 0, rtn, 0, index);
		System.arraycopy(value, index + 1, rtn, index, value.length - index - 1);
		return rtn;
	}
	
    public static String FindStringWithRegular(String value,String regularExpress) {
        String rtn = "";
        Pattern pattern = Pattern.compile(regularExpress);
        Matcher matcher = pattern.matcher(value); 
        
        if(matcher.find()) {
            rtn = matcher.group(0);
        }
        
        return rtn;
    }
}
