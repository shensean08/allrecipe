import java.util.ArrayList;

import edu.kgu.recipe.bean.*;
import edu.kgu.recipe.webService.outRecipeDataWebService;
import edu.kgu.recipe.webService.saveRecipeDataWebService;
import edu.kgu.util.file.fileIO;


public class experimentData {
	private outRecipeDataWebService outWS = new outRecipeDataWebService();
	private saveRecipeDataWebService srWS = new saveRecipeDataWebService();
	
	private ArrayList<receipeFormBean> dataBean = new ArrayList<receipeFormBean>();
	
	public void saveRecipeData() {
		for (int i = 0;i < dataBean.size(); i++) {
			receipeFormBean bean = dataBean.get(i);
			srWS.execute(bean.getLink(), bean.getCountry());
		}
	}
	
	public void outCSVFile(String file) {
		ArrayList<String> context = outWS.outRecipeData();
		fileIO.fileWrite(file, context, false);
	}
	
	public void outEntropyFile(String file,ArrayList<String> countryLst) {
		ArrayList<String> context = outWS.outRecipeEntropy(countryLst);
		fileIO.fileWrite(file, context, false);
	}
	
	public void readRecipeLinkFile(String file) {
		ArrayList<String> filecontext = fileIO.fileRead(file);
		
		for (int i = 0; i < filecontext.size(); i++) {
			String line = filecontext.get(i);
			receipeFormBean bean = new receipeFormBean();
			bean.setCountry(line.substring(0, line.indexOf(",")));
			bean.setLink(line.substring(line.indexOf(",") + 1) + "?scale=1&ismetric=1");
			dataBean.add(bean);
		}
	}
}
