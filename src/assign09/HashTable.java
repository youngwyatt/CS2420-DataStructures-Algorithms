package assign09;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a hashtable backed by an ArrayList of MapEntry key-value pairs.
 * Uses double hashing and multiple primary hash functions.
 * @param <k> generic key type
 * @param <v> generic value type
 * @author Brian Keller & Wyatt Young
 * @version July 8, 2024
 */
public class HashTable<k, v> implements Map<k, v>
{
    private ArrayList<MapEntry<k, v>> table;
    private int capacity;
    private int size;
    private int secondaryHashPrime;
    private long collisions;
    /**
     * constructor for a hash table backed by an ArrayList of student information including
     * name and uid.
     */
    public HashTable()
    {
        this.capacity = 101;
        this.secondaryHashPrime = previousPrime(capacity);
        this.collisions = 0;
        table = new ArrayList<MapEntry<k, v>>();
        for(int i = 0; i < capacity; i++)
        {
            table.add(null);
        }
    }

    /**
     * Removes all mappings from this map.
     * <p>
     * O(table length)
     */
    @Override
    public void clear() {
        for(int i  = 0; i < table.size(); i++)
        {
            table.set(i, null);
        }
        this.size = 0;
    }

    /**
     * Determines whether this map contains the specified key.
     * <p>
     * O(1)
     *
     * @param key - the key being searched for
     * @return true if this map contains the key, false otherwise
     */
    @Override
    public boolean containsKey(k key)
    {
        int primary = primaryHash(key);
        int clamp = clamp(primary);
        while(table.get(clamp) != null){
            MapEntry<k,v> curr = table.get(clamp);
            k currKey = curr.getKey();
            if(currKey.equals(key))
            {
                if(curr.getValue() != null) return !curr.getValue().equals("deleted");
                else return true;
            }
            else
            {
                incrementCollisions();
                clamp = clamp + secondHashFn(key);
                clamp = clamp(clamp);
            }
        }
        return false;
    }

