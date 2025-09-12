package ego.storage;

import ego.task.TaskList;
import ego.task.ToDo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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

    @Test
    void loadTasks_mixedTypes_parsesAllAndPreservesOrder() throws Exception {
        // Arrange: write a realistic save-file with T/D/E lines
        File testFile = new File(tempDir, "test-tasks.txt");
        Storage s = new Storage(testFile.getAbsolutePath());
        String lines = String.join(System.lineSeparator(),
                "T | 0 | read book",
                "D | 1 | submit report | 2025-09-30",
                "E | 0 | hackathon | 2025-09-10 | 2025-09-12"
        );
        Files.writeString(testFile.toPath(), lines, StandardCharsets.UTF_8);

        // Act
        TaskList loaded = s.loadTasks();

        // Assert: size & basic content checks (avoid brittle exact formats)
        assertEquals(3, loaded.getSize());
        assertTrue(loaded.getTask(0).toString().toLowerCase().contains("read book"));
        assertTrue(loaded.getTask(1).toString().toLowerCase().contains("submit report"));
        assertTrue(loaded.getTask(2).toString().toLowerCase().contains("hackathon"));
    }

    @Test
    void saveTasks_overwritesExistingFile() throws Exception {
        // Arrange
        TaskList first = new TaskList(new ArrayList<>());
        first.addTask(new ToDo("first version"));
        storage.saveTasks(first);

        // Overwrite with a different set
        TaskList second = new TaskList(new ArrayList<>());
        second.addTask(new ToDo("second version"));
        storage.saveTasks(second);

        // Act
        TaskList loaded = storage.loadTasks();

        // Assert: only second set remains
        assertEquals(1, loaded.getSize());
        assertTrue(loaded.getTask(0).toString().toLowerCase().contains("second version"));
    }

}
