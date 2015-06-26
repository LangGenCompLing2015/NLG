package templates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InstructionTemplateFinder {

	private List<Template> templates;
	
	public InstructionTemplateFinder(){
		templates = new ArrayList<Template>();
	}

	public List<Template> findTemplate(Map<String,Object> wordFeatures) {
		List<Template> temp = new ArrayList<Template>();
		for (Template t : templates) {
			
		}
		return temp;
	}
	
	public void addTemplate(Template t){
		templates.add(t);
	}
}
