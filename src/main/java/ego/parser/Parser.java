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

        }
        return "";
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
        if (taskNum <= 0 || taskNum > this.tasks.getSize()) {
            throw new EgoException("Wow! Please input a number from 1 to " + this.tasks.getSize());
        }
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
        if (taskNum <= 0 || taskNum > this.tasks.getSize()) {
            throw new EgoException("Wow! Please input a number from 1 to " + this.tasks.getSize());
        }
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
     * @throws EgoException If the task to be added has a invalid format.
     */
    public String addTask(String input) throws EgoException {
        Task newTask = null;
        TaskType type = TaskType.fromString(input);

        switch (type) {
        case TODO:
            String todo = input.substring(4).trim();
            if (todo.isEmpty()) {
                throw new EgoException("Hey... better take a closer look! " +
                        "Your task description can't be empty egoist.");
            }
            newTask = new ToDo(todo);
            break;

        case DEADLINE:
            String deadline = input.substring(8).trim();
            if (deadline.isEmpty()) {
                throw new EgoException("Hey... better take a closer look! " +
                        "Your task description can't be empty egoist!");
            }
            if (!deadline.contains("/by")) {
                throw new EgoException("Hey... Your command should be in the format: deadline <description>" +
                        " /by <end date>!");
            }
            String[] splitted = deadline.split("/by ");
            if (splitted.length < 1 || splitted[0].isEmpty()) {
                throw new EgoException("Hey... better take a closer look! " +
                        "Your task description can't be empty egoist!");
            }
            if (splitted.length < 2 || splitted[1].trim().isEmpty()) {
                throw new EgoException("Hey... remind me what's the deadline again? " +
                        "Your command should be in the format deadline <description>" +
                        " /by <end date>!");
            }
            String dueDate = splitted[1].trim();
            newTask = new Deadline(splitted[0], dueDate);
            break;

        case EVENT:
            String event = input.substring(5).trim();
            if (event.isEmpty()) {
                throw new EgoException("Hey... better take a closer look! " +
                        "Your task description can't be empty egoist!");
            }
            if (!event.contains("/from")) {
                throw new EgoException("Hey... where is your /from! " +
                        "Your command should be in the format: deadline <description>" +
                        " /from <start date> /to <end date>!");
            }
            if (!event.contains("/to")) {
                throw new EgoException("Hey... where is your /to! " +
                        "Your command should be in the format: deadline <description>" +
                        " /from <start date> /to <end date>!");
            }
            String[] splitEvent = event.split("/from ");
            event = splitEvent[0];
            if (splitEvent[0].isEmpty()) {
                throw new EgoException("Hey... better take a closer look! " +
                        "Your task description can't be empty egoist!");
            }
            splitEvent = splitEvent[1].split("/to ");
            if (splitEvent[0].isEmpty()) {
                throw new EgoException("Hey... start date must be provided!" +
                        " Your command should be in the format: deadline <description> " +
                        "/from <start date> /to <end date>.");
            }
            if (splitEvent.length < 2 || splitEvent[1].isEmpty()) {
                throw new EgoException("Hey... end date must be provided!" +
                        " Your command should be in the format: deadline <description> " +
                        "/from <start date> /to <end date>.");
            }
            String startDate = splitEvent[0].trim();
            String endDate = splitEvent[1].trim();
            newTask = new Event(event, startDate, endDate);
            break;

        }

        this.tasks.addTask(newTask);
        String msg = "Added: " + newTask + "\n";
        msg += "Now you have " + this.tasks.getSize() + " tasks to complete!";
        return msg;
    }

    /**
     * Returns the String displaying the result of the selected task being deleted from the user's
     * task list.
     * @param taskNum The index of the task deleted from the task list.
     * @return A String displaying the task that has been removed.
     * @throws EgoException If the index of the task to be deleted is invalid.
     */
    public String deleteTask(int taskNum) throws EgoException {
        if (taskNum <= 0 || taskNum > this.tasks.getSize()) {
            throw new EgoException("Wow! Please input a number from 1 to " + this.tasks.getSize());
        }
        String msg = "Roger, I'll delete this task from your list!\n  ";
        Task deletedTask = this.tasks.removeTask(taskNum - 1);
        msg += deletedTask + "\n";
        msg += "Now you have " + this.tasks.getSize() + " tasks to complete!";
        return msg;
    }
}
