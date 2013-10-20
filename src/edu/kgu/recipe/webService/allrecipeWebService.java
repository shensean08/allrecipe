package edu.kgu.recipe.webService;

import java.util.ArrayList;
import java.util.List;

import edu.kgu.log.LogLogger;
import edu.kgu.recipe.DataMining.*;
import edu.kgu.recipe.access.*;
import edu.kgu.recipe.bean.*;
import edu.kgu.recipe.define.ConstWeight;
import edu.kgu.util.*;

public class allrecipeWebService {
	
	private MiningBase mining;

	public List<ingredientFormBean> getData(String link) {
		List<ingredientFormBean> rtn = new ArrayList<ingredientFormBean>();
		
		mining = new AllRecipesMining();
		
		mining.process(link);
		
		ArrayList<RecipeBean> beanLst = mining.getResults();
		
		LogLogger.info(link);
		
		for (int i = 0; i < beanLst.size(); i++) {
			RecipeBean bean = beanLst.get(i);
			// ingredient name
			String ingredientname = bean.getIngredientname();
//			ingredientname = this.getIngredientname(ingredientname);
			ingredientname = this.getUsdaName(ingredientname.toLowerCase());
			
			// ingredient amount
			String ingredientamount = bean.getIngredientamount();
			ingredientamount = this.getIngredientamount(ingredientamount);
			
			
			ingredientFormBean formBean = new ingredientFormBean();
			formBean.setIngredientname(bean.getIngredientname());
			formBean.setIngredientnameIn(ingredientname);
			formBean.setIngredientamount(bean.getIngredientamount());
			formBean.setIngredientamountIn(ingredientamount);
			
			rtn.add(formBean);
			
			LogLogger.info(bean.getIngredientname() + " : " + ingredientname);
			LogLogger.info(bean.getIngredientamount() + " : " + ingredientamount);		
		}
		LogLogger.info("----------------------");
		
		return rtn;
	}
	
	public String getUsdaName(String value) {
		String rtn = "";
		// initialize string
		String ingredientname = StringProcess.EraseInterpunction(value," ");
		String[] ingredientnamelst = ingredientname.split("\\s+");
		
		// database access
		fooddescAccess access = new fooddescAccess();
		NoiseWordAccess noisewordaccess = new NoiseWordAccess();
		
		// filter noise words 
		for (int i = 0; i < ingredientnamelst.length; i++) {
			ArrayList<String> noiseResultLst = access.getfooddesclike(ingredientnamelst[i]);
			
			boolean isInNoiseWord = noisewordaccess.isNoiseWord(ingredientnamelst[i]);
			// filter verb
			String inged = StringProcess.FindStringWithRegular(ingredientnamelst[i], "(ed)+$");
			if (noiseResultLst.size() == 0 || inged.length() > 0 || isInNoiseWord) {
				ingredientnamelst = StringProcess.DelStringListItem(ingredientnamelst,i);
				i--;
			}
		}
			
		// get main word
		for (int i = 0;i < ingredientnamelst.length; i++) {
			String mainResult = "";
			boolean headWord = false;
			ArrayList<String> mainResultLst = access.getfooddesclike(ingredientnamelst[i] + ",");
			for (int j = 0; j <  mainResultLst.size(); j++) {
				mainResult = mainResultLst.get(j).toLowerCase();
				int mainWordLocation = mainResult.indexOf(ingredientnamelst[i] + ",");
				if (mainWordLocation == 0) {
					headWord = true;
					break;
				} else {
					String tmpHeadWord = mainResult.substring(0, mainWordLocation);
					if (!tmpHeadWord.contains(" ")) {
						headWord = true;
						break;
					}
				}
			}
			if (mainResultLst.size() > 0 && headWord) {
				ingredientnamelst[i] = ingredientnamelst[i] + ",";
				break;
			}
		}
		
		// get ingredient name from usda data
		ArrayList<String> result = new ArrayList<String>();
		if (ingredientnamelst.length > 0) {
			result = access.getfooddesc(ingredientnamelst);
		}
		
		// if havn't data  search again
		if (result.size() == 0) {
			int recordCnt = 9999;
			String[] ingredientnamelstagain = StringProcess.ArrayCopy(ingredientnamelst);
			
			for (int delListCnt = 1; delListCnt < ingredientnamelst.length; delListCnt++) {
				for (int i = 0; i < ingredientnamelst.length; i++) {
					String[] tmpingredientnamelst = StringProcess.ArrayCopy(ingredientnamelst);
					
					for (int j = 0; j < delListCnt; j ++) {
						int index = i + j;

						if (index >= tmpingredientnamelst.length) {
							index = index - tmpingredientnamelst.length;
						}
				
						if (tmpingredientnamelst[index].indexOf(",") < 0) {
							tmpingredientnamelst[index] = "";
						}
					}
					for (int k = 0; k < tmpingredientnamelst.length; k++) {
						if (tmpingredientnamelst[k].length() == 0) {
							tmpingredientnamelst = StringProcess.DelStringListItem(tmpingredientnamelst, k);
							k--;
						}
					}
					
					if (tmpingredientnamelst.length > 0) {
						result = access.getfooddesc(tmpingredientnamelst);
					}
					
					if (result.size() > 0) {
						if (result.size() < recordCnt) {
							recordCnt = result.size();
							ingredientnamelstagain = StringProcess.ArrayCopy(tmpingredientnamelst);
						}
					}
				}
				if (result.size() > 0) {
					break;
				}
			}
			
			if (ingredientnamelstagain.length > 0) {
				result = access.getfooddesc(ingredientnamelstagain);
			}
		}
		
		int resultIndex = 0;
		if (result.size() > 0) {
			int wordLocation = 9999;
			for (int i = 0; i < result.size(); i++) {
				String ingredientNameResult = result.get(i).toLowerCase();
				for (int j = 0; j < ingredientnamelst.length; j++) {
					String ingredientWord = ingredientnamelst[j];
					int tmpLocation = ingredientNameResult.indexOf(ingredientWord);
					if (tmpLocation < wordLocation) {
						wordLocation = tmpLocation;
						resultIndex = i;
					}
					
				}
			}
			
			rtn = result.get(resultIndex);
		}
		
		return rtn;
	}
	
