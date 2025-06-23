package comprehensive;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a single entry in a map; i.e., a key-value pair of strings.
 * 
 * @author Brian keller & Wyatt Young
 * @version July 23, 2024
 *
 */
public class MapEntry {

	private String key;
	private String value;

	/**
	 * Creates a new MapEntry with the specified key and an empty list of values.
	 *
	 * @param key - the key portion of the new map entry
	 */
	public MapEntry(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Getter for the key portion of this MapEntry.
	 *
	 * @return the key of this MapEntry
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * Getter for the values portion of this MapEntry.
	 *
	 * @return the list of values of this MapEntry
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Overrides Objects's equals method to leverage the equals methods of the 
	 * key and value members.
	 * 
	 * @param other - the object to compare this MapEntry with
	 * @return true if the objects are equal, false otherwise
	 */
	public boolean equals(Object other) {
		if(!(other instanceof MapEntry))
			return false;

		MapEntry rhs = (MapEntry) other;
		return key.equals(rhs.key);
	}
	
	/**
	 * Overrides Object's toString method to leverage the toString methods of the 
	 * key and value members.
	 * 
	 * @return a textual representation of this MapEntry; i.e., (key, value) pair
	 */
	public String toString() {
		return "(" + key + ", " + value + ")";
	}
}