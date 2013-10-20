package edu.kgu.recipe.action;

import java.util.List;

import edu.kgu.recipe.access.*;
import edu.kgu.recipe.bean.*;

public class allrecipeAction extends BaseAction {
	private recipeDetailAccess detailAccess = new recipeDetailAccess();
	private recipeMasterAccess masterAccess = new recipeMasterAccess();
	private IngredientInfoAccess ingredientinfoAccess = new IngredientInfoAccess();
	
	private receipeFormBean formBean;
	
	public receipeFormBean getFormBean() {
		return formBean;
	}

	public void setFormBean(receipeFormBean formBean) {
		this.formBean = formBean;
	}
	
	private List<ingredientFormBean> ingredientFormBean;
	
	public List<ingredientFormBean> getIngredientFormBean() {
		return ingredientFormBean;
	}

	public void setIngredientFormBean(List<ingredientFormBean> ingredientFormBean) {
		this.ingredientFormBean = ingredientFormBean;
	}
	
	public String execute() {
		String rtn = SUCCESS;
		boolean masterResult,detailResult;
		
		// check data dual
		if (masterAccess.getRecipeMaster(formBean.getRecipeName().trim()).length()  <= 0) {
			
			if (ingredientFormBean.size() > 0 ) {
				recipeMasterBean masterBean = new recipeMasterBean();
				
				masterBean.setLink(formBean.getLink().trim());
				masterBean.setCountry(formBean.getCountry().trim());
				masterBean.setRecipeName(formBean.getRecipeName().trim());
				masterResult = masterAccess.insertRecipeMaster(masterBean);
				
				if (masterResult) {
					for (int i = 0; i < ingredientFormBean.size(); i++) {
						recipeDetailBean detailBean = new recipeDetailBean();
						
						ingredientFormBean inBean = ingredientFormBean.get(i);
						
						// insert ingredient name
						if (ingredientinfoAccess.getIngredientInfo(inBean.getIngredientnameIn().trim()).length() <= 0) {
							IngredientInfoBean ingredientinfobean = new IngredientInfoBean();
							ingredientinfobean.setIngredientName(inBean.getIngredientnameIn().trim());
							ingredientinfoAccess.insertIngredientInfo(ingredientinfobean);
						}
							
						// insert detail
						detailBean.setRecipeName(masterBean.getRecipeName().trim());
						detailBean.setIngredientName(inBean.getIngredientnameIn().trim().toLowerCase());
						detailBean.setIngredientAmount(inBean.getIngredientamountIn().trim());
							
						detailResult = detailAccess.insertRecipeDetail(detailBean);
						if (!detailResult) {
							rtn = INPUT;
							this.messagebean.setErrorMsg("DETAIL INSERT ERROR");
							break;
						}
					}
				} else {
					this.messagebean.setErrorMsg("MASTER INSERT ERROR");
					rtn = INPUT;
				}
			}
		} else {
			this.messagebean.setErrorMsg("dual data");
		}
		
		return rtn;
	}
}
