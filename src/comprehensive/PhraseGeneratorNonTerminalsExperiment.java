package comprehensive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PhraseGeneratorNonTerminalsExperiment {

    // Set these numbers for your experiment ///////////////////////////////
    private static final int firstN = 10000; // smallest value of N
    private static final int incrementForN = 1000; // how much N increases by each step
    private static final int numberOfNValues = 40; // how many steps (values of N)
    // Increase timesToLoop to get more accurate, smoother results.
    private static final int timesToLoop = 10;
    private static final String[] TERMINALS = {"cat", "dog", "mouse", "fish", "bird", "cow", "frog", "lion", "tiger", "bear"};
    private static final int numProductionRules = 5; // Constant number of production rules per non-terminal
    private static final int numNonTerminalsPerRule = 2; // Constant number of non-terminals per production
    ////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        long startTime, endTime, afterCompensation;
        HashMap<String, List<String>> grammarTable;
        PhraseGenerator phraseGenerator;
        ArrayList<String> startPhrase;
        long[] problemSizes = new long[numberOfNValues];
        problemSizes[0] = firstN;
        for (int i = 1; i < numberOfNValues; i++) {
            problemSizes[i] = problemSizes[i - 1] + incrementForN;
        }

        System.out.println("size(N)    generate_time(ns)");

        for (long N : problemSizes)
        {
            System.gc();
            // Generate grammar with N non-terminals and a fixed number of production rules
            grammarTable = generateGrammar((int) N);

            // Extract start phrase and initialize phrase generator
            startPhrase = getStartPhrase(grammarTable);
            phraseGenerator = new PhraseGenerator(startPhrase, 10, grammarTable);
            startTime = System.nanoTime();
            for(int i = 0; i < timesToLoop; i++){
                // Generate phrases
                phraseGenerator.generatePhrases();
            }
            endTime = System.nanoTime();
            // compensation loop
            for(int i = 0; i < timesToLoop; i++){getStartPhrase(grammarTable);}
            afterCompensation = System.nanoTime();
            double compTime = afterCompensation - endTime;
            double generateTime = endTime - startTime;
            double averageTime = (double) (generateTime - compTime) / timesToLoop;
            System.out.printf("%-10d%-15.2f%n", N, averageTime);
        }
    }

    private static HashMap<String, List<String>> generateGrammar(int numNonTerminals) {
        HashMap<String, List<String>> grammarTable = new HashMap<>();
        Random rand = new Random();

        // Start rule with predictable non-terminals
        List<String> startProductions = new ArrayList<>();
        startProductions.add("<nonTerminal0> <nonTerminal1> <nonTerminal2>");
        grammarTable.put("<start>", startProductions);

        // Non-terminal rules
        for (int i = 0; i < numNonTerminals; i++) {
            String nonTerminal = "<nonTerminal" + i + ">";
            List<String> productions = new ArrayList<>();
            for (int j = 0; j < numProductionRules; j++) {
                productions.add(generateProduction(numNonTerminals, rand));
            }
            grammarTable.put(nonTerminal, productions);
        }

        return grammarTable;
    }

    private static String generateProduction(int numNonTerminals, Random rand) {
        StringBuilder production = new StringBuilder();
        int numNonTerminalsPerRule = rand.nextInt(3); // Random number between 0 and 2
        for (int i = 0; i < numNonTerminalsPerRule; i++) {
            if (i > 0) {
                production.append(" ");
            }
            if (rand.nextBoolean()) {
                production.append("<nonTerminal").append(rand.nextInt(numNonTerminals)).append(">");
            } else {
                production.append(TERMINALS[rand.nextInt(TERMINALS.length)]);
            }
        }
        return production.toString();
    }

    private static ArrayList<String> getStartPhrase(HashMap<String, List<String>> grammarTable) {
        List<String> startProductions = grammarTable.get("<start>");
        String startPhrase = startProductions.get(0);
        return PhraseGenerator.parsePhrases(startPhrase);
    }
}