package assign06;

/**
 * Represents a change to a text String. Each edit has a position, a flag for
 * insert/remove, and a character that is either inserted or removed from the
 * position.
 * 
 * @author Eric Heisler
 * @version 2024-6-10
 */
public class Edit {
	private char character;
	private int position;
	private boolean isInsert;

	public static final boolean INSERT = true;
	public static final boolean REMOVE = false;

	/**
	 * Constructs a new Edit.
	 * 
	 * @param character char
	 * @param position of char
	 * @param mode     either Edit.INSERT or Edit.REMOVE
	 */
	public Edit(char character, int position, boolean mode)
	{
		this.character = character;
		this.position = position;
		this.isInsert = mode;
	}
	
	/**
	 * Applies this edit to a given StringBuilder to modify it by one character.
	 * 
	 * @param textString to modify
	 */
	public void apply(StringBuilder textString)
	{
		if (isInsert)
			textString.insert(position, character);
		else
			textString.deleteCharAt(position);
	}
	
	/**
	 * Reverses an edit on a given StringBuilder.
	 * 
	 * @param textString to modify
	 */
	public void revert(StringBuilder textString) {
		isInsert = !isInsert;
		apply(textString);
		isInsert = !isInsert;
	}
	
	/**
	 * @return true if this equals other.
	 */
	public boolean equals(Object other) {
		if (!(other instanceof Edit))
			return false;
		Edit otherEdit = (Edit) other;
		return character == otherEdit.character
				&& position == otherEdit.position 
				&& isInsert == otherEdit.isInsert;
	}
	
	/**
	 * @return a String representation of this Edit
	 */
	public String toString() {
		if (isInsert)
			return "insert " + character + " at " + position;
		else
			return "remove " + character + " at " + position;
	}
}
