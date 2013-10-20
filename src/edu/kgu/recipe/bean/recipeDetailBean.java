package edu.kgu.recipe.bean;

public class recipeDetailBean {
	private String recipeName = "";
	private String ingredientName = "";
	private String ingredientAmount = "";
	private String chkflg = "";
	
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public String getIngredientName() {
		return ingredientName;
	}
	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}
	public String getIngredientAmount() {
		return ingredientAmount;
	}
	public void setIngredientAmount(String ingredientAmount) {
		this.ingredientAmount = ingredientAmount;
	}
	public String getChkflg() {
		return chkflg;
	}
	public void setChkflg(String chkflg) {
		this.chkflg = chkflg;
	}
}
