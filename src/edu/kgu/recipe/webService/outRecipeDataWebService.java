package edu.kgu.recipe.webService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import edu.kgu.log.LogLogger;
import edu.kgu.recipe.access.*;
import edu.kgu.recipe.bean.*;
import edu.kgu.util.StringProcess;
import java.lang.Math;

public class outRecipeDataWebService {
	private fooddescAccess fooddescaccess = new fooddescAccess();
	private recipeMasterAccess masteraccess = new recipeMasterAccess();
	private recipeDetailAccess detailaccess = new recipeDetailAccess();
	
	public ArrayList<String> outRecipeEntropy(ArrayList<String> countryLst) {
		LogLogger.debug("------");
		ArrayList<String> context = new ArrayList<String>();
		ArrayList<String> columns = fooddescaccess.getfooddescAll();
		HashMap<String, Double> sumGroup = new HashMap<String, Double>();
		
		String outString = "recipeName,country,";
		
		// delete sum zero columns
		for (int i = 0; i < columns.size(); i++) {
			if (detailaccess.getIngredientAmoutSumByCountry(columns.get(i),new ArrayList<String>()) == 0) {
				columns.remove(i);
				i--;
			} else {
				outString = outString + StringProcess.EraseInterpunction(columns.get(i), "") + ",";
			}
		}
		context.add(outString.substring(0,outString.length() - 1));
		
		// create sum map
		for (int i = 0; i < columns.size(); i++) {
			double sumValue = detailaccess.getIngredientAmoutSumByCountry(columns.get(i),countryLst);
			sumGroup.put(columns.get(i).trim(), sumValue);
			LogLogger.debug(columns.get(i).trim() + ":" + sumValue);
		}
		
		// get master data
		ArrayList<recipeMasterBean> masterLst = masteraccess.getRecipeMasterByCountry(countryLst);
		for (int i = 0; i < masterLst.size(); i++) {
			recipeMasterBean masterbean = masterLst.get(i);
			
			outString = masterbean.getRecipeName() + ","
					  + masterbean.getCountry() + ",";
			
			// get detail data;
			ArrayList<recipeDetailBean> detailLst = detailaccess.getRecipeDetail(masterbean.getRecipeName());
			for (int j = 0; j < columns.size(); j++) {
				String columnValue = "0,";
				
				for(int m = 0; m < detailLst.size(); m++) {
					recipeDetailBean detailbean = detailLst.get(m);
					if (detailbean.getIngredientName().equals(columns.get(j).trim())) {
						double sumXij = sumGroup.get(columns.get(j));
						double Xij = Double.parseDouble(detailbean.getIngredientAmount());
						String sIngredientAmount = "0";
						if (sumXij > 0 && Xij > 0) {
							double Pij = Xij/sumXij;
							double lnPij = Math.log(Pij);
							sIngredientAmount = String.valueOf(Pij * lnPij);
							LogLogger.debug("sum:" + sumXij + " Xij:" + Xij + " Pij:" + Pij + " lnPij:" + lnPij);
						}
						columnValue = sIngredientAmount + ",";
						break;
					}
				}
				
				outString = outString + columnValue;
			}
			
			context.add(outString.substring(0,outString.length() - 1));
		}
		return context;
	}
	
	public ArrayList<String> outRecipeData() {
		ArrayList<String> context = new ArrayList<String>();
		
		String outString = "recipeName,link,country,";
		ArrayList<String> columns = fooddescaccess.getfooddescAll();
		
		// create columns
		for (int i = 0 ; i < columns.size(); i++) {
			outString = outString + StringProcess.EraseInterpunction(columns.get(i), "") + ",";
		}
		context.add(outString.substring(0,outString.length() - 1));
		
		// get master data
		ArrayList<recipeMasterBean> masterLst = masteraccess.getRecipeMasterAll();
		for (int i = 0; i < masterLst.size(); i++) {
			recipeMasterBean masterbean = masterLst.get(i);
			
			outString = masterbean.getRecipeName() + ","
					  + masterbean.getLink() + ","
					  + masterbean.getCountry() + ",";
			
			// get detail data;
			ArrayList<recipeDetailBean> detailLst = detailaccess.getRecipeDetail(masterbean.getRecipeName());
			
			for (int j = 0; j < columns.size(); j++) {
				String columnValue = "0,";
				
				for(int m = 0; m < detailLst.size(); m++) {
					recipeDetailBean detailbean = detailLst.get(m);
					if (detailbean.getIngredientName().equals(columns.get(j))) {
						columnValue = detailbean.getIngredientAmount() + ",";
						break;
					}
				}
				
				outString = outString + columnValue;
			}
			context.add(outString.substring(0,outString.length() - 1));
		}
		
		return context;
	}
}
