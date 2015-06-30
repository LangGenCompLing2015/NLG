package lexicon;

import simplenlg.framework.WordElement;
import simplenlg.lexicon.Lexicon;

/**
 * Class for saving the modified Lexicon.
 * 
 */
public class NLGLexicon {
	private Lexicon lexicon = Lexicon.getDefaultLexicon();

	public Lexicon getLexicon() {
		return lexicon;
	}

	/**
	 * Method, which sets up the Lexicon with our custom features.
	 */
	public void setUpLexicon() {
		WordElement toast = lexicon.getWord("toast");
		toast.setFeature("shape", "flat");
		WordElement table = lexicon.getWord("table");
		table.setFeature("shape", "flat");
		// V: mögliche andere Wörter
		WordElement tomato = lexicon.getWord("tomato");
		tomato.setFeature("shape", "flat");
		WordElement cheese = lexicon.getWord("cheese");
		cheese.setFeature("shape", "flat");
		WordElement bowl = lexicon.getWord("bowl");
		bowl.setFeature("shape", "hollow");
		WordElement water = lexicon.getWord("water");
		water.setFeature("shape", "fluid");
	}
}
