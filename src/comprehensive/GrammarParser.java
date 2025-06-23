package comprehensive;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
*This class is used to parse grammar files into a format readable by our random phrase
 *  generating program. Inserts items into a Java Hash Map
*
*@author Brian Keller and Wyatt Young
*@version July 23, 2024
 */
public class GrammarParser {
    /**
     * this method parses a given grammar file, creating lists of each non-terminals productions
     * @param fileName the grammar file to be parsed
     * @return a hashmap of the non-terminals and their associated productions
     */
    public static HashMap<String, List<String>> parseGrammarFile(File fileName) {
        HashMap<String, List<String>> grammarMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String currLine;
            String currString = null;
            boolean inside = false;
            List<String> currProductions = null;
            while ((currLine = reader.readLine()) != null) {
                currLine = currLine.trim();
                if (currLine.isEmpty()) {
                    continue;
                }
                if (currLine.equals("{")) {
                    inside = true;
                    currProductions = new ArrayList<>();
                } else if (currLine.equals("}")) {
                    inside = false;
                    grammarMap.put(currString, currProductions);
                    currString = null;
                } else if (inside) {
                    if (currString == null) {
                        currString = currLine;
                    } else {
                        currProductions.add(currLine);
                    }
                }
            }
        } catch (IOException e) {
            System.out.print("File not found");
        }
        return grammarMap;
    }
}
