import org.htmlparser.Node;  
import org.htmlparser.NodeFilter;  
import org.htmlparser.Parser;
import org.htmlparser.filters.*;  
import org.htmlparser.util.NodeList;

public class parse {
	public static void main(String[] args){
          
        try{

        	
            Parser parser = new Parser("http://allrecipes.com/recipe/grandmas-suet-pudding/detail.aspx?scale=1&ismetric=1");  
            parser.setEncoding("UTF-8");
            NodeFilter ingredientamountFilter = new HasAttributeFilter("class","ingredient-amount");
            NodeFilter ingredientnameFilter = new HasAttributeFilter("class","ingredient-name");
            NodeFilter chiefFilter = new HasAttributeFilter("class","fl-ing");
            NodeList chiefList = parser.parse(chiefFilter);
            
            Node[] chiefNodes = chiefList.toNodeArray();
            for (int i = 0; i < chiefNodes.length; i++) {
            	String ingredientname = "";
            	String ingredientamount = "";
            	
            	NodeList ingredientsNodelist = chiefNodes[i].getChildren();
            	NodeList ingredientnameNode = ingredientsNodelist.extractAllNodesThatMatch(ingredientnameFilter); 
            	NodeList ingredientamountNode = ingredientsNodelist.extractAllNodesThatMatch(ingredientamountFilter);
            	
            	if (ingredientnameNode.toNodeArray().length > 0) {
            		
            		ingredientname = ingredientnameNode.elementAt(0).toPlainTextString();
            	}
            	
            	if (ingredientamountNode.toNodeArray().length > 0) {
            		ingredientamount = ingredientamountNode.elementAt(0).toPlainTextString();
            	}
            	
            	System.out.print("ingredientname:" + ingredientname);
            	System.out.println(" ingredientamount:" + ingredientamount);
            }

        }catch(Exception e){  
            e.printStackTrace();  
        }
    }  
}
