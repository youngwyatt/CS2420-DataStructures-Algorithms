package assign08;

import java.io.File;
import java.util.List;

/**
 * A small demonstration of the SpellChecker class.
 * 
 * @author Prof. Parker
 * @version June 27, 2024
 */
public class SpellCheckerDemo {

	public static void main(String[] args) {
		SpellChecker mySC = new SpellChecker(new File("src/assign08/dictionary.txt"));

		runSpellCheck(mySC, "src/assign08/hello_world.txt");
		runSpellCheck(mySC, "src/assign08/good_luck.txt");
	}

	/**
	 * Runs the given spell checker (with dictionary already added) on the specified file.
	 * 
	 * @param checker - the given spell checker
	 * @param documentFilename - name of the file to be spell checked
	 */
	private static void runSpellCheck(SpellChecker checker, String documentFilename) {
		File doc = new File(documentFilename);
		List<String> misspelledWords = checker.spellCheck(doc);
		if(misspelledWords.size() == 0) 
			System.out.println("There are no misspelled words in file " + doc + ".");
		else {
			System.out.println("The misspelled words in file " + doc + " are:");
			for(String w : misspelledWords) 
				System.out.println("\t" + w);
		}
	}
}