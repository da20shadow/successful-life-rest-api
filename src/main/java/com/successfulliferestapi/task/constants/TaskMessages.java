package com.successfulliferestapi.Task.constants;

public class TaskMessages {
    public static class Error {
        public static final String ADD = "Unable to add new task. Please try again.";
        public static final String UPDATE = "Unable to update task. Please try again.";
        public static final String DELETE = "Unable to delete task. Please try again.";
        public static final String DUPLICATE_TITLE = "A task with the same title already exists.";
        public static final String NO_CHANGES = "There are no changes to save.";
    }

    public static class Success {
        public static final String ADD = "Successfully added a new task.";
        public static final String UPDATE = "Successfully updated the task.";
        public static final String DELETE = "Successfully deleted the task.";
    }
}
