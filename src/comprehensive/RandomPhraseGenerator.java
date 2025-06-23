package comprehensive;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class contains the main method that starts the program and is called in the command line.
 * Stores input args and parses start phrase before calling PhraseGenerator to randomly complete
 * and print each phrase to the console.
 *
 * @author Brian Keller & Wyatt Young
 * @version July 21, 2024
 */
public class RandomPhraseGenerator {
    public static void main(String[] args)
    {
        String inputFilePath = args[0];
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists()) {
            System.err.println("File not found: " + inputFilePath);
            return;
        }
        // grammar parse file input
        HashMap<String, List<String>> grammar;
        grammar = GrammarParser.parseGrammarFile(inputFile);
        // store and parse start phrase
        String startParse = PhraseGenerator.getRandomString(grammar, "<start>");
        ArrayList<String> startPhrase;
        startPhrase = PhraseGenerator.parsePhrases(startParse);
        // store number of phrases to generate
        int k = Integer.parseInt(args[1]);
        // call phraseGenerator
        PhraseGenerator phraseGenerator = new PhraseGenerator(startPhrase, k, grammar);
        phraseGenerator.generatePhrases();
    }
}
