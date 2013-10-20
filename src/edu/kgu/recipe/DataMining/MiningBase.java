package edu.kgu.recipe.DataMining;

import java.util.ArrayList;

import org.htmlparser.Node;  
import org.htmlparser.NodeFilter;  
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;

import edu.kgu.recipe.define.ConstEnCoding;
import edu.kgu.recipe.bean.*;

public abstract class MiningBase {
	private String link;

	private String encoding = ConstEnCoding.UTF8;
	
	protected Parser parser;
	
	private NodeFilter nodeFilter;

	protected NodeList nodeList;
	
	protected Node[] nodes;

	protected ArrayList<RecipeBean> recipeBeanlst= new ArrayList<RecipeBean>();
	
	public void initialize() throws Exception {
			parser = new Parser(this.link);
			parser.setEncoding(this.encoding);
			nodeList = parser.parse(nodeFilter);
			nodes = nodeList.toNodeArray();
	}
	
	public abstract void process(String Link);
	
	public ArrayList<RecipeBean> getResults() {
		return recipeBeanlst;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public void setNodeFilter(NodeFilter nodeFilter) {
		this.nodeFilter = nodeFilter;
	}
}
