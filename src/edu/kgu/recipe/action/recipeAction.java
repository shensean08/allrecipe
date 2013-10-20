package edu.kgu.recipe.action;

import java.util.List;

import edu.kgu.recipe.bean.*;
import edu.kgu.recipe.webService.allrecipeWebService;

public class recipeAction extends BaseAction {
	
	private allrecipeWebService webservice = new allrecipeWebService();
	
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
	
//	public List<RecipeBean> listBean;
//	
//	public List<RecipeBean> getListBean() {
//		return listBean;
//	}
//
//	public void setListBean(List<RecipeBean> listBean) {
//		this.listBean = listBean;
//	}
	
	public String execute() {
		String rtn;
		ingredientFormBean = webservice.getData(formBean.getLink());
		
		if (ingredientFormBean.size() > 0) {
			rtn = SUCCESS;
		} else {
			rtn = INPUT;
		}
//		if (listBean == null) {
//			listBean = new ArrayList<RecipeBean>();
//			for(int i = 0;i<5;i++) {
//				RecipeBean bean = new RecipeBean(); 
//				bean.setIngredientamount(String.valueOf(i));
//				bean.setIngredientname(String.valueOf(i));
//				listBean.add(bean);
//			}
//		}
		return rtn;
	}


}
