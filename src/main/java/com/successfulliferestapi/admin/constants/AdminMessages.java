package com.successfulliferestapi.Admin.constants;

public class AdminMessages {
    public static class Error {
        public static final String ADD_USER = "Unable to add new user. Please try again.";
        public static final String UPDATE_USER = "Unable to update user. Please try again.";
        public static final String DELETE_USER = "Unable to delete user. Please try again.";
        public static final String DUPLICATE_USERNAME = "A user with the same username already exists.";
        public static final String NO_CHANGES = "There are no changes to save.";
    }

    public static class Success {
        public static final String ADDED_USER = "Successfully added a new user.";
        public static final String UPDATED_USER = "Successfully updated the user.";
        public static final String DELETED_USER = "Successfully deleted the user.";
        public static final String BANNED_USER = "Your account is banned!";
    }
}
