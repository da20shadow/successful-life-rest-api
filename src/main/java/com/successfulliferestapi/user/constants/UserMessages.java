package com.successfulliferestapi.User.constants;

public class UserMessages {
    public static class Error {
        public static final String LOGIN = "Bad credentials.";
        public static final String REGISTRATION = "Error occurred while registering. Please, try again.";
        public static final String INVALID_PASSWORD = "Invalid password.";
        public static final String INVALID_EMAIL = "Invalid email format.";
        public static final String EMAIL_ALREADY_EXIST = "A user with the same email already exists.";
        public static final String NO_CHANGES = "There are no changes to save.";
        public static final String DELETE = "Unable to delete account. Please try again or contact us.";
        public static final String NOT_FOUND = "User not found.";
        public static final String SAME_USERNAME = "Username is the same!";
        public static final String USERNAME_ALREADY_TAKEN = "This username is already taken!";
        public static final String BANNED = "Your account is banned!";
    }

    public static class Success {
        public static final String LOGIN = "Successfully logged in.";
        public static final String REGISTRATION = "Successfully registered.";
        public static final String UPDATE = "Successfully updated the profile.";
        public static final String UPDATE_EMAIL = "Successfully changed email.";
        public static final String UPDATE_USERNAME = "Successfully changed username.";
        public static final String UPDATE_PASSWORD = "Successfully changed password.";
        public static final String UPDATE_NAMES = "Successfully changed names.";
        public static final String DELETE = "Successfully deleted account.";
    }
}
