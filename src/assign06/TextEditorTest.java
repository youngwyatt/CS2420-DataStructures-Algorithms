package assign06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TextEditorTest {
    private TextEditor editor;
    @BeforeEach
    void setUp()
    {
        editor = new TextEditor();
    }
    @Test
    void insert()
    {
        editor.insert('a',0);
        editor.insert('b',1);
        editor.insert('c',2);
        editor.insert('d',3);
        editor.insert('e',4);
        assertEquals(editor.toString(),"abcde");
        editor.remove(2);
        assertEquals(editor.toString(),"abde");
    }
    @Test
    void insertCase()
    {
        editor.insert('A',0);
        editor.insert('b',1);
        editor.insert('C',2);
        editor.insert('d',3);
        editor.insert('E',4);
        assertEquals(editor.toString(),"AbCdE");
    }
    @Test
    void insertOOB()
    {
        editor.insert('a',0);
        editor.insert('b',1);
        editor.insert('c',2);
        assertThrows(StringIndexOutOfBoundsException.class, () -> {editor.insert('d',4);});
    }
    @Test
    void remove()
    {
        editor.insert('A',0);
        editor.insert('b',1);
        editor.insert('C',2);
        editor.insert('d',3);
        editor.insert('E',4);
        editor.remove(0);
        assertEquals(editor.toString(),"bCdE");
        editor.remove(2);
        assertEquals(editor.toString(),"bCE");
    }
    @Test
    void removeOOB()
    {
        editor.insert('A',0);
        editor.insert('b',1);
        editor.insert('C',2);
        editor.insert('d',3);
        editor.insert('E',4);
        editor.remove(0);
        assertEquals(editor.toString(),"bCdE");
        assertThrows(StringIndexOutOfBoundsException.class, () -> {editor.remove(4);});
    }
    @Test
    void undoAll()
    {
        editor.insert('a',0);
        editor.insert('b',1);
        editor.insert('c',2);
        editor.insert('d',3);
        editor.insert('e',4);
        editor.undo();
        assertEquals(editor.toString(), "abcd");
        editor.undo();
        assertEquals(editor.toString(),"abc");
        editor.undo();
        editor.undo();
        editor.undo();
        assertEquals(editor.toString(),"");
    }
    @Test
    void undoException()
    {
        editor.insert('a',0);
        editor.insert('b',1);
        editor.insert('c',2);
        editor.insert('d',3);
        editor.insert('e',4);
        editor.undo();
        assertEquals(editor.toString(), "abcd");
        editor.undo();
        assertEquals(editor.toString(),"abc");
        editor.undo();
        editor.undo();
        editor.undo();
        assertEquals(editor.toString(),"");
        assertThrows(NoSuchElementException.class, () -> {editor.undo();});
    }
    @Test
    void undoWithRedos()
    {
        editor.insert('a',0);
        editor.insert('b',1);
        editor.insert('c',2);
        editor.undo();
        assertEquals(editor.toString(), "ab");
        editor.redo();
        assertEquals(editor.toString(),"abc");
        editor.undo();
        editor.undo();
        assertEquals(editor.toString(),"a");
    }
    @Test
    void redo()
    {
        editor.insert('a', 0);
        editor.insert('b', 1);
        editor.insert('c', 2);
        editor.undo();
        editor.undo();
        editor.redo();
        assertEquals("ab", editor.toString());
        editor.redo();
        assertEquals("abc", editor.toString());
    }
    @Test
    public void testUndoRedoInsertMixed() {
        editor.insert('a', 0);
        editor.insert('b', 1);
        editor.insert('c', 2);
        editor.undo();
        assertEquals(editor.toString(), "ab");
        editor.redo();
        assertEquals(editor.toString(),"abc");
        editor.insert('d', 3);
        assertEquals(editor.toString(), "abcd");
        editor.undo();
        assertEquals(editor.toString(), "abc");
    }
    @Test
    void redoException()
    {
        editor.insert('a',0);
        editor.insert('b',1);
        editor.insert('c',2);
        editor.undo();
        editor.undo();
        assertEquals(editor.toString(),"a");
        editor.undo();
        assertEquals(editor.toString(), "");
        editor.redo();
        editor.redo();
        editor.redo();
        assertEquals(editor.toString(),"abc");
        assertThrows(NoSuchElementException.class, () -> {editor.redo();});
    }
    @Test
    void History()
    {
        editor.insert('a', 0);
        editor.insert('b', 1);
        editor.insert('c', 2);
        editor.remove(1);
        SinglyLinkedList<Edit> history = editor.history();
        Edit edit1 = new Edit('a', 0, Edit.INSERT);
        Edit edit2 = new Edit('b', 1, Edit.INSERT);
        Edit edit3 = new Edit('c', 2, Edit.INSERT);
        Edit edit4 = new Edit('b', 1, Edit.REMOVE);
        assertEquals(4, history.size());
        assertEquals(edit1, history.get(0));
        assertEquals(edit2, history.get(1));
        assertEquals(edit3, history.get(2));
        assertEquals(edit4, history.get(3));
    }
    @Test
    public void HistoryRedoUndo() {
        editor.insert('a', 0);
        editor.insert('b', 1);
        editor.insert('c', 2);
        editor.insert('d', 3);
        editor.insert('e', 4);
        editor.undo();
        editor.undo();
        editor.redo();
        SinglyLinkedList<Edit> history = editor.history();
        Edit edit1 = new Edit('a', 0, Edit.INSERT);
        Edit edit2 = new Edit('b', 1, Edit.INSERT);
        Edit edit3 = new Edit('c', 2, Edit.INSERT);
        Edit edit4 = new Edit('d', 3, Edit.INSERT);
        assertEquals(4, history.size());
        assertEquals(edit1, history.get(0));
        assertEquals(edit2, history.get(1));
        assertEquals(edit3, history.get(2));
        assertEquals(edit4, history.get(3));
    }
}