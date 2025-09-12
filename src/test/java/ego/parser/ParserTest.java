package ego.parser;

import ego.exception.EgoException;
import ego.storage.Storage;
import ego.task.Event;
import ego.task.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    private TaskList tasks;
    private Parser parser;

    @BeforeEach
    void setUp() {
        File file = new File("./data/test-tasks.txt");
        if (file.exists()) {
            file.delete();
        }
        Storage storage = new Storage("./data/test-tasks.txt");
        tasks = new TaskList(new ArrayList<>());
        parser = new Parser(tasks, storage);
    }

    @Test
    void addTask_validEvent_addsCorrectly() throws EgoException {
        String response = parser.addTask("event borrow book /from 2025-08-26 /to 2025-08-29");
        assertEquals(1, tasks.getSize());
        assertTrue(response.contains("borrow book"));
        assertTrue(tasks.getTask(0) instanceof Event);
    }

    @Test
    void addTask_missingDescription_throwsException() throws EgoException {
        assertThrows(EgoException.class,
                () -> parser.addTask("event"));
    }

    @Test
    void addTask_missingFromDate_throwsException() throws EgoException {
        assertThrows(EgoException.class,
                () -> parser.addTask("event borrow book /from"));
    }

    @Test
    void addTask_missingEndDate_throwsException() throws EgoException {
        assertThrows(EgoException.class,
                () -> parser.addTask("event borrow book /from 2025-08-26 /to"));
    }

    @Test
    void addTask_invalidDateFormat_throwsException() throws EgoException {
        assertThrows(EgoException.class,
                () -> parser.addTask("event borrow book /from 2025-08-26 /to 2022088"));
    }

    @Test
    void markTask_validIndex_marksDone() throws EgoException {
        parser.addTask("todo borrow book");
        String response = parser.markTask(1);
        assertTrue(response.contains("task as completed"));
    }

    @Test
    void markTask_invalidIndex_throwsException() throws EgoException{
        assertThrows(EgoException.class,
                () -> parser.markTask(1));
    }

    @Test
    void addTask_validTodo_addsCorrectly() throws EgoException {
        String response = parser.addTask("todo read book");
        assertEquals(1, tasks.getSize());
        assertTrue(response.toLowerCase().contains("added"));
        assertTrue(response.toLowerCase().contains("read book"));
    }

    @Test
    void addTask_validDeadline_addsCorrectly() throws EgoException {
        String response = parser.addTask("deadline submit report /by 2025-10-01");
        assertEquals(1, tasks.getSize());
        assertTrue(response.toLowerCase().contains("submit report"));
        // do not assert exact formatting; just ensure stored text is present
    }

    @Test
    void addTask_todoMissingDescription_throwsException() {
        assertThrows(EgoException.class, () -> parser.addTask("todo    "));
    }

    @Test
    void deleteTask_validIndex_removesTask() throws EgoException {
        parser.addTask("todo alpha");
        parser.addTask("todo beta");
        assertEquals(2, tasks.getSize());

        String response = parser.parseCommand("delete 1");
        assertTrue(response.toLowerCase().contains("delete"));
        assertEquals(1, tasks.getSize());
        // remaining item should be "beta"
        assertTrue(tasks.getTask(0).toString().toLowerCase().contains("beta"));
    }

    @Test
    void deleteTask_invalidIndex_throwsException() {
        assertThrows(EgoException.class, () -> parser.parseCommand("delete 1"));
    }

    @Test
    void unmarkTask_validIndex_marksUndone() throws EgoException {
        parser.addTask("todo borrow book");
        parser.markTask(1);
        String response = parser.unmarkTask(1);
        assertTrue(response.toLowerCase().contains("not done yet"));
    }

    @Test
    void listTasks_includesAllDescriptions() throws EgoException {
        parser.addTask("todo buy milk");
        parser.addTask("deadline return textbook /by 2025-10-03");
        String list = parser.listTasks();
        assertTrue(list.toLowerCase().contains("buy milk"));
        assertTrue(list.toLowerCase().contains("return textbook"));
    }

    @Test
    void findTask_positiveMatch_returnsOnlyMatches() throws EgoException {
        parser.addTask("todo borrow book");
        parser.addTask("todo watch movie");

        String result = parser.parseCommand("find book");
        assertTrue(result.toLowerCase().contains("borrow book"));
        assertFalse(result.toLowerCase().contains("watch movie"));
    }

    @Test
    void findTask_noMatches_returnsHeaderOnly() throws EgoException {
        parser.addTask("todo borrow book");
        String result = parser.parseCommand("find movie");
        assertTrue(result.toLowerCase().contains("relevant tasks"));
        assertFalse(result.toLowerCase().contains("borrow book"));
    }

    @Test
    void parseCommand_invalidCommand_returnsHelpfulError() throws EgoException {
        String result = parser.parseCommand("dance 123");
        assertTrue(result.toLowerCase().contains("invalid command"));
    }

    @Test
    void help_containsKeyCommands() throws EgoException {
        String help = parser.parseCommand("help");
        // spot check a few lines so we don't couple to exact formatting
        assertTrue(help.contains("- list"));
        assertTrue(help.contains("- todo <desc>"));
        assertTrue(help.contains("- deadline <desc> /by <deadline>"));
        assertTrue(help.contains("- event <desc> /from <start> /to <end>"));
        assertTrue(help.contains("- delete <taskNum>"));
        assertTrue(help.contains("- bye"));
    }

    @Test
    void bye_returnsFarewellMessage_withoutCrashing() throws EgoException {
        parser.addTask("todo wave goodbye");
        String msg = parser.parseCommand("bye");
        assertTrue(msg.toLowerCase().contains("farewell"));
        // Storage side-effects (file persistence) are covered in StorageTest.
    }
}
