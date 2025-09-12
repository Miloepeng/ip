package ego.parser;

import ego.command.CommandType;
import ego.exception.EgoException;
import ego.storage.Storage;
import ego.task.Deadline;
import ego.task.Event;
import ego.task.Task;
import ego.task.TaskList;
import ego.task.TaskType;
import ego.task.ToDo;

import java.util.ArrayList;

/**
 * Deals with making sense of the user command and executing it accordingly.
 */
public class Parser {
    private TaskList tasks;
    private Storage storage;

    public Parser(TaskList tasks, Storage storage) {
        this.tasks = tasks;
        this.storage = storage;
    }

    /**
     * Returns the corresponding result that should be displayed to the user by
     * parsing the users' command and calling the correct method.
     * @param input Input command given by the user to the chatbot.
     * @return String result of what is to be displayed to user.
     * @throws EgoException If the command given by user is invalid due to issues such as
     * formatting.
     */
    public String parseCommand(String input) throws EgoException {
        CommandType command = CommandType.fromString(input);
        switch (command) {
        case LIST:
            return listTasks();

        case MARK:
            int markNum = Integer.parseInt(input.substring(5));
            return markTask(markNum);

        case UNMARK:
            int unmarkNum = Integer.parseInt(input.substring(7));
            return unmarkTask(unmarkNum);

        case TODO:
            //Fallthrough
        case DEADLINE:
            //Fallthrough
        case EVENT:
            //Fallthrough
            return addTask(input);

        case DELETE:
            int delNum = Integer.parseInt(input.substring(7));
            return deleteTask(delNum);

        case BYE:
            this.storage.saveTasks(this.tasks);
            return "Farewell... see you soon";

        case INVALID:
            return "Sorry! " + input + " is a invalid command. Try something else?";

        case FIND:
            String key = input.substring(5).trim();
            return findTask(key);

        case HELP:
            return getHelpMessage();

        default:
            return "";
        }
    }

    /**
     * Returns the current tasks the user is currently tracking.
     * @return A String result of the user's task list, numbered by order in which they were added.
     */
    public String listTasks() {
        String msg = "OK egoist, ready to rock your to-do list?\n";
        msg += this.tasks;
        return msg;
    }

    /**
     * Returns the current tasks the user is tracking after marking the selected task
     * as being completed.
     * @param taskNum The index of the task the user wish to mark as completed.
     * @return A String displayed to the user informing them that the task has been successfully
     * marked as completed.
     * @throws EgoException If the index of the task the user inputs is not within the valid range.
     */
    public String markTask(int taskNum) throws EgoException {
        validateTaskIndex(taskNum);

        String msg = "Well done egoist, I've marked this task as completed:\n  ";
        this.tasks.getTask(taskNum - 1).doTask();
        msg += this.tasks.getTask(taskNum - 1);

        return msg;
    }

    /**
     * Returns the current tasks the user is tracking after marking the selected task as
     * incomplete.
     * @param taskNum The index of the task the user wish to mark as completed.
     * @return  String displayed to the user informing them that the task has been successfully
     * marked as incomplete.
     * @throws EgoException If the index of the task the user inputs is not within the valid range.
     */
    public String unmarkTask(int taskNum) throws EgoException {
        validateTaskIndex(taskNum);

        String msg = "Alright... I'll mark this task as not done yet...\n  ";
        this.tasks.getTask(taskNum - 1).undoTask();
        msg += this.tasks.getTask(taskNum - 1);

        return msg;
    }

    /**
     * Returns the task to be added after successfully adding it into the user's task list.
     * @param input The full command by the user which includes information about the tasks
     * such as description and deadlines.
     * @return String to be displayed to the user confirming the addition of the task the user
     * wish to add in their task list.
     * @throws EgoException If the task to be added has an invalid format.
     */
    public String addTask(String input) throws EgoException {
        Task newTask;
        TaskType type = TaskType.fromString(input);

        switch (type) {
        case TODO:
            newTask = createToDoTask(input);
            break;

        case DEADLINE:
            newTask = createDeadlineTask(input);
            break;

        case EVENT:
            newTask = createEventTask(input);
            break;

        default:
            throw new EgoException("Unknown task type: " + type);
        }

        assert newTask != null : "Command execution must always add a task";
        this.tasks.addTask(newTask);
        String msg = "Added: " + newTask + "\n";
        msg += "Now you have " + this.tasks.getSize() + " tasks to complete!";
        return msg;
    }

    /**
     * Creates a new ToDo task using the user input.
     * @param input The full command from the user with the user's task description.
     * @return A ToDo task.
     * @throws EgoException If the description is empty.
     */
    private Task createToDoTask(String input) throws EgoException {
        String todo = input.substring(4).trim();
        if (todo.isEmpty()) {
            throw new EgoException("Hey... better take a closer look! " +
                    "Your task description can't be empty egoist.");
        }
        return new ToDo(todo);
    }

