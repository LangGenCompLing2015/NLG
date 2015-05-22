package entry;

import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;




public class NLG {

        public static void main(String[] args) {
                Lexicon lexicon = Lexicon.getDefaultLexicon();
                NLGFactory nlgFactory = new NLGFactory(lexicon);
                Realiser realiser = new Realiser(lexicon);

        }

}