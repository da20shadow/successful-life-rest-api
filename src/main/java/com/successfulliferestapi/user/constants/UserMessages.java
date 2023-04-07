package com.successfulliferestapi.user.constants;

public class UserMessages {
    public static class Error {
        public static final String LOGIN = "Bad credentials.";
        public static final String REGISTER = "Error occurred while registering. Please, try again.";
        public static final String INVALID_PASSWORD = "Invalid password.";
        public static final String INVALID_EMAIL = "Invalid email format.";
        public static final String EMAIL_ALREADY_EXIST = "A user with the same email already exists.";
        public static final String NO_CHANGES = "There are no changes to save.";
        public static final String DELETE = "Unable to delete account. Please try again or contact us.";
    }

    public static class Success {
        public static final String LOGIN = "Successfully logged in.";
        public static final String REGISTER = "Successfully registered.";
        public static final String UPDATE = "Successfully updated the profile.";
        public static final String DELETE = "Successfully deleted account.";
    }
}
