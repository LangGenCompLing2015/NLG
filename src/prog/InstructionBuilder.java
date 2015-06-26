package prog;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lexicon.NLGLexicon;
import simplenlg.framework.WordElement;
import simplenlg.lexicon.Lexicon;
import templates.InstructionTemplateFinder;
import templates.Template;

public class InstructionBuilder implements Builder{
	private InstructionTemplateFinder finder ;
	private final String instructions;
	private NLGLexicon lexicon;
	
	private String obj1;
	
	private String obj2;
	
	private String verbType;
	
	public InstructionBuilder(String ins){
		instructions=ins;
		finder = new InstructionTemplateFinder();
		lexicon = new NLGLexicon();
		//setupTemplates();
		lexicon.setUpLexicon();
	}
	public void start(){
		Lexicon lex = lexicon.getLexicon();
		WordElement toast = lex.getWord(obj1);
		Map<String,Object> toastFeatures;
		toastFeatures = toast.getAllFeatures();
		//List<Template> findTemplate = finder.findTemplate(new ArrayList<String>(), toastFeatures, new ArrayList<String>());
	}
	
	private void parseInput(){
		
	}
	
//	private void setupTemplates(){
//		Template t = new Template();
//		t.addObj1Feature("flat");
//		finder.addTemplate(t);
//	}
}
