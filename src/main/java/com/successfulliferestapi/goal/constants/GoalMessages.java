package com.successfulliferestapi.Goal.constants;

public class GoalMessages {
    public static class Error {
        public static final String ADD = "Unable to add new goal. Please try again.";
        public static final String UPDATE = "Unable to update goal. Please try again.";
        public static final String DELETE = "Unable to delete goal. Please try again.";
        public static final String DUPLICATE_TITLE = "A goal with the same title already exists.";
        public static final String NO_CHANGES = "There are no changes to save.";
        public static final String NOT_FOUND = "Goal not found!";
    }

    public static class Success {
        public static final String ADD = "Successfully added a new goal.";
        public static final String UPDATED = "Successfully updated the goal.";
        public static final String UPDATED_TITLE = "Successfully updated goal title.";
        public static final String UPDATED_DESCRIPTION = "Successfully updated goal description.";
        public static final String UPDATED_DEADLINE = "Successfully updated goal deadline.";
        public static final String DELETED = "Successfully deleted the goal.";
        public static final String RECOVERED = "Successfully recovered a deleted goal!";
    }
}
