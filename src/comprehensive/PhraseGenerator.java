package comprehensive;

import java.util.*;

/**
 * Class to generate/print random, completed phrases to console. Iterates through start phrase using ArrayStack
 * and parses non-terminals to be added back onto stack until phrase is complete. Each complete phrase is then printed
 *
 * @author Brian Keller & Wyatt Young
 * @version July 24, 2024
 */
public class PhraseGenerator
{
    private ArrayList<String> startPhrase;
    private int numIterations;
    private StringBuilder phrase;
    private HashMap<String, List<String>> map;

    /**
     * constructor for PhraseGenerator object
     * @param startPhrase phrase to start with
     * @param numIterations number of iterations of that start phrase to generate with terminals
     * @param map a HashMap of non-terminals and their associated productions
     */
    public PhraseGenerator(ArrayList<String> startPhrase, int numIterations, HashMap<String, List<String>> map)
    {
        this.startPhrase = startPhrase;
        this.numIterations = numIterations;
        this.map = map;
    }

    /**
     * Method called in Main method to generate phrases. Uses ArrayStack to push and pop words/punctuation and retrieve
     * random productions when necessary to build phrase. Void as it simply prints completed phrases for all iterations k
     * on their own line
     */
    public void generatePhrases()
    {
        phrase = new StringBuilder();
        for(int i = 0; i < numIterations; i++) //O(k)
        {
            phrase.setLength(0); // reset SB for current phrase
            ArrayStack<String> stack = new ArrayStack<>(); // Stack for current phrase
            // Push start phrase onto stack
            stack.pushAllReverse(startPhrase); //O(s)
            // While stack has items, pop each and either append to phrase or deal w non-terminal
            while (!stack.isEmpty()) { // O(s + p)
                String curr = stack.pop();
                    if(isTerminal(curr))
                    {
                        phrase.append(curr);
                    }
                    else
                    {
                        // If non-terminal get random production from map, and parse
                        List<String> newWords = parsePhrases(getRandomString(map, curr)); //O(p)
                        // push new production onto stack
                        stack.pushAllReverse(newWords); //O(p)
                    }
            }
            System.out.println(phrase.toString());
        }
    }
    /**
     * private helper method to determine if word passed is a terminal
     * @param word from stack
     * @return true if terminal, false o/w
     */
    private boolean isTerminal(String word)
    {
        return !word.startsWith("<");
    }

    /**
     * helper method to get a random production from HashMap
     * @param map of non-terminals and their productions
     * @param key non production
     * @return random production
     * @throws NoSuchElementException if map list is null or empty
     */
    protected static String getRandomString(HashMap<String, List<String>> map, String key) throws NoSuchElementException{
        List<String> list = map.get(key);
        if(list == null || list.isEmpty())
        {
            throw new NoSuchElementException("List is empty " + key +" has no productions");
        }
        Random rng = new Random();
        return list.get(rng.nextInt(list.size()));
    }
    /**
     * helper method to parse phrases/productions pulled from hash map, loops through input String
     * as a character array and tracks angle brackets to return a List with each word(terminal or not).
     * @param input String production to be parsed
     * @return List of parsed elements from input string
     */
    protected static ArrayList<String> parsePhrases(String input) {
        ArrayList<String> parsed = new ArrayList<>();
        StringBuilder currWord = new StringBuilder();
        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            // non-terminal begin '<'
            if (c == '<')
            {
                if (currWord.length() > 0) {
                    parsed.add(currWord.toString());
                    currWord.setLength(0); // Reset current word SB
                }
                // Read until end of non-terminal ('>')
                currWord.append(c);
                while (i + 1 < input.length() && input.charAt(i + 1) != '>')
                {
                    currWord.append(input.charAt(++i));
                }
                if (i + 1 < input.length() && input.charAt(i + 1) == '>')
                {
                    currWord.append(input.charAt(++i));
                }
                parsed.add(currWord.toString()); // add non-terminal to result
                currWord.setLength(0); //reset SB for next iteration
            }
            // Spaces
            else if (Character.isWhitespace(c))
            {
                if (currWord.length() > 0)
                {
                    parsed.add(currWord.toString());
                    currWord.setLength(0);
                }
                // Add space to the result
                parsed.add(" ");
            }
            // Letter characters + punctuation
            else
            {
                currWord.append(c);
            }
        }
        if (currWord.length() > 0)
        {
            parsed.add(currWord.toString());
        }
        return parsed;
    }
}