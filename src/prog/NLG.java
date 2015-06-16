package prog;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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

public class NLG {
        private static final String[] VERBLIST = { "need", "require" };
        private static final String[] CONJLIST = { "and", "and also", "as well as" };
        private static final String[] CONNLIST = { "furthermore", "additionally",
                        "also" };
        public static void main(String[] args) {
                NLG nlg = new NLG();
                nlg.start();
        }
        Lexicon lexicon = Lexicon.getDefaultLexicon();
        NLGFactory nlgFactory = new NLGFactory(lexicon);
        Realiser realiser = new Realiser(lexicon);
        private BufferedReader br;
        public void start() {
                Random random = new Random();
                List<List<String>> sentenceList = new ArrayList<List<String>>();
                List<String> splittedBySemicolon;
                try {
                        FileReader fr = new FileReader("ingredients.txt");
                        BufferedReader br = new BufferedReader(fr);
                        String line = br.readLine();
                        splittedBySemicolon = new ArrayList<String>(Arrays.asList(line
                                        .split(";")));
                        addToTargetListOrSplitAndTryAgain(sentenceList, splittedBySemicolon);
                        br.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                for (int i = 0; i < sentenceList.size(); i++) {
                        SPhraseSpec phrase = nlgFactory.createClause();
                        phrase.setVerb(VERBLIST[random.nextInt(VERBLIST.length)]);
                        phrase.setSubject("you");
                        CoordinatedPhraseElement obj = nlgFactory.createCoordinatedPhrase();
                        List<NPPhraseSpec> phrases = new ArrayList<NPPhraseSpec>();
                        phrases = getPhrases(sentenceList.get(i));
                        for (int j = 0; j < phrases.size(); j++) {
                                obj.addCoordinate(phrases.get(j));
                                obj.setConjunction(CONJLIST[random.nextInt(CONJLIST.length)]);
                        }
                        phrase.setObject(obj);
                        String resultingSentence = realiser.realise(phrase).toString();
                        if (i > 0) {
                                resultingSentence = CONNLIST[random.nextInt(CONNLIST.length)]
                                                + ", " + resultingSentence;
                        }
                        System.out.println(resultingSentence);
                }
        }
        /**
         * Get Phrases from input.
         * 
         * @return the Phrases as {@link NPPhraseSpec}
         * @throws IOException
         */
        public List<NPPhraseSpec> getPhrases(List<String> sentence) {
                List<NPPhraseSpec> phrases = new ArrayList<NPPhraseSpec>();
                for (int i = 0; i < sentence.size(); i++) {
                        String[] str = sentence.get(i).split(",");
                        NLGElement el1 = nlgFactory
                                        .createWord(str[1], LexicalCategory.NOUN);
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