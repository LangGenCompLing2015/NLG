package prog;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import features.Feature;
import lexicon.NLGLexicon;
import simplenlg.features.Form;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.framework.WordElement;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;
import templates.InstructionTemplateFinder;
import templates.Template;

/**
 * Class, which builds instruction-phrases from the given message.
 * 
 */
public class InstructionBuilder implements Builder {
	private InstructionTemplateFinder finder;
	private String instructions;
	private NLGLexicon lexicon;
	private NLGFactory nlgFactory;
	private Realiser realiser;
	private SPhraseSpec clause;

	private String verbType;
	
	private int maxNum;
	private int currentNum;
	private Template t;

	public InstructionBuilder(String ins) {
		instructions = ins;
		finder = new InstructionTemplateFinder();
		lexicon = new NLGLexicon();
		setupTemplates();
		lexicon.setUpLexicon();
		nlgFactory = new NLGFactory(lexicon.getLexicon());
		realiser = new Realiser(lexicon.getLexicon());
		clause= nlgFactory.createClause();
	}

	public void start() {
		Lexicon lex = lexicon.getLexicon();
		getNUmOfInstructions();
		List<String> inputWords = getWordsFromInput();
		List<Map<String, Object>> featureList = new ArrayList<Map<String, Object>>();
		Random r = new Random();
		for (String s : inputWords) {
			WordElement w = lex.getWord(s);
			Map<String, Object> wordFeature = w.getAllFeatures();
			featureList.add(wordFeature);
			

		}
		// V: debug
		try {
			List<Template> possibleTemplates = finder.findTemplate(verbType,
					featureList);
			t = possibleTemplates.get(r.nextInt(possibleTemplates
					.size()));
			buildSentence(t.toString(inputWords));
			//System.out.println(t.toString(inputWords));
			System.out.println(realiser.realise(clause));
		} catch (Exception e) {
			System.err.println("No matching templates found!");
		}
	}

	private void buildSentence(String sentence) {
		String[] splitSentence = sentence.split(" ");
		String verb = splitSentence[0];
		String prep = splitSentence[2];
		String object = splitSentence[splitSentence.length-1];
		clause.setVerb(verb);		
		NPPhraseSpec np = nlgFactory.createNounPhrase("the", object);
		PPPhraseSpec prepPhrase = nlgFactory.createPrepositionPhrase(prep, np);
		clause.addComplement(prepPhrase);
		clause.setFeature(simplenlg.features.Feature.FORM, Form.IMPERATIVE);
	}

	private void getNUmOfInstructions() {
		String[] split = instructions.split(":",2);
		instructions = split[1];
		String numbers = split[0];
		String[] splitnumber = numbers.split("/");
		currentNum = Integer.parseInt(splitnumber[0]);
		maxNum = Integer.parseInt(splitnumber[1]);
	}

	/**
	 * Read all words from the input message.
	 * 
	 * @return the list with words, not including the verb type.
	 */
	private List<String> getWordsFromInput() {
		boolean first = true;
		List<String> inputWords = new ArrayList<String>();
		String[] split = instructions.split(";");
		
		verbType = split[0];
		for (int i = 1; i < split.length; i++) {
			NPPhraseSpec ph;
			String[] str = split[i].split(",");
			inputWords.add(str[2]);
			NLGElement el1 = nlgFactory.createWord(str[1], LexicalCategory.NOUN);
			NLGElement el2 = nlgFactory
					.createWord(str[2], LexicalCategory.NOUN);
			boolean plural = Integer.parseInt(str[0]) != 1;
			if (!str[1].isEmpty()) {
				NPPhraseSpec obj = nlgFactory.createNounPhrase("the", el1);
				PPPhraseSpec pp = nlgFactory.createPrepositionPhrase("of", el2);
				pp.setPlural(plural);
				ph = nlgFactory.createNounPhrase(obj, pp);
			} else {
				el2.setPlural(plural);
				ph = nlgFactory.createNounPhrase("the", el2);
			}

			if(first){
				clause.setObject(ph);
				first = false;
			}
		}

		
		return inputWords;
	}

	// TODO this Method needs to be more user-friendly. Possibly reading from
	// text file?
	/**
	 * Method for setting up the templates.
	 */
	private void setupTemplates() {
		LinkedHashMap<List<Feature>, List<String>> map = new LinkedHashMap<List<Feature>, List<String>>();
		LinkedHashMap<List<Feature>, List<String>> map2 = new LinkedHashMap<List<Feature>, List<String>>();
		List<Feature> featureList1 = new ArrayList<Feature>();
		List<Feature> featureList2 = new ArrayList<Feature>();
		List<Feature> featureList3a = new ArrayList<Feature>();
		List<Feature> featureList3b = new ArrayList<Feature>();
		featureList1.add(new Feature("shape", "flat"));
		featureList2.add(new Feature("shape", "flat"));
		featureList3a.add(new Feature("shape", "fluid"));
		featureList3b.add(new Feature("shape", "hollow"));
		List<String> valueList1 = new ArrayList<String>();
		List<String> valueList2 = new ArrayList<String>();
		List<String> valueList3a = new ArrayList<String>();
		List<String> valueList3b = new ArrayList<String>();
		valueList1.add("place");
		// V: put als Alternative
		valueList1.add("put");
		valueList2.add("on");
		valueList2.add("on top of");
		valueList3a.add("place");
		valueList3a.add("put");
		valueList3b.add("into");
		map.put(featureList1, valueList1);
		map.put(featureList2, valueList2);
		map2.put(featureList3a, valueList3a);
		map2.put(featureList3b, valueList3b);
		Template t = new Template("placing", map);
		finder.addTemplate(t);
		Template t2 = new Template("placing", map2);
		finder.addTemplate(t2);
	}

}
