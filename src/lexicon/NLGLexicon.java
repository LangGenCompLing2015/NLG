package lexicon;

import simplenlg.framework.WordElement;
import simplenlg.lexicon.Lexicon;

/**
 * Class for saving the modified Lexicon.
 *
 */
public class NLGLexicon {
	private Lexicon lexicon = Lexicon.getDefaultLexicon();

	public Lexicon getLexicon(){
		return lexicon;
	}
	
	/**
	 * Method, which sets up the Lexicon with our custom features.
	 */
	public void setUpLexicon(){
		WordElement toast = lexicon.getWord("toast");
		toast.setFeature("shape", "flat");
		WordElement table = lexicon.getWord("table");
		table.setFeature("shape", "flat");
	}
}