    /**
     * Creates a new Deadline task using the user input.
     * @param input The full command from the user with the user's task description
     *              and deadline.
     * @return A Deadline task.
     * @throws EgoException If the format is invalid.
     */
    private Task createDeadlineTask(String input) throws EgoException {
        String deadline = input.substring(8).trim();
        if (deadline.isEmpty()) {
            throw new EgoException("Hey... better take a closer look! "
                    + "Your task description can't be empty egoist!");
        }
        if (!deadline.contains("/by")) {
            throw new EgoException("Hey... Your command should be in the format: "
                    + "deadline <description> /by <end date>!");
        }

        String[] splitString = deadline.split("/by ");
        if (splitString.length < 2 || splitString[0].isEmpty() || splitString[1].trim().isEmpty()) {
            throw new EgoException("Hey... remind me what's the deadline again? "
                    + "Your command should be in the format: deadline <description> /by <end date>!");
        }
        String description = splitString[0].trim();
        String dueDate = splitString[1].trim();
        return new Deadline(description, dueDate);
    }

    /**
     * Creates a new Event task using the user input.
     * @param input The full command from the user with the user's task
     *              description, start date and end date.
     * @return An Event task.
     * @throws EgoException If the format is invalid.
     */
    private Task createEventTask(String input) throws EgoException {
        String event = input.substring(5).trim();
        if (event.isEmpty()) {
            throw new EgoException("Hey... better take a closer look! "
                    + "Your task description can't be empty egoist!");
        }
        if (!event.contains("/from") || !event.contains("/to")) {
            throw new EgoException("Hey... Your command should be in the format: "
                    + "event <description> /from <start date> /to <end date>!");
        }

        String[] splitDesc = event.split("/from ");
        if (splitDesc.length < 2 || splitDesc[0].isEmpty()) {
            throw new EgoException("Hey... better take a closer look! "
                    + "Your task description can't be empty egoist!");
        }
        String description = splitDesc[0].trim();

        String[] splitDates = splitDesc[1].split("/to ");
        if (splitDates.length < 2 || splitDates[0].isEmpty() || splitDates[1].isEmpty()) {
            throw new EgoException("Hey... start and end dates must be provided! "
                    + "Format: event <description> /from <start date> /to <end date>.");
        }
        String startDate = splitDates[0].trim();
        String endDate = splitDates[1].trim();
        return new Event(description, startDate, endDate);
    }

    /**
     * Returns the String displaying the result of the selected task being deleted from the user's
     * task list.
     * @param taskNum The index of the task deleted from the task list.
     * @return A String displaying the task that has been removed.
     * @throws EgoException If the index of the task to be deleted is invalid.
     */
    public String deleteTask(int taskNum) throws EgoException {
        validateTaskIndex(taskNum);

        String msg = "Roger, I'll delete this task from your list!\n  ";
        Task deletedTask = this.tasks.removeTask(taskNum - 1);
        assert deletedTask != null : "Deleted task cannot be null";
        msg += deletedTask + "\n";
        msg += "Now you have " + this.tasks.getSize() + " tasks to complete!";

        return msg;
    }

    /**
     * Finds tasks that contains the given keyword in their String representation.
     * @param keyword The search keyword.
     * @return A String listing matching tasks.
     * @throws EgoException
     */
    public String findTask(String keyword) throws EgoException {
        TaskList result = searchTasks(keyword);
        return "Here are the relevant tasks you asked for:\n" + result;
    }

    /**
     * Filters tasks by keyword.
     * @param keyword The search keyword.
     * @return A TaskList of tasks that match the keyword.
     */
    private TaskList searchTasks(String keyword) {
        TaskList result = new TaskList(new ArrayList<>());
        for (Task task : this.tasks.getTasks()) {
            if (task.toString().contains(keyword)) {
                result.addTask(task);
            }
        }
      
        assert result != null : "Command execution must always return a result";
        return result;
    }

    /**
     * Validates that the given task number is within bounds.
     * @param taskNum Index of the task.
     * @throws EgoException If the index is invalid.
     */
    private void validateTaskIndex(int taskNum) throws EgoException {
        if (taskNum <= 0 || taskNum > this.tasks.getSize()) {
            throw new EgoException("Wow! Please input a number from 1 to " + this.tasks.getSize());
        }
    }

    /**
     * Returns a help message to list all commands of Ego.
     * @return A string containing a list of commands for Ego.
     */
    public String getHelpMessage() {
        return """
            Hello there diamonds in the rough, feeling lost?
            Here are the list of commands you can use:
            - help
                Show list of commands
            - list
                Show current task you have
            - mark <taskNum>
                Mark a task as completed
            - unmark <taskNum>
                Mark a task as not done
            - todo <desc>
                Add a todo task with description
            - deadline <desc> /by <deadline>
                Add a task with a deadline
            - event <desc> /from <start> /to <end>
                Add an event with start and end date
            - delete <taskNum>
                Delete a task
            - find <keyword>
                Find tasks by keyword
            - bye
                Exit and save tasks
            """;
    }

}
