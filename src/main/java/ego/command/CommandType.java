package ego.command;

public enum CommandType {
    LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, BYE, INVALID, FIND;

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

        return INVALID;
    }
}
