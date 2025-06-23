package comprehensive;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing a separate chaining hashtable backed by an ArrayList of ArrayLists of MapEntry objects.
 * Uses primary hashing and clamping for indexing.
 *
 * @author Brian Keller & Wyatt Young
 * @version July 23, 2024
 */
public class HashTable
{
    private ArrayList<ArrayList<MapEntry>> table;
    private int capacity;
    private int size;
    /**
     * Constructor for a hash table backed by an ArrayList.
     */
    public HashTable() {
        this.capacity = 101;
        this.size = 0;
        table = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            table.add(new ArrayList<>());
        }
    }

    /**
     * Removes all mappings from this map.
     *
     * O(table length)
     */
    public void clear() {
        for (int i = 0; i < table.size(); i++) {
            table.set(i, new ArrayList<>());
        }
        this.size = 0;
    }

    /**
     * Determines whether this map contains the specified key.
     * O(1)
     *
     * @param key - the key being searched for
     * @return true if this map contains the key, false otherwise
     */
    public boolean containsKey(String key)
    {
        int index = clamp(primaryHash(key));
        ArrayList<MapEntry> chain = table.get(index);
        return !chain.isEmpty();
    }

    /**
     * Determines whether this map contains the specified value.
     * O(table length)
     *
     * @param value - the value being searched for
     * @return true if this map contains one or more keys to the specified value,
     * false otherwise
     */
    public boolean containsValue(String value)
    {
        for (int i = 0; i < this.capacity; i++)
        {
            for (MapEntry entry : table.get(i))
            {
                if (entry.getValue().contains(value))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns a list view of the mappings contained in this map, where the ordering of
     * mappings in the list is insignificant.
     * <p>
     * O(table length)
     *
     * @return a list containing all mappings (i.e., entries) in this map
     */
    public List<MapEntry> entries() {
        List<MapEntry> list = new ArrayList<>(size);
        for (ArrayList<MapEntry> chain : table)
        {
            list.addAll(chain);
        }
        return list;
    }
    /**
     * Gets the value to which the specified key is mapped.
     * O(1)
     *
     * @param key - the key for which to get the mapped value
     * @return the value to which the specified key is mapped, or null if this map
     * contains no mapping for the key
     */
    public ArrayList<MapEntry> get(String key)
    {
        int primary = primaryHash(key);
        int clamp = clamp(primary);
        if(table.get(clamp).isEmpty())
        {
            return null;
        }
        else return table.get(clamp);
    }
    /**
     * Gets the start phrase to which the specified key is mapped.
     * O(1)
     *
     * @return the value to which the specified key is mapped, or null if this map
     * contains no mapping for the key
     */
    public String getStart()
    {
        int primary = primaryHash("<start>");
        int clamp = clamp(primary);
        if(table.get(clamp).isEmpty())
        {
            return null;
        }
        else
        {
            MapEntry start = table.get(clamp).get(0);
            return start.getValue();
        }
    }
    /**
     * Gets a random value from the list of values mapped to the specified key.
     * O(1)
     *
     * @param key - the key for which to get a random mapped value
     * @return a random value mapped to the specified key, or null if this map
     * contains no mapping for the key
     */
    public String getRandomValue(String key)
    {
        int primary = primaryHash(key);
        int clamp = clamp(primary);
        Random rng = new Random();
        // get chain length for random int
        ArrayList<MapEntry> chain = table.get(clamp);
        int separateLength = chain.size();
        if(chain.isEmpty())
        {
            return null;
        }
        MapEntry result = chain.get(rng.nextInt(separateLength));
        return result.getValue();
    }

    /**
     * Determines whether this map contains any mappings.
     * <p>
     * O(1)
     *
     * @return true if this map contains no mappings, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * (I.e., if the key already exists in this map, adds the value to the list of values;
     * otherwise adds the specified key with the value in a new MapEntry.)
     * <p>
     * O(1)
     *
     * @param key - the key for which to update the value (if exists)
     * @param value - the value to be mapped to the key
     */
    public void put(String key, String value)
    {
        //check load factor, rehash if necessary
        if(getLoad() >= 1.0){
            rehash();
        }
        int primary = primaryHash(key);
        int clamp = clamp(primary);
        ArrayList<MapEntry> chain = table.get(clamp);
        chain.add(new MapEntry(key, value));
        size++;
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     *
     * O(1)
     *
     * @param key - the key to be removed
     * @return the previous value associated with key, or null if there was no
     * mapping for key
     */
    public ArrayList<MapEntry> remove(String key)
    {
        int primary = primaryHash(key);
        int clamp = clamp(primary);
        ArrayList<MapEntry> chain = table.get(clamp);
        ArrayList<MapEntry> removedEntries = new ArrayList<>();

        // Iterate through the chain to find and remove entries with the given key
        for (int i = 0; i < chain.size(); i++) {
            MapEntry entry = chain.get(i);
            if (entry.getKey().equals(key)) {
                removedEntries.add(entry);
                chain.remove(i);
                size--;
                i--;
            }
        }
        if(removedEntries.isEmpty()) return null;
        else return removedEntries;
    }

    /**
     * method for getting the current capacity of the table, used in testing
     * @return the capacity of the table
     */
    protected int getCapacity(){
        return this.capacity;
    }

    /**
     * Determines the number of mappings in this map.
     * O(1)
     *
     * @return the number of mappings in this map
     */
    public int size() {
        return this.size;
    }
    /**
     * private helper method for clamping based on current table capacity
     * @return clamped index
     */
    private int clamp(int position)
    {
        return Math.abs(position) % capacity;
    }

    /**
     * getter for load factor, lambda
     */
    private double getLoad()
    {
        return (double)size/capacity;
    }
    /**
     * primary hashing function helper
     */
    private int primaryHash(String key)
    {
//        char[] chars = key.toCharArray();
//        int hash = 0;
//        for(char l : chars)
//        {
//            hash += l;
//        }
//        return hash;
        return key.hashCode();
    }

    private void rehash() {
        int oldCapacity = this.capacity;
        int newCapacity = nextPrime();
        //create list to store elements
        ArrayList<ArrayList<MapEntry>> newTable = new ArrayList<>(newCapacity);
        for(int i = 0; i < newCapacity; i++)
        {
            newTable.add(new ArrayList<>());
        }
        for(int i = 0; i < oldCapacity; i++)
        {
            for(MapEntry entry : table.get(i))
            {
                int primary = primaryHash(entry.getKey());
                int newIndex = clamp(primary);
                newTable.get(newIndex).add(entry);
            }
        }
        table = newTable;
    }

    /**
     * helper method for finding next prime capacity when rehashing
     * @return the next prime
     */
    private int nextPrime(){
        int currentCap = this.capacity;
        int nextCap = currentCap * 2;
        while(true){
            if(isPrime(nextCap))return nextCap;
            nextCap++;
        }
    }

    /**
     * helper method for finding the prime before the current capacity of the table
     * @param number before which a prime is found
     * @return the previous prime
     */
    private int previousPrime(int number){
        int temp = 0;
        for(int i  = number - 1; i > 1; i--){
            if(isPrime(i))
            {
                temp = i;
                break;
            }
        }
        return temp;
    }

    /**
     * private helper method checking if number is prime
     * @param number potential prime number
     * @return true if prime, false o/w
     */
    private boolean isPrime(int number){
        for(int i = 2; i < number/2; i++){
            int remainder = number % i;
            if (remainder == 0) return false;
        }
        return true;
    }
}
