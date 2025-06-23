package assign06;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A simple text editor with undo and redo functionality.
 * @author Brian Keller & Wyatt Young
 * @version June 16, 2024
 */
public class TextEditor
{
    private Stack<Edit> undoStack;
    private Stack<Edit> redoStack;
    private StringBuilder text;
    /**
     * Constructs a new text editor with no previous edits and no undone edits.
     */
    public TextEditor()
    {
        this.undoStack = new LinkedListStack<>();
        this.redoStack = new LinkedListStack<>();
        this.text = new StringBuilder();
    }
    /**
     * Constructs a new text editor with a preloaded history of edits.
     *
     * @param history the history of edits
     */
    public TextEditor(SinglyLinkedList<Edit> history)
    {
        this.undoStack = new LinkedListStack<>();
        this.redoStack = new LinkedListStack<>();
        this.text = new StringBuilder();
        for(Edit edit : history)
        {
            edit.apply(text);
            undoStack.push(edit);
        }
    }
    /**
     * Inserts a character at the specified position.
     *
     * @param character the character to insert
     * @param position the position at which to insert the character
     */
    public void insert(char character, int position)
    {
        Edit insertEdit = new Edit(character,position, Edit.INSERT);
        insertEdit.apply(text);
        undoStack.push(insertEdit);
        redoStack.clear();
    }
    /**
     * Removes a character from the specified position.
     *
     * @param position the position from which to remove the character
     */
    public void remove(int position)
    {
        char removed = text.charAt(position);
        Edit removeEdit = new Edit(removed, position, Edit.REMOVE);
        removeEdit.apply(text);
        undoStack.push(removeEdit);
        redoStack.clear();
    }
    /**
     * Undoes the most recent edit.
     *
     * @throws NoSuchElementException if there are no edits to undo
     */
    public void undo() throws NoSuchElementException
    {
        if(undoStack.isEmpty()){throw new NoSuchElementException();}
        Edit undoEdit = undoStack.pop();
        undoEdit.revert(text);
        redoStack.push(undoEdit);
    }
    /**
     * Redoes the most recently undone edit.
     *
     * @throws NoSuchElementException if there are no edits to redo
     */
    public void redo() throws NoSuchElementException
    {
        if(redoStack.isEmpty()){throw new NoSuchElementException();}
        Edit redoEdit = redoStack.pop();
        redoEdit.apply(text);
        undoStack.push(redoEdit);
    }
    /**
     * Returns the history of edits made.
     *
     * @return a list of applied edits
     */
    public SinglyLinkedList<Edit> history()
    {
        SinglyLinkedList<Edit> history = new SinglyLinkedList<>();
        Stack<Edit> temp = new LinkedListStack<>();
        while(!undoStack.isEmpty())
        {
            temp.push(undoStack.pop());
        }
        while(!temp.isEmpty())
        {
            Edit edit = temp.pop();
            undoStack.push(edit);
            history.insert(history.size(), edit);
        }
        return history;
    }
    /**
     * Returns the current text.
     *
     * @return the current text
     */
    @Override
    public String toString()
    {
        return text.toString();
    }
}
