package templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import features.Feature;

public class Template {
	//Must be LinkedHashMap, since order of insertion matters.
	private LinkedHashMap<List<Feature>,List<String>> featureMap;
	private String verbType;
	
	public Template(String verbType, LinkedHashMap<List<Feature>,List<String>> featureMap){
		this.featureMap = featureMap;
		this.verbType = verbType;
	}
	
	public void addVerbFeature(List<Feature> features, List<String> possibleValues){
		featureMap.put(features,possibleValues);
	}
	
	public boolean matches(String verbType,List<List<Feature>> features){
		if(verbType!= this.verbType){
			return false;
		}
		Set<List<Feature>> keySet = featureMap.keySet();
		if(keySet.size() != features.size()){
			return false;
		}
		int i = 0;
		for(List<Feature> list : keySet){
			List<Feature> featureList = features.get(i);
			for(Feature f : list){
				if(!featureList.contains(f)){
					return false;
				}
			}
			i++;
		}
		return true;
	}
}
