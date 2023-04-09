package com.successfulliferestapi.Target.constants;

public class TargetMessages {
    public static class Error {
        public static final String ADD = "Unable to add new target. Please try again.";
        public static final String UPDATE = "Unable to update target. Please try again.";
        public static final String DELETE = "Unable to delete target. Please try again.";
        public static final String DUPLICATE_TITLE = "A target with the same title already exists.";
        public static final String NO_CHANGES = "There are no changes to save.";
        public static final String NOT_FOUND = "Target not found!";
    }

    public static class Success {
        public static final String ADDED = "Successfully added a new target.";
        public static final String UPDATED = "Successfully updated the target.";
        public static final String UPDATED_TITLE = "Successfully updated target title!";
        public static final String UPDATED_DESCRIPTION = "Successfully updated target description!";
        public static final String DELETED = "Successfully deleted the target.";
    }
}