    /**
     * Determines whether this map contains the specified value.
     * <p>
     * O(table length)
     *
     * @param value - the value being searched for
     * @return true if this map contains one or more keys to the specified value,
     * false otherwise
     */
    @Override
    public boolean containsValue(v value)
    {
        for(MapEntry<k,v> item : table){
            if(item != null && !item.getValue().equals("deleted") && item.getValue().equals(value))
                return true;
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
    @Override
    public List<MapEntry<k, v>> entries() {
        List<MapEntry<k, v>> list = new ArrayList<>(size);
        for (MapEntry<k, v> item : table) {
            if (item != null && !item.getValue().equals("deleted")) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     * Gets the value to which the specified key is mapped.
     * <p>
     * O(1)
     *
     * @param key - the key for which to get the mapped value
     * @return the value to which the specified key is mapped, or null if this map
     * contains no mapping for the key
     */
    @Override
    public v get(k key)
    {
        int primary = primaryHash(key);
        int clamp = clamp(primary);
        while(table.get(clamp) != null){
            MapEntry<k,v> curr = table.get(clamp);
            if(curr.getKey().equals(key) && curr.getValue() != null && !curr.getValue().equals("deleted")) return curr.getValue();
            else
            {
                clamp = clamp + secondHashFn(key);
                clamp = clamp(clamp);
            }
        }
        return null;
    }

    /**
     * Determines whether this map contains any mappings.
     * <p>
     * O(1)
     *
     * @return true if this map contains no mappings, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * (I.e., if the key already exists in this map, resets the value;
     * otherwise adds the specified key-value pair.)
     * <p>
     * O(1)
     *
     * @param key   - the key for which to update the value (if exists)
     *              or to be added to the table
     * @param value - the value to be mapped to the key
     * @return the previous value associated with key, or null if there was no
     * mapping for key
     */
    @Override
    public v put(k key, v value)
    {
        //check load factor, rehash if necessary
        if(getLoad() >= 0.75){
            rehash();
        }
        MapEntry<k, v> curr = new MapEntry<>(key, value);
        int primary = primaryHash(key);
        int clamp = clamp(primary);
        int h = secondHashFn(key);
        while(true)
        {
         MapEntry<k,v> entry = table.get(clamp);
            //if the slot is empty or occupied by a deleted value, update the value
            if(entry == null || entry.getValue() != null && entry.getValue().equals("deleted")) {
                table.set(clamp, curr);
                size++;
                return null;
            }
         //if the key already exists, update the value and return the previous value
            if(entry.getKey().equals(key)){
                v temp = entry.getValue();
                entry.setValue(value);
                return temp;
            }
         //update the clamp to move to the next position
         clamp = clamp(clamp + h);
         incrementCollisions();
        }

    }

    /**
     * Removes the mapping for a key from this map if it is present.
     * <p>
     * O(1)
     *
     * @param key - the key to be removed
     * @return the previous value associated with key, or null if there was no
     * mapping for key
     */
    @Override
    public v remove(k key)
    {
        int primary = primaryHash(key);
        int clamp = clamp(primary);
        while(table.get(clamp) != null){
            MapEntry<k,v> curr = table.get(clamp);
            if(curr.getKey().equals(key)){
                v temp = curr.getValue();
               curr.setValue((v)"deleted");
               size--;
                return temp;
            }
            else{
                clamp = clamp + secondHashFn(key);
                clamp = clamp(clamp);
            }
        }
        return null;
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
     * <p>
     * O(1)
     *
     * @return the number of mappings in this map
     */
    @Override
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
     * second hash function used for double hashing collision resolving strategy
     *
     * @param key to be hashed to array index
     * @return index to probe
     */
    private int secondHashFn(k key)
    {
        // prime number < table capacity
        int p = secondaryHashPrime;
        return p - (primaryHash(key) % p);
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
    private int primaryHash(k key)
    {
        if (key instanceof StudentBadHash) {
            return ((StudentBadHash) key).hashCode();
        } else if (key instanceof StudentMediumHash) {
            return ((StudentMediumHash) key).hashCode();
        } else if (key instanceof StudentGoodHash) {
            return ((StudentGoodHash) key).hashCode();
        } else {
            return key.hashCode();
        }
    }

    /**
     * get student good hashcode method
     * @param student instance
     * @return the hashcode method
     */
    private int getStudentGoodHash(StudentGoodHash student) {
        return student.hashCode();
    }
    /**
     * get student medium hashcode method
     * @param student instance
     * @return the hashcode method
     */
    private int getStudentMediumHash(StudentMediumHash student) {
        return student.hashCode();
    }
    /**
     * get student bad hashcode method
     * @param student instance
     * @return the hashcode method
     */
    private int getStudentBadHash(StudentBadHash student) {
        return student.hashCode();
    }
    /**
     * rehashing helper method to capacity * 2 next prime
     * called if load >= 0.75
     */

    private void rehash() {
        int newCapacity = nextPrime();
        //create list to store elements
        List<MapEntry<k, v>> list = this.entries();
        //clear table, set it up with a new capacity, and fill with null values
        this.clear();
        this.capacity = newCapacity;
        this.secondaryHashPrime = previousPrime(newCapacity);
        for (int i = 0; i < capacity; i++) {
            this.table.add(null);
        }
        //reinsert all elements into new hash
        for (MapEntry<k, v> entry : list) {
            if (entry != null) {
                this.put(entry.getKey(), entry.getValue());

            }
        }
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
    private void incrementCollisions()
    {
        if(collisions == Long.MAX_VALUE)
        {
            System.out.println("Collisions full");
        }
        else
        {
            collisions++;
        }
    }
    public long getCollisions()
    {
        return collisions;
    }
    public void resetCollisions()
    {
        collisions = 0;
    }
}
