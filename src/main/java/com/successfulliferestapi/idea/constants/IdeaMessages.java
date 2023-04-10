package com.successfulliferestapi.Idea.constants;

public class IdeaMessages {
    public static class Error {
        public static final String ADD = "Unable to add new idea. Please try again.";
        public static final String UPDATE = "Unable to update idea. Please try again.";
        public static final String DELETE = "Unable to delete idea. Please try again.";
        public static final String DUPLICATE_TITLE = "A idea with the same title already exists.";
        public static final String NO_CHANGES = "There are no changes to save.";
        public static final String NOT_FOUND = "Idea not found!";
        public static final String NO_IDEAS_YET = "There are no ideas yet.";
        public static final String NO_IDEAS_BY_TAG = "There are no ideas with this tag.";
    }

    public static class Success {
        public static final String ADDED = "Successfully added a new idea.";
        public static final String UPDATED = "Successfully updated the idea.";
        public static final String DELETED = "Successfully deleted the idea.";
    }
}
