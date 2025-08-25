package ego.storage;

import ego.task.TaskList;
import ego.task.ToDo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {
    private Storage storage;

    @TempDir
    File tempDir;

    @BeforeEach
    void setUp() {
        File testFile = new File(tempDir, "test-tasks.txt");
        this.storage = new Storage(testFile.getAbsolutePath());
    }

    @Test
    void saveTasks_nonEmptyList_savesToFile() {
        TaskList tasks = new TaskList(new ArrayList<>());
        ToDo firstTodo = new ToDo("borrow book");
        ToDo secondTodo = new ToDo("return book");
        tasks.addTask(firstTodo);
        tasks.addTask(secondTodo);
        this.storage.saveTasks(tasks);
        assertTrue(this.storage.loadTasks().getTask(0).toString().contains("borrow"));
        assertTrue(this.storage.loadTasks().getTask(1).toString().contains("return"));
    }

    @Test
    void saveTasks_emptyList_createsEmptyFile() {
        TaskList tasks = new TaskList(new ArrayList<>());
        this.storage.saveTasks(tasks);
        assertEquals(0, this.storage.loadTasks().getSize());
    }

    @Test
    void loadTasks_existingFile_loadsCorrectly() {
        TaskList tasks = new TaskList(new ArrayList<>());
        tasks.addTask(new ToDo("borrow book"));
        tasks.addTask(new ToDo("return book"));
        this.storage.saveTasks(tasks);

        TaskList loaded = this.storage.loadTasks();

        assertTrue(loaded.getTask(0).toString().contains("borrow"));
        assertTrue(loaded.getTask(1).toString().contains("return"));
    }

    @Test
    void loadTasks_nonExistentFile_returnsEmptyList() {
        TaskList loaded = this.storage.loadTasks();

        assertEquals(0, loaded.getSize());
    }

    @Test
    void loadTasks_corruptedFile_throwsException() throws Exception {
        File file = new File("./data/corrupted-test.txt");
        if (file.exists()) file.delete();
        storage = new Storage(file.getAbsolutePath());
        java.nio.file.Files.writeString(file.toPath(), "not a valid task line");

        assertThrows(IllegalStateException.class, () -> storage.loadTasks());

        file.delete();
    }
}
