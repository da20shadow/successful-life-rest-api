package com.successfulliferestapi.Goal.constants;

public class GoalMessages {
    public static class Error {
        public static final String ADD = "Unable to add new goal. Please try again.";
        public static final String UPDATE = "Unable to update goal. Please try again.";
        public static final String DELETE = "Unable to delete goal. Please try again.";
        public static final String DUPLICATE_TITLE = "A goal with the same title already exists.";
        public static final String NO_CHANGES = "There are no changes to save.";
    }

    public static class Success {
        public static final String ADD = "Successfully added a new goal.";
        public static final String UPDATE = "Successfully updated the goal.";
        public static final String DELETE = "Successfully deleted the goal.";
    }
}
