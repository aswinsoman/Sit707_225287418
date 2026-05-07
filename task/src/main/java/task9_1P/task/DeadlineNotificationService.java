package task9_1P.task;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service that manages task deadlines and generates notifications for students.
 * Provides functionality to:
 * - Add and retrieve tasks for a student
 * - Check which tasks are overdue
 * - Generate deadline reminder notifications
 * - Generate overdue notifications
 * - Retrieve upcoming tasks within a given number of days
 */
public class DeadlineNotificationService {

    private List<Task> tasks;

    public DeadlineNotificationService() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the service.
     */
    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }
        tasks.add(task);
    }

    /**
     * Returns all tasks for a given student.
     */
    public List<Task> getTasksForStudent(String studentId) {
        return tasks.stream()
                .filter(t -> t.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    /**
     * Returns all overdue tasks for a student as of the given date.
     * Submitted tasks are never considered overdue.
     */
    public List<Task> getOverdueTasks(String studentId, LocalDate currentDate) {
        return getTasksForStudent(studentId).stream()
                .filter(t -> t.isOverdue(currentDate))
                .collect(Collectors.toList());
    }

    /**
     * Returns tasks due within the next N days (inclusive) for a student.
     * Only returns tasks that are not yet submitted and not yet overdue.
     */
    public List<Task> getUpcomingTasks(String studentId, LocalDate currentDate, int withinDays) {
        return getTasksForStudent(studentId).stream()
                .filter(t -> t.getStatus() != Task.Status.SUBMITTED)
                .filter(t -> {
                    long daysLeft = t.daysUntilDeadline(currentDate);
                    return daysLeft >= 0 && daysLeft <= withinDays;
                })
                .collect(Collectors.toList());
    }

    /**
     * Generates notification messages for a student.
     * Includes overdue warnings and upcoming deadline reminders.
     */
    public List<String> generateNotifications(String studentId, LocalDate currentDate, int reminderDays) {
        List<String> notifications = new ArrayList<>();

        // Overdue notifications
        List<Task> overdue = getOverdueTasks(studentId, currentDate);
        for (Task t : overdue) {
            long daysOverdue = Math.abs(t.daysUntilDeadline(currentDate));
            notifications.add("OVERDUE: \"" + t.getTaskName() + "\" was due "
                    + daysOverdue + " day(s) ago. Please submit immediately.");
        }

        // Upcoming deadline reminders
        List<Task> upcoming = getUpcomingTasks(studentId, currentDate, reminderDays);
        for (Task t : upcoming) {
            long daysLeft = t.daysUntilDeadline(currentDate);
            if (daysLeft == 0) {
                notifications.add("URGENT: \"" + t.getTaskName() + "\" is due TODAY.");
            } else {
                notifications.add("REMINDER: \"" + t.getTaskName() + "\" is due in "
                        + daysLeft + " day(s).");
            }
        }

        return notifications;
    }

    /**
     * Marks overdue tasks with OVERDUE status.
     */
    public void updateOverdueStatuses(String studentId, LocalDate currentDate) {
        getTasksForStudent(studentId).stream()
                .filter(t -> t.isOverdue(currentDate))
                .forEach(t -> t.setStatus(Task.Status.OVERDUE));
    }

    /**
     * Returns total number of tasks tracked.
     */
    public int getTotalTaskCount() {
        return tasks.size();
    }
}