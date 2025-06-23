package assign09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {
    private HashTable<StudentMediumHash, Double> test;
    private HashTable<StudentMediumHash, Double> testLarge;

    private HashTable<String, Integer> testString;
    private HashTable<String, Integer> testSmall;

    @BeforeEach
    void setUp() {
        test = new HashTable<StudentMediumHash, Double>();
        testLarge = new HashTable<StudentMediumHash, Double>();
        testString = new HashTable<String, Integer>();
        testSmall = new HashTable<>();
        char firstName = 'A';
        char lastName = 'A';
        int uidBase = 1000000;
        //fill small array of studetns
        for (int i = 0; i < 10; i++) {
            int uid = uidBase + i;
            String first = String.valueOf(firstName);
            String last = String.valueOf(lastName);
            double gpa = 2.0 + (i % 3);
            StudentMediumHash student = new StudentMediumHash(uid, first, last);
            // Put the student and their GPA into the hash table
            test.put(student, gpa);
            // Increment the characters for names
            if (lastName < 'Z') {
                lastName++;
            } else {
                lastName = 'A';
                if (firstName < 'Z') {
                    firstName++;
                } else {
                    firstName = 'A';
                }
            }
        }
        for (MapEntry<StudentMediumHash, Double> e : test.entries()) {
            System.out.println("Student " + e.getKey() + " has GPA " + e.getValue() + ".");
        }
        //fill large table of students
        for (int i = 0; i < 76; i++) {
            int uid = uidBase + i;
            String first = String.valueOf(firstName);
            String last = String.valueOf(lastName);
            double gpa = 2.0 + (i % 3);
            StudentMediumHash student = new StudentMediumHash(uid, first, last);
            // Put the student and their GPA into the hash table
            testLarge.put(student, gpa);
            // Increment the characters for names
            if (lastName < 'Z') {
                lastName++;
            } else {
                lastName = 'A';
                if (firstName < 'Z') {
                    firstName++;
                } else {
                    firstName = 'A';
                }
            }
        }
        //fill string table
        char firstChar = 'A';
        char secondChar = 'A';
        int value = 1;
        for (int i = 0; i < 500; i++) {
            String key = "" + firstChar + secondChar;
            testString.put(key, value++);

            if (secondChar == 'Z') {
                firstChar++;
                secondChar = 'A';
            } else {
                secondChar++;
            }
        }
        char firstCharSmall = 'A';
        char secondCharSmall = 'A';
        int valueSmall = 1;
        for (int i = 0; i < 1; i++) {
            String key = "" + firstCharSmall + secondCharSmall;
            testSmall.put(key, valueSmall++);

            if (secondCharSmall == 'Z') {
                firstCharSmall++;
                secondCharSmall = 'A';
            } else {
                secondCharSmall++;
            }
        }
    }

    @Test
    void clear() {
        test.clear();
        assertEquals(0, test.size());
        assertTrue(test.isEmpty());
    }

    @Test
    void clearEmpty(){
        test = new HashTable<StudentMediumHash, Double>();
        assertEquals(0, test.size());
        assertTrue(test.isEmpty());
        test.clear();
        assertEquals(0, test.size());
        assertTrue(test.isEmpty());
    }

    @Test
    void containsKey() {
        StudentMediumHash testStudent = new StudentMediumHash(1000000, "A", "A");
        assertTrue(test.containsKey(testStudent));
    }

    @Test
    void containsKeyDoesNot(){
        StudentMediumHash testStudent = new StudentMediumHash(1000010, "A", "A");
        assertFalse(test.containsKey(testStudent));
    }

    @Test
    void containsKeyAfterRemoving(){
        StudentMediumHash testStudent = new StudentMediumHash(1000000, "A", "A");
        test.remove(testStudent);
        assertFalse(test.containsKey(testStudent));
    }

    @Test
    void containsKeyDuplicates(){
        StudentMediumHash testStudent = new StudentMediumHash(1000000, "A", "A");
        test.put(testStudent, 5.0);
        assertTrue(test.containsKey(testStudent));
    }

    @Test
    void containsKeyEmpty(){
        test = new HashTable<StudentMediumHash, Double>();
        StudentMediumHash testStudent = new StudentMediumHash(1000000, "A", "A");
        assertFalse(test.containsKey(testStudent));

    }

    @Test
    void containsKeyStringTable(){
        assertTrue(testString.containsKey("AA"));
    }

    @Test
    void containsValue() {
        assertTrue(test.containsValue(4.0));
    }

    @Test
    void containsValueDoesNot(){
        assertFalse(test.containsValue(5.0));
    }

    @Test
    void containsValueDeleted(){
        test.remove(new StudentMediumHash(1000000, "A","A"));
        test.remove(new StudentMediumHash(1000003, "A","D"));
        test.remove(new StudentMediumHash(1000006, "A","G"));
        assertTrue(test.containsValue(2.0));
        test.remove(new StudentMediumHash(1000009, "A","J"));
        assertFalse(test.containsValue(2.0));
    }

    @Test
    void containsStringTable(){
        assertTrue(testString.containsValue(1));
    }

    @Test
    void entries() {
        assertEquals(10, test.entries().size());
    }

    @Test
    void entriesRemoved(){
        test.remove(new StudentMediumHash(1000000, "A","A"));
        assertEquals(9, test.entries().size());
    }

    @Test
    void entriesStringTable(){
        assertEquals(500, testString.entries().size());
    }

    @Test
    void get() {
        double result = test.get(new StudentMediumHash(1000000, "A","A"));
        assertEquals(2.0, result);
    }

    @Test
    void getRemoved(){
        test.remove(new StudentMediumHash(1000000, "A","A"));
        assertNull(test.get(new StudentMediumHash(1000000, "A","A")));
    }

    @Test
    void getStringTable(){
        assertEquals(1, testString.get("AA"));
    }

    @Test
    void isEmptyRemoved() {
        test.remove(new StudentMediumHash(1000000, "A","A"));
        test.remove(new StudentMediumHash(1000001, "A","B"));
        test.remove(new StudentMediumHash(1000002, "A","C"));
        test.remove(new StudentMediumHash(1000003, "A","D"));
        test.remove(new StudentMediumHash(1000004, "A","E"));
        test.remove(new StudentMediumHash(1000007, "A","H"));
        test.remove(new StudentMediumHash(1000005, "A","F"));
        test.remove(new StudentMediumHash(1000008, "A","I"));
        test.remove(new StudentMediumHash(1000006, "A","G"));
        assertFalse(test.isEmpty());
        test.remove(new StudentMediumHash(1000009, "A","J"));
        assertTrue(test.isEmpty());

    }

    @Test
    void putSameHash() {
        StudentMediumHash testStudent = new StudentMediumHash(1000001, "A", "B");
        assertEquals(3.0, test.put(testStudent, 5.0));
        assertEquals(10, test.size());
        assertTrue(test.containsValue(5.0));
        assertTrue(test.containsKey(testStudent));
    }

    @Test
    void putResize(){
        assertEquals(101, testLarge.getCapacity());
        assertNull(testLarge.put(new StudentMediumHash(10000101, "A", "A"), 5.0));
        assertEquals(211, testLarge.getCapacity());
        assertEquals(77, testLarge.size());
    }

    @Test
    void putNull(){
        StudentMediumHash testStudent = new StudentMediumHash(1000100, "A", "A");
        test.put(testStudent , null);
        assertTrue(test.containsKey(testStudent));
        assertNull(test.get(testStudent));
    }

    @Test
        void putRemoved(){
            StudentMediumHash testStudent = new StudentMediumHash(1000100, "A","A");
            test.put(testStudent, 5.0);
            test.remove(testStudent);
            assertFalse(test.containsValue(5.0));
            test.put(testStudent, 7.0);
            assertTrue(test.containsValue(7.0));
        }

        @Test
        void putSameHashString() {
            assertEquals(1, testString.put("AA", 5));
            assertEquals(500, testString.size());
            assertTrue(testString.containsValue(5));
            assertTrue(testString.containsKey("AA"));
        }


        @Test
        void putNullString() {
            testString.put("ZZ", null);
            assertTrue(testString.containsKey("ZZ"));
            assertNull(testString.get("ZZ"));
        }

        @Test
        void putRemovedString() {
            testString.put("AA", 5);
            testString.remove("AA");
            testString.remove("AE");
            assertFalse(testString.containsValue(5));
            testString.put("AA", 7);
            assertTrue(testString.containsValue(7));
        }

        @Test
        void putReplace() {
            assertEquals(1, testString.get("AA"));
            assertEquals(1, testString.put("AA", 5));
            assertEquals(5, testString.get("AA"));
        }


        @Test
        void remove() {
            StudentMediumHash testStudent = new StudentMediumHash(1000000, "A","A");
            assertEquals(2.0, test.remove(testStudent));
        }

        @Test
        void removeNullValue(){
            StudentMediumHash testStudent = new StudentMediumHash(1000100, "A", "A");
            test.put(testStudent , null);
            assertNull(test.remove(testStudent));
        }
        @Test
        void removeString() {
            assertEquals(1, testString.remove("AA"));
        }

        @Test
        void removeNullValueString() {
            testString.put("ZZ", null);
            assertNull(testString.remove("ZZ"));
        }

        @Test
        void size()
        {
            assertEquals(test.entries().size(), test.size());
        }

        @Test
        void sizeAdjustingMethods(){
            assertEquals(10, test.size());
            StudentMediumHash testStudent = new StudentMediumHash(1000100, "A", "A");
            test.put(testStudent, 5.0);
            assertEquals(11, test.size());
            test.remove(testStudent);
            assertEquals(10, test.size());
            test.clear();
            assertEquals(0, test.size());
        }
        @Test
        void sizeString() {
            assertEquals(testString.entries().size(), testString.size());
        }

        @Test
        void sizeAdjustingMethodsString() {
            assertEquals(500, testString.size());
            testString.put("ZZ", 5);
            assertEquals(501, testString.size());
            testString.remove("ZZ");
            assertEquals(500, testString.size());
            testString.clear();
            assertEquals(0, testString.size());
        }

        @Test
        void smallTableOperations(){
        testSmall.put("AA", null);
        assertEquals(1, testSmall.size());
        assertTrue(testSmall.containsKey("AA"));
        assertNull(testSmall.remove("AA"));
    }

    @Test
    void removeOnlyItem(){
        assertEquals(1, testSmall.remove("AA"));
        assertEquals(0, testSmall.size());
        assertFalse(testSmall.containsKey("AA"));
        assertFalse(testSmall.containsValue(1));
    }
}