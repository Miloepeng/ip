package ego.command;

/**
 * Represents the valid commands available to the user.
 */
public enum CommandType {
    LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, BYE, INVALID, FIND, HELP;

    /**
     * Returns the correct CommandType based on the user's input.
     * @param input The whole command given by the user to the chatbot.
     * @return A CommandType representing what category of command the user has issued.
     */
    public static CommandType fromString(String input) {
        if (input == null || input.isEmpty()) {
            return INVALID;
        }

        if (input.equals("list")) return LIST;
        if (input.startsWith("mark")) return MARK;
        if (input.startsWith("unmark")) return UNMARK;
        if (input.startsWith("todo")) return TODO;
        if (input.startsWith("deadline")) return DEADLINE;
        if (input.startsWith("event")) return EVENT;
        if (input.startsWith("delete")) return DELETE;
        if (input.equals("bye")) return BYE;
        if (input.startsWith("find")) return FIND;
        if (input.equals("help")) return HELP;

        return INVALID;
    }
}
