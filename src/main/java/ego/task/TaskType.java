package ego.task;

/**
 * Represents the valid types of task available to the user.
 */
public enum TaskType {
    TODO, DEADLINE, EVENT, INVALID;

    /**
     * Returns the correct TaskType based on the user's command.
     * @param input The command given by the user to the chatbot.
     * @return The correct TaskType representing what category of task the user wish to track
     * in the task list.
     */
    public static TaskType fromString(String input) {
        if (input.startsWith("todo")) return TODO;
        if (input.startsWith("deadline")) return DEADLINE;
        if (input.startsWith("event")) return EVENT;
        return INVALID;
    }
}
