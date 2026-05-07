package task9_1P.task;

import java.time.LocalDate;

/**
 * Represents a student task in OnTrack with a deadline.
 */
public class Task {

    public enum Status {
        NOT_STARTED,
        IN_PROGRESS,
        SUBMITTED,
        OVERDUE
    }

    private String taskId;
    private String taskName;
    private String studentId;
    private LocalDate deadline;
    private Status status;

    public Task(String taskId, String taskName, String studentId, LocalDate deadline) {
        if (taskId == null || taskId.isEmpty()) {
            throw new IllegalArgumentException("Task ID cannot be null or empty.");
        }
        if (taskName == null || taskName.isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be null or empty.");
        }
        if (studentId == null || studentId.isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }
        if (deadline == null) {
            throw new IllegalArgumentException("Deadline cannot be null.");
        }
        this.taskId = taskId;
        this.taskName = taskName;
        this.studentId = studentId;
        this.deadline = deadline;
        this.status = Status.NOT_STARTED;
    }

    public String getTaskId() { return taskId; }
    public String getTaskName() { return taskName; }
    public String getStudentId() { return studentId; }
    public LocalDate getDeadline() { return deadline; }
    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    /**
     * Checks if the task is overdue relative to a given date.
     */
    public boolean isOverdue(LocalDate currentDate) {
        return (status != Status.SUBMITTED) && currentDate.isAfter(deadline);
    }

    /**
     * Returns days remaining until deadline from a given date.
     * Negative value means overdue.
     */
    public long daysUntilDeadline(LocalDate currentDate) {
        return java.time.temporal.ChronoUnit.DAYS.between(currentDate, deadline);
    }
}
