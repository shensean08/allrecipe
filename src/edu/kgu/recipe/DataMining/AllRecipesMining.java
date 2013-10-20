package edu.kgu.recipe.DataMining;

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;

import edu.kgu.recipe.bean.RecipeBean;
import edu.kgu.util.StringProcess;

public class AllRecipesMining extends MiningBase{

	// allrecipes filter (ingredient information)
    private NodeFilter nodeFilter = new HasAttributeFilter("class","fl-ing");
    
	
	public void process(String link) {
		try {
		    // ingredient-name filter
		    NodeFilter ingredientnameFilter = new HasAttributeFilter("class","ingredient-name");
		    
			// ingredient-amount filter
		    NodeFilter ingredientamountFilter = new HasAttributeFilter("class","ingredient-amount");
		    
		    // allrecipes link
		    this.setLink(link);
		    this.setNodeFilter(nodeFilter);
		    
		    // initialize
		    this.initialize();
			
		    // analyse the html node of ingredient
			for (int i = 0; i < this.nodes.length; i++) {
	        	String ingredientname = "";
	        	String ingredientamount = "";
	        	
	        	NodeList ingredientsNodelist = nodes[i].getChildren();
	        	NodeList ingredientnameNode = ingredientsNodelist.extractAllNodesThatMatch(ingredientnameFilter); 
	        	NodeList ingredientamountNode = ingredientsNodelist.extractAllNodesThatMatch(ingredientamountFilter);
	        	
	        	// get ingredient name from html node
	        	if (ingredientnameNode.toNodeArray().length > 0) {
	        		ingredientname = ingredientnameNode.elementAt(0).toPlainTextString();
	        		ingredientname = StringProcess.EraseHtmlCharacter(ingredientname);
	        	}
	        	
	        	// get ingredient amount from html node
	        	if (ingredientamountNode.toNodeArray().length > 0) {
	        		ingredientamount = ingredientamountNode.elementAt(0).toPlainTextString();
	        		ingredientamount = StringProcess.EraseHtmlCharacter(ingredientamount);
	        	}
	        	
	        	if (ingredientname.trim().length() > 0) {
		        	// set Result
		        	RecipeBean bean = new RecipeBean();
		        	
		        	bean.setIngredientname(ingredientname);
		        	bean.setIngredientamount(ingredientamount);
		        	
		        	this.recipeBeanlst.add(bean);
	        	}
	        }
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
