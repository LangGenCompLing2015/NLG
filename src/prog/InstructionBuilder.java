package prog;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import features.Feature;
import lexicon.NLGLexicon;
import simplenlg.framework.WordElement;
import simplenlg.lexicon.Lexicon;
import templates.InstructionTemplateFinder;
import templates.Template;

public class InstructionBuilder implements Builder{
	private InstructionTemplateFinder finder ;
	private final String instructions;
	private NLGLexicon lexicon;
	
	private String verbType;
	
	public InstructionBuilder(String ins){
		instructions=ins;
		finder = new InstructionTemplateFinder();
		lexicon = new NLGLexicon();
		setupTemplates();
		lexicon.setUpLexicon();
	}
	public void start(){
		Lexicon lex = lexicon.getLexicon();
		List<String> inputWords = getWordsFromInput();
		List<Map<String,Object>> featureList = new ArrayList<Map<String,Object>>();
		for(String s : inputWords){
			WordElement w = lex.getWord(s);
			Map<String,Object> wordFeature = w.getAllFeatures();
			featureList.add(wordFeature);
			
		}
		List<Template> possibleTemplates = finder.findTemplate(verbType,featureList);
		Template t = possibleTemplates.get(0);
		System.out.println(t.toString(inputWords));
	}
	
	private List<String> getWordsFromInput(){
		List<String> inputWords = new ArrayList<String>(); 
		String[] split = instructions.split(",");
		verbType = split[0];
		for (int i = 1 ; i<split.length;i++){
			inputWords.add(split[i]);
		}
		return inputWords;
	}
	
	private void setupTemplates(){
		LinkedHashMap<List<Feature>,List<String>> map = new LinkedHashMap<List<Feature>, List<String>>();
		List<Feature> featureList1 = new ArrayList<Feature>();
		List<Feature> featureList2 = new ArrayList<Feature>();
		featureList1.add(new Feature("shape","flat"));
		featureList2.add(new Feature("shape","flat"));
		List<String> valueList1 = new ArrayList<String>();
		List<String> valueList2 = new ArrayList<String>();
		valueList1.add("place");
		valueList2.add("on");
		map.put(featureList1, valueList1);
		map.put(featureList2, valueList2);
		Template t = new Template("placing",map);
		finder.addTemplate(t);
	}
}
