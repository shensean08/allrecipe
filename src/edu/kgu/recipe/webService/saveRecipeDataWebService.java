package edu.kgu.recipe.webService;

import java.util.List;

import edu.kgu.recipe.access.IngredientInfoAccess;
import edu.kgu.recipe.access.recipeDetailAccess;
import edu.kgu.recipe.access.recipeMasterAccess;
import edu.kgu.recipe.bean.IngredientInfoBean;
import edu.kgu.recipe.bean.ingredientFormBean;
import edu.kgu.recipe.bean.receipeFormBean;
import edu.kgu.recipe.bean.recipeDetailBean;
import edu.kgu.recipe.bean.recipeMasterBean;

public class saveRecipeDataWebService {
	private allrecipeWebService getDataService = new allrecipeWebService();
	
	private recipeDetailAccess detailAccess = new recipeDetailAccess();
	private recipeMasterAccess masterAccess = new recipeMasterAccess();
	
	
	public void execute(String link,String country) {
		receipeFormBean recipeMain = new receipeFormBean();
		recipeMain.setCountry(country);
		recipeMain.setLink(link);
		recipeMain.setRecipeName(this.getRecipeName(link));
		List<ingredientFormBean> recipeData = getDataService.getData(link);
		saveRecipeData(recipeMain,recipeData);
	}
	
	public void saveRecipeData(receipeFormBean formBean,List<ingredientFormBean> ingredientFormBean) {
		boolean masterResult = true,detailResult = true;
		
		// Check dual data
		if (masterAccess.getRecipeMaster(formBean.getRecipeName().trim()).length()  <= 0) {
			// insert data
			if (ingredientFormBean.size() > 0 ) {
				
				//insert master data
				recipeMasterBean masterBean = new recipeMasterBean();
				
				masterBean.setLink(formBean.getLink().trim());
				masterBean.setCountry(formBean.getCountry().trim());
				masterBean.setRecipeName(formBean.getRecipeName().trim());
				masterResult = masterAccess.insertRecipeMaster(masterBean);
				
				// insert detail data
				if (masterResult) {
					for (int i = 0; i < ingredientFormBean.size(); i++) {
						recipeDetailBean detailBean = new recipeDetailBean();
						
						ingredientFormBean inBean = ingredientFormBean.get(i);	
						// insert detail
						detailBean.setRecipeName(masterBean.getRecipeName().trim());
						detailBean.setIngredientName(inBean.getIngredientnameIn().trim());
						detailBean.setIngredientAmount(inBean.getIngredientamountIn().trim());
						// don't insert null ingredientname
						if (detailBean.getIngredientName().length() > 0) {
							//check detail dual
							if (detailAccess.getRecipeDetailByIngredientName(detailBean).size() == 0) {
								detailResult = detailAccess.insertRecipeDetail(detailBean);
								if (!detailResult) {
									break;
								}
							}
						}
					}
				}
			}
		}
		
		
		if (masterResult && detailResult) {
			masterAccess.udpateRecipeMaster(formBean.getRecipeName().trim());
			detailAccess.udpateRecipeDetail(formBean.getRecipeName().trim());
		}
	}
	
	private String getRecipeName(String link) {
		String rtn;
		rtn = link.replaceAll("http://allrecipes.com/recipe/", "");
		rtn = rtn.replace("/detail.aspx?scale=1&ismetric=1", "");
		return rtn;
	}
}