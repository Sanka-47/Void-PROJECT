package model;

/**
 *
 * @author Rushma
 */
public class Task {
    private String taskId;
    private String description;
    private boolean completed;

    // Constructor for incomplete tasks
    public Task(String taskId, String description) {
        this.taskId = taskId;
        this.description = description;
        this.completed = false;
    }

    // Constructor for specifying all fields
    public Task(String taskId, String description, boolean completed) {
        this.taskId = taskId;
        this.description = description;
        this.completed = completed;
    }

    // Getters and setters
    public String getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Utility method to convert boolean to integer
    public int getCompletedAsInt() {
        return this.completed ? 1 : 0;
    }

    // Utility method to set completion status from integer
    public void setCompletedFromInt(int completedInt) {
        this.completed = completedInt == 1;
    }
}
