import java.util.ArrayList;
import java.util.List;

import edu.kgu.recipe.DataMining.*;
import edu.kgu.recipe.access.IngredientInfoAccess;
import edu.kgu.recipe.access.fooddescAccess;
import edu.kgu.recipe.action.allrecipeAction;
import edu.kgu.recipe.bean.*;
import edu.kgu.recipe.define.ConstWeight;
import edu.kgu.recipe.webService.allrecipeWebService;
import edu.kgu.recipe.webService.outRecipeDataWebService;
import edu.kgu.recipe.webService.saveRecipeDataWebService;
import edu.kgu.util.NumberProcess;
import edu.kgu.util.StringProcess;
import edu.kgu.util.WeightUnit;
import edu.kgu.util.file.fileIO;

public class test {
	public static void main(String[] args){
		outRecipeData();
	}
	

	public static void experiment() {
		experimentData experiment = new experimentData();
		experiment.readRecipeLinkFile("D:/workspace/allrecipe/datafile/data_china.csv");
		experiment.saveRecipeData();
	}
	
	public static void outRecipeData() {
		experimentData experiment = new experimentData();
		experiment.outCSVFile("D:/workspace/allrecipe/datafile/result_china.csv");
	}
	
	public static void outIslam_Entropy() {
		experimentData experiment = new experimentData();
		ArrayList<String> countryLst = new ArrayList<String>();
		countryLst.add("Iran");
		countryLst.add("Pakistan ");
		countryLst.add("Turkey  ");
		countryLst.add("Indonesia   ");
		countryLst.add("Malaysia    ");
		experiment.outEntropyFile("/Users/seanshen/Documents/Islam_entropy.csv",countryLst);
	}
	
	public static void outSouthAmericaEntropy() {
		experimentData experiment = new experimentData();
		ArrayList<String> countryLst = new ArrayList<String>();
		countryLst.add("Brazil  ");
		countryLst.add("Colombia   ");
		countryLst.add("Peru   ");
		countryLst.add("Chile    ");
		countryLst.add("Argentina     ");
		countryLst.add("Caribbean      ");
		experiment.outEntropyFile("/Users/seanshen/Documents/SouthAmerica_entropy.csv",countryLst);
	}
	
	public static void outNorthAmericaEntropy() {
		experimentData experiment = new experimentData();
		ArrayList<String> countryLst = new ArrayList<String>();
		countryLst.add("USA Regional and Ethnic  ");
		countryLst.add("Canada   ");
		experiment.outEntropyFile("/Users/seanshen/Documents/NorthAmerica_entropy.csv",countryLst);
	}
	
	public static void outSouthAsia() {
		experimentData experiment = new experimentData();
		ArrayList<String> countryLst = new ArrayList<String>();
		countryLst.add("India  ");
		countryLst.add("Thailand   ");
		experiment.outEntropyFile("/Users/seanshen/Documents/SouthAsia_entropy.csv",countryLst);
	}
	
	public static void outEastAsia() {
		experimentData experiment = new experimentData();
		ArrayList<String> countryLst = new ArrayList<String>();
		countryLst.add("Vietnam  ");
		countryLst.add("Chinese   ");
		countryLst.add("Japanese   ");
		experiment.outEntropyFile("/Users/seanshen/Documents/EastAsia_entropy.csv",countryLst);
	}
	
	public static void outEurpeo() {
		experimentData experiment = new experimentData();
		ArrayList<String> countryLst = new ArrayList<String>();
		countryLst.add("Greek   ");
		countryLst.add("Spain    ");
		countryLst.add("Italian     ");
		countryLst.add("Germany      ");
		countryLst.add("French      ");
		experiment.outEntropyFile("/Users/seanshen/Documents/Eurpeo_entropy.csv",countryLst);
	}
	
	public static void arraytest() {
		String[] ingredientnamelst = new String[5];
		ingredientnamelst[0] = "1";
		ingredientnamelst[1] = "2";
		ingredientnamelst[2] = "3";
		ingredientnamelst[3] = "4";
		ingredientnamelst[4] = "5";
		
		
		for (int delListCnt = 1; delListCnt < ingredientnamelst.length; delListCnt++) {
			for (int i = 0; i < ingredientnamelst.length; i++) {
//				if ((i + delListCnt) <= ingredientnamelst.length) {
					String[] tmpList = new String[ingredientnamelst.length];
					System.arraycopy(ingredientnamelst, 0, tmpList, 0, ingredientnamelst.length);
				
					for (int j = 0; j < delListCnt; j ++) {
						int location = i + j;
						if (location >= ingredientnamelst.length)
							location = location - ingredientnamelst.length;
						tmpList[location] = " ";
					}
					//
					for (int k = 0; k < tmpList.length; k++) {
						System.out.print(tmpList[k]);
					}
					System.out.println("");
					
					//
//				}
			}
		}
	}
	public static void usdaexecute() {
		saveRecipeDataWebService web = new saveRecipeDataWebService();
		
		web.execute("http://allrecipes.com/recipe/alis-amazing-bruschetta/detail.aspx?scale=1&ismetric=1", "Italian");
	}
	public static void filetest() {
		fileIO file = new fileIO();
		ArrayList<String> context = new ArrayList<String>();
		context.add("aaa");
		context.add("bbbb");
		file.fileWrite("/Users/seanshen/Documents/test.txt", context,true);
	}
	public static void usdatest() {
		fooddescAccess access = new fooddescAccess();
		String ingredient = "skinless boneless chicken breast halves cut into bite size pieces";
		String[] inValue = ingredient.split(" ");
	}
	
	public static void insertData() {
		receipeFormBean beanMaster = new receipeFormBean();
		beanMaster.setLink("testlink");
		beanMaster.setRecipeName("testrecipename");
		beanMaster.setCountry("testCountry");
		
		allrecipeWebService webservice = new allrecipeWebService();
		List<ingredientFormBean> beanDetail = webservice.getData("http://allrecipes.com/recipe/pantry-raid-chicken-enchilada-casserole/detail.aspx?scale=1&ismetric=1");
		
		allrecipeAction action = new allrecipeAction();
		action.setIngredientFormBean(beanDetail);
		action.setFormBean(beanMaster);
		action.execute();
	}
	
	public static void mining() {
		AllRecipesMining mining = new AllRecipesMining();
		
		mining.process("http://allrecipes.com/recipe/grandmas-suet-pudding/detail.aspx?scale=1&ismetric=1");
		
		mining.process("http://allrecipes.com/recipe/million-dollar-chinese-cabbage-salad/detail.aspx?scale=1&ismetric=1");
	}
	
	public static void access() {
		IngredientInfoAccess access = new IngredientInfoAccess();
		
		access.getIngredientInfo("dddd");
	}
	
	public static void webservice() {
		allrecipeWebService webservice = new allrecipeWebService();
		webservice.getData("http://allrecipes.com/recipe/million-dollar-chinese-cabbage-salad/detail.aspx?scale=1&ismetric=1");
	}
	
	public static void weightunit() {
		System.out.println(WeightUnit.getIngredientamount("afasdff 0.3 asdf "));	
	}
	
	public static void fraction() {
		System.out.println(NumberProcess.Fraction2Double("1-1/8"));
	}
	
	public static void eraseinterpunction(){
		System.out.println(StringProcess.EraseInterpunction("sdfasfasf.dddd.s,,dsss....ss"," "));
	}
}