	public String getIngredientname(String value) {
		String rtn = "";
		// initialize string
		String ingredientname = StringProcess.EraseInterpunction(value," ");
		String[] ingredientnamelst = ingredientname.split("\\s+");
		
		// database access
		IngredientInfoAccess ingredientinfoaccess = new IngredientInfoAccess();
		NoiseWordAccess noisewordaccess = new NoiseWordAccess();
		
		// delete noise word
		for (int i = 0; i < ingredientnamelst.length; i++) {
			if (noisewordaccess.isNoiseWord(ingredientnamelst[i])) {
				ingredientnamelst = StringProcess.DelStringListItem(ingredientnamelst,i);
			}
		}

		// entirety matching
		String oneWord = "";
		String twoWord = "";
		String threeWord = "";
		for (int i = 0;i < ingredientnamelst.length; i++) {
			if (oneWord.length() == 0 && twoWord.length() == 0 && threeWord.length() == 0) {
				oneWord = ingredientinfoaccess.getIngredientInfo(ingredientnamelst[i]);
				if (oneWord.length() > 0) {
					rtn = oneWord;
				}
			}
			
			if (i < ingredientnamelst.length - 1 && threeWord.length() == 0) {
				twoWord = ingredientinfoaccess.getIngredientInfo(ingredientnamelst[i] + " " + ingredientnamelst[i + 1]);
				if (twoWord.length() > 0) {
					rtn = twoWord;
				}
			}
			if (i < ingredientnamelst.length - 2) {
				threeWord = ingredientinfoaccess.getIngredientInfo(ingredientnamelst[i] + " " + ingredientnamelst[i + 1] + " " + ingredientnamelst[i + 2]);
				if (threeWord.length() > 0) {
					rtn = threeWord;
				}
			}
		}
		
		// like matching
		if (rtn.length() == 0) {
			for (int i = 0;i < ingredientnamelst.length; i++) {
				String likeCondition = ingredientnamelst[i].replaceAll("es*$|ies*$|s*$", "");
				ArrayList<IngredientInfoBean> onelike = ingredientinfoaccess.getIngredientInfoLike(likeCondition);
				if (onelike.size() > 0) {
					IngredientInfoBean bean = onelike.get(0);
					rtn = bean.getIngredientName();
					break;
				}
			}
		}
		
		// if have not ingredient name in db, return no noise name
//		if (rtn.length() == 0) {
//			for (int i = 0;i < ingredientnamelst.length; i++) {
//				rtn = rtn + ingredientnamelst[i] + " ";
//			}
//		}
		
		return rtn.trim();
	}
	
	public String getIngredientamount(String value) {
		String rtn  = "";
			
		String gram = WeightUnit.getIngredientamountWithType(value, ConstWeight.GRAM);
		String ml = WeightUnit.getIngredientamountWithType(value, ConstWeight.ML);
		String ounce = WeightUnit.getIngredientamountWithType(value, ConstWeight.OUNCE);
		String pound = WeightUnit.getIngredientamountWithType(value, ConstWeight.POUND);
		String fraction = WeightUnit.getIngredientamountFraction(value);
		
		if (gram.length() > 0 || ml.length() > 0) {
			if (gram.length() > 0) {
				rtn = WeightUnit.getIngredientamount(gram);
			}
			if (ml.length() > 0) {
				rtn = WeightUnit.getIngredientamount(ml);
			}
		}
		
		if (fraction.length() > 0) {
			if  (ounce.length() <=0 && pound.length() <= 0) {
				rtn = fraction;
			} else {
				// ounce
				if (ounce.length() > 0) {
					ounce = WeightUnit.getIngredientamount(ounce);
					rtn = String.format("%.2f",(Double.parseDouble(ounce) * NumberProcess.Fraction2Double(fraction) 
							* ConstWeight.OUNCE2GRAM));
				}
				// pound
				if (pound.length() > 0) {
					pound = WeightUnit.getIngredientamount(pound);
					rtn = String.format("%.2f",(Double.parseDouble(pound) * NumberProcess.Fraction2Double(fraction) 
							* ConstWeight.POUND2GRAM));
				}
			}
		}
		
		if (rtn.length() == 0) {
			if (value.trim().length() == 0) {
				rtn = "0.1";
			} else {
				rtn = value + "/";
			}
		}
		
		return rtn;
	}
}
