package com.successfulliferestapi.Task.constants;

public class TaskMessages {
    public static class Error {
        public static final String ADD = "Unable to add new task. Please try again.";
        public static final String UPDATE = "Unable to update task. Please try again.";
        public static final String DELETE = "Unable to delete task. Please try again.";
        public static final String DUPLICATE_TITLE = "A task with the same title already exists.";
        public static final String NO_CHANGES = "There are no changes to save.";
        public static final String NOT_FOUND = "Task not found!";
        public static final String DUPLICATE_CHECKLIST_ITEM_TITLE = "Checklist item title already added.";
        public static final String CHECKLIST_ITEM_NOT_FOUND = "Checklist Item not found.";
    }

    public static class Success {
        public static final String ADDED = "Successfully added a new task.";
        public static final String UPDATED = "Successfully updated the task.";
        public static final String DELETED = "Successfully deleted the task.";
        public static final String ADDED_CHECKLIST_ITEM = "Successfully added checklist item.";
        public static final String DELETED_CHECKLIST_ITEM = "Check list item deleted successfully.";
        public static final String UPDATED_CHECKLIST_ITEM = "Check list item updated successfully.";
    }
}
