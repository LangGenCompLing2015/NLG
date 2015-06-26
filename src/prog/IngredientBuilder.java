package prog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;

/**
 * Class for creating sentences which describe, what ingredients are needed for the recipe.
 * @author Bachmann, Czichon, Siewerts, Spinnreker, Wellhausen
 *
 */
public class IngredientBuilder implements Builder {
	private static final String[] VERBLIST = { "need", "require" };
	private static final String[] CONJLIST = { "and", "and also", "as well as" };
	private static final String[] CONNLIST = { "furthermore,", "additionally,",
			"also,", "moreover," };

	private static final Map<String, String> WORDMAP = new HashMap<String, String>();

	private final String ingredients;

	private Lexicon lexicon = Lexicon.getDefaultLexicon();
	private NLGFactory nlgFactory = new NLGFactory(lexicon);
	private Realiser realiser = new Realiser(lexicon);
	/**
	 * Constructs the builder with the given ingredients.
	 * @param ing the ingredients
	 */
	public IngredientBuilder(String ing) {
		ingredients = ing;
	}

	/**
	 * Initialize all additional Information we might need.
	 */
	private void initialize() {
		WORDMAP.put("tbsp", "tablespoon");
		WORDMAP.put("gr", "gramm");
		WORDMAP.put("g", "gramm");
		WORDMAP.put("l", "liter");
		WORDMAP.put("ml", "milliliter");
		WORDMAP.put("tsp", "teaspoon");
	}

	public void start() {
		initialize();
		Random random = new Random();
		List<List<String>> sentenceList = new ArrayList<List<String>>();
		List<String> splitBySemicolon;
		String line = ingredients;
		line.split(":");

		splitBySemicolon = new ArrayList<String>(Arrays.asList(line.split(";")));
		addToTargetListOrSplitAndTryAgain(sentenceList, splitBySemicolon);

		ArrayList<String> conjunctionsCopy = new ArrayList<String>(
				Arrays.asList(CONJLIST.clone()));
		Collections.shuffle(conjunctionsCopy);

		ArrayList<String> connectorsCopy = new ArrayList<String>(
				Arrays.asList(CONNLIST.clone()));
		Collections.shuffle(connectorsCopy);

		
		for (int i = 0; i < sentenceList.size(); i++) {
			SPhraseSpec phrase = nlgFactory.createClause();
			phrase.setVerb(VERBLIST[random.nextInt(VERBLIST.length)]);
			phrase.setSubject("you");
			CoordinatedPhraseElement obj = nlgFactory.createCoordinatedPhrase();
			List<NPPhraseSpec> phrases = new ArrayList<NPPhraseSpec>();
			phrases = getPhrases(sentenceList.get(i));
			for (int j = 0; j < phrases.size(); j++) {
				obj.addCoordinate(phrases.get(j));
				if (j == phrases.size() - 1) {
					if (conjunctionsCopy.isEmpty()) {
						conjunctionsCopy = new ArrayList<String>(
								Arrays.asList(CONJLIST.clone()));
						Collections.shuffle(conjunctionsCopy);
					}
					obj.setConjunction(conjunctionsCopy.get(0));
					conjunctionsCopy.remove(0);
				}
			}
			phrase.setObject(obj);
			if (i > 0) {
				if (connectorsCopy.isEmpty()) {
					connectorsCopy = new ArrayList<String>(
							Arrays.asList(CONNLIST.clone()));
					Collections.shuffle(connectorsCopy);
				}
				phrase.setFrontModifier(connectorsCopy.get(0));
				connectorsCopy.remove(0);
			}

			System.out.println(realiser.realiseSentence(phrase));
		}
	}

	/**
	 * Builds a List of {@link NPPhraseSpec} from the given String list, which contains
	 * all ingredients and further information. The returned list contains the noun phrases
	 * which can then be used by nlg to build sentences.
	 * @param sentence the information for building sentences
	 * @return the noun phrases
	 */
	public List<NPPhraseSpec> getPhrases(List<String> sentence) {
		List<NPPhraseSpec> phrases = new ArrayList<NPPhraseSpec>();
		for (int i = 0; i < sentence.size(); i++) {
			String[] str = sentence.get(i).split(",");
			// get full word from abbreviation
			String noun = WORDMAP.get(str[1]);
			if (noun == null) {
				noun = str[1];
			}
			NLGElement el1 = nlgFactory.createWord(noun, LexicalCategory.NOUN);
			NLGElement el2 = nlgFactory
					.createWord(str[2], LexicalCategory.NOUN);
			boolean plural = Integer.parseInt(str[0]) != 1;
			if (!str[1].isEmpty()) {
				NPPhraseSpec obj = nlgFactory.createNounPhrase(str[0], el1);
				PPPhraseSpec pp = nlgFactory.createPrepositionPhrase("of", el2);
				pp.setPlural(plural);
				NPPhraseSpec ph = nlgFactory.createNounPhrase(obj, pp);
				phrases.add(ph);
			} else {
				el2.setPlural(plural);
				NPPhraseSpec ph = nlgFactory.createNounPhrase(str[0], el2);
				phrases.add(ph);
			}
		}
		return phrases;
	}

	public void addToTargetListOrSplitAndTryAgain(
			List<List<String>> targetList, List<String> sourceList) {
		if (sourceList.size() < 6) {
			targetList.add(sourceList);
		} else {
			int halfSize = sourceList.size() / 2;
			List<String> firstHalf = sourceList.subList(0, halfSize);
			List<String> secondHalf = sourceList.subList(halfSize,
					sourceList.size());
			addToTargetListOrSplitAndTryAgain(targetList, firstHalf);
			addToTargetListOrSplitAndTryAgain(targetList, secondHalf);
		}
	}
}
