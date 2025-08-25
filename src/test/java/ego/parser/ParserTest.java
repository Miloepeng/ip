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
}
