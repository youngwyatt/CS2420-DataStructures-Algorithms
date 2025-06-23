package assign08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a "dictionary" of strings using a binary search tree and offers
 * methods for spell-checking documents.
 * 
 * @author Prof. Parker and Brian Keller and Wyatt Young
 * @version June 30, 2024
 */
public class SpellChecker {
	private BinarySearchTree<String> dictionary;
	/**
	 * Creates empty dictionary.
	 */
	public SpellChecker() {
		dictionary = new BinarySearchTree<String>();
	}
	/**
	 * Creates dictionary from a list of words.
	 * 
	 * @param words - the list of strings used to build the dictionary
	 */
	public SpellChecker(List<String> words) {
		this();
		buildDictionary(words);
	}
	/**
	 * Creates dictionary from a file.
	 * 
	 * @param dictionaryFile - the file containing strings used to build the dictionary
	 */
	public SpellChecker(File dictionaryFile) {
		this();
		buildDictionary(readFromFile(dictionaryFile));
	}
	/**
	 * Adds a word to the dictionary in a sorted order.
	 * 
	 * @param word - the string to be added to the dictionary
	 */
	public void addToDictionary(String word)
	{
		String lowerCaseWord = word.toLowerCase();
		if(dictionary.contains(lowerCaseWord)){throw new IllegalArgumentException("No duplicates!");}
		dictionary.add(lowerCaseWord);
	}
	/**
	 * Removes a word from the dictionary.
	 * 
	 * @param word - the string to be removed from the dictionary
	 */
	public void removeFromDictionary(String word)
	{
		String lowerCaseWord = word.toLowerCase();
		if(!dictionary.contains(lowerCaseWord)){throw new IllegalArgumentException("Word doesn't exist!");}
		dictionary.remove(lowerCaseWord);
	}
	/**
	 * Spell-checks a document against the dictionary.
	 * 
	 * @param documentFile - the file containing strings to be looked up in the dictionary
	 * @return the list of misspelled words
	 */
	public List<String> spellCheck(File documentFile) {
		List<String> wordsToCheck = readFromFile(documentFile);
		List<String> misspells = new ArrayList<>();
		for(String word : wordsToCheck)
		{
			String lowerCaseWord = word.toLowerCase();
			if(!dictionary.contains(lowerCaseWord))
			{
				misspells.add(lowerCaseWord);
			}
		}
		return misspells;
	}
	/**
	 * Determines the number of words that are between begin and end (inclusive).
	 * 
	 * @param begin - the string at the beginning of the range
	 * @param end - the string at the end of the range
	 * @return the number of words that are between begin and end
	 */
	public int countWordsBetween(String begin, String end)
	{
		String lowerBegin = begin.toLowerCase();
		String lowerEnd = end.toLowerCase();
		Object[] rangeList = dictionary.toArrayRange(lowerBegin, lowerEnd);
		if(rangeList.length == 0)
		{
			String lowerBeginTwo = begin.toLowerCase();
			String lowerEndTwo = end.toLowerCase();
			Object[] rangeListTwo = dictionary.toArrayRange(lowerEndTwo, lowerBeginTwo);
			return rangeListTwo.length;
		}
		return rangeList.length;
	}
	/**
	 * Fills in the dictionary with the input list of words.
	 * 
	 * @param words - the list of strings to be added to the dictionary
	 */
	private void buildDictionary(List<String> words)
	{
		for (String word : words)
		{
			String lowerWord = word.toLowerCase();
			dictionary.add(lowerWord);
		}
	}
	/**
	 * Generates a list of the words contained in the specified file. 
	 * Note that symbols, digits, and spaces are ignored, and all characters
	 * are converted to lowercase.
	 * 
	 * @param file - the file to be read
	 * @return the list of the strings in the input file
	 */
	private List<String> readFromFile(File file) {
		ArrayList<String> words = new ArrayList<String>();
		try {
			Scanner fileInput = new Scanner(file);
			fileInput.useDelimiter("\\s*[^a-zA-Z]\\s*");
			while(fileInput.hasNext()) {
				String s = fileInput.next();
				if(!s.equals("")) 
					words.add(s.toLowerCase());
			}
			fileInput.close();
		}
		catch(FileNotFoundException e) {
			System.err.println("File " + file + " cannot be found.");
		}
		return words;
	}
}