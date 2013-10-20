package edu.kgu.util;

public class NumberProcess {
	public static double Fraction2Double(String value) {
		double rtn;
		
		String integer = value.replaceAll("(-)*(\\d+/\\d+$)*","");
		
		if (integer.length() <= 0) {
			integer = "0";
		}
		
		value = value.replaceAll("^\\d+-", "");
		String numerator = value.replaceAll("/\\d+$", "");
		String denominator = value.replaceAll("^\\d+/", "");

		rtn = (double)(Integer.parseInt(integer) 
				+ (double)Integer.parseInt(numerator) / (double)Integer.parseInt(denominator));
		return rtn;
	}
}
