package lexicon;

import simplenlg.framework.WordElement;
import simplenlg.lexicon.Lexicon;

public class NLGLexicon {
	private Lexicon lexicon = Lexicon.getDefaultLexicon();

	public Lexicon getLexicon(){
		return lexicon;
	}
	
	public void setUpLexicon(){
		WordElement toast = lexicon.getWord("toast");
		toast.setFeature("shape", "flat");
		toast.getAllFeatures();
	}
}
