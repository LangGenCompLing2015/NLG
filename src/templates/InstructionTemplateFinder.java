package templates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import features.Feature;

public class InstructionTemplateFinder {

	private List<Template> templates;

	public InstructionTemplateFinder() {
		templates = new ArrayList<Template>();
	}

	public List<Template> findTemplate(String verbType,
			List<Map<String, Object>> wordFeatures) {
		List<Template> temp = new ArrayList<Template>();
		List<List<Feature>> featureList = toFeatureList(wordFeatures);
		for (Template t : templates) {
			if (t.matches(verbType, featureList)) {
				temp.add(t);
			}
		}
		return temp;
	}
	
	private List<List<Feature>> toFeatureList(List<Map<String, Object>> wordFeatures){
		List<List<Feature>> allFeatureList = new ArrayList<List<Feature>>();
		for (Map<String, Object> m : wordFeatures) {
			List<Feature> featureList = new ArrayList<Feature>();
			for (String s : m.keySet()) {
				Object o = m.get(s);
				if(o instanceof String){
					Feature f = new Feature(s, (String) m.get(s));
					featureList.add(f);
				}
			}
			allFeatureList.add(featureList);
		}
		return allFeatureList;
	}

	public void addTemplate(Template t) {
		templates.add(t);
	}
}
