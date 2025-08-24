public enum TaskType {
    TODO, DEADLINE, EVENT, INVALID;

    public static TaskType fromString(String input) {
        if (input.startsWith("todo")) return TODO;
        if (input.startsWith("deadline")) return DEADLINE;
        if (input.startsWith("event")) return EVENT;
        return INVALID;
    }
}
