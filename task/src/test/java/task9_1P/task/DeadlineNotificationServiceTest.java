package task9_1P.task;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class DeadlineNotificationServiceTest {

    private DeadlineNotificationService service;
    private static final String STUDENT_ID = "225287418";
    private static final LocalDate TODAY = LocalDate.of(2026, 5, 7);

    @Test
    public void testStudentIdentity() {
        String studentId = "225287418";
        assertNotNull("Student ID is null", studentId);
    }

    @Test
    public void testStudentName() {
        String studentName = "Aswin Soman";
        assertNotNull("Student name is null", studentName);
    }

    @Before
    public void setUp() {
        service = new DeadlineNotificationService();
    }

    // ========== TDD ITERATION 1: Task Creation ==========

    @Test
    public void testIteration1_createTask() {
        // Red: Task class does not exist yet
        // Green: Create Task class with constructor and getters
        Task task = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 14));
        assertEquals("T1", task.getTaskId());
        assertEquals("5.1P", task.getTaskName());
        assertEquals(STUDENT_ID, task.getStudentId());
        assertEquals(LocalDate.of(2026, 5, 14), task.getDeadline());
    }

    @Test
    public void testIteration1_taskDefaultStatus() {
        // Red: No status field yet
        // Green: Add status field defaulting to NOT_STARTED
        Task task = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 14));
        assertEquals(Task.Status.NOT_STARTED, task.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIteration1_nullTaskIdThrowsException() {
        // Red: No validation yet
        // Green: Add null check in constructor
        new Task(null, "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 14));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIteration1_emptyTaskNameThrowsException() {
        new Task("T1", "", STUDENT_ID, LocalDate.of(2026, 5, 14));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIteration1_nullDeadlineThrowsException() {
        new Task("T1", "5.1P", STUDENT_ID, null);
    }

    // ========== TDD ITERATION 2: Add and Retrieve Tasks ==========

    @Test
    public void testIteration2_addTaskToService() {
        // Red: DeadlineNotificationService class does not exist yet
        // Green: Create service with addTask() and getTotalTaskCount()
        Task task = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 14));
        service.addTask(task);
        assertEquals(1, service.getTotalTaskCount());
    }

    @Test
    public void testIteration2_getTasksForStudent() {
        // Red: No getTasksForStudent() method
        // Green: Add method to filter tasks by student ID
        Task t1 = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 14));
        Task t2 = new Task("T2", "6.1P", "999999999", LocalDate.of(2026, 5, 20));
        Task t3 = new Task("T3", "5.2C", STUDENT_ID, LocalDate.of(2026, 5, 21));

        service.addTask(t1);
        service.addTask(t2);
        service.addTask(t3);

        List<Task> myTasks = service.getTasksForStudent(STUDENT_ID);
        assertEquals(2, myTasks.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIteration2_addNullTaskThrowsException() {
        service.addTask(null);
    }

    // ========== TDD ITERATION 3: Overdue Detection ==========

    @Test
    public void testIteration3_taskIsOverdue() {
        // Red: No isOverdue() method on Task
        // Green: Add isOverdue() comparing deadline with current date
        Task task = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 1));
        assertTrue(task.isOverdue(TODAY)); // deadline was May 1, today is May 7
    }

    @Test
    public void testIteration3_taskNotOverdueBeforeDeadline() {
        Task task = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 14));
        assertFalse(task.isOverdue(TODAY)); // deadline is May 14, today is May 7
    }

    @Test
    public void testIteration3_submittedTaskNeverOverdue() {
        // Red: Overdue check doesn't consider status
        // Green: Add status check — submitted tasks excluded
        Task task = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 1));
        task.setStatus(Task.Status.SUBMITTED);
        assertFalse(task.isOverdue(TODAY)); // past deadline but submitted
    }

    @Test
    public void testIteration3_getOverdueTasksFromService() {
        // Red: No getOverdueTasks() on service
        // Green: Add method filtering overdue tasks for student
        Task t1 = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 1)); // overdue
        Task t2 = new Task("T2", "6.1P", STUDENT_ID, LocalDate.of(2026, 5, 20)); // not overdue

        service.addTask(t1);
        service.addTask(t2);

        List<Task> overdue = service.getOverdueTasks(STUDENT_ID, TODAY);
        assertEquals(1, overdue.size());
        assertEquals("T1", overdue.get(0).getTaskId());
    }

    // ========== TDD ITERATION 4: Days Until Deadline ==========

    @Test
    public void testIteration4_daysUntilDeadline() {
        // Red: No daysUntilDeadline() method
        // Green: Add method using ChronoUnit.DAYS.between()
        Task task = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 10));
        assertEquals(3, task.daysUntilDeadline(TODAY)); // May 7 -> May 10 = 3 days
    }

    @Test
    public void testIteration4_daysUntilDeadlineNegativeWhenOverdue() {
        Task task = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 5));
        assertEquals(-2, task.daysUntilDeadline(TODAY)); // May 7 - May 5 = -2 days
    }

    @Test
    public void testIteration4_daysUntilDeadlineZeroOnDueDate() {
        Task task = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 7));
        assertEquals(0, task.daysUntilDeadline(TODAY)); // due today
    }

    // ========== TDD ITERATION 5: Upcoming Tasks ==========

    @Test
    public void testIteration5_getUpcomingTasksWithin3Days() {
        // Red: No getUpcomingTasks() method
        // Green: Add method filtering by days remaining
        Task t1 = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 8));  // 1 day away
        Task t2 = new Task("T2", "6.1P", STUDENT_ID, LocalDate.of(2026, 5, 20)); // 13 days away
        Task t3 = new Task("T3", "5.2C", STUDENT_ID, LocalDate.of(2026, 5, 9));  // 2 days away

        service.addTask(t1);
        service.addTask(t2);
        service.addTask(t3);

        List<Task> upcoming = service.getUpcomingTasks(STUDENT_ID, TODAY, 3);
        assertEquals(2, upcoming.size()); // T1 and T3
    }

    @Test
    public void testIteration5_upcomingExcludesSubmittedTasks() {
        Task t1 = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 8));
        t1.setStatus(Task.Status.SUBMITTED);

        service.addTask(t1);

        List<Task> upcoming = service.getUpcomingTasks(STUDENT_ID, TODAY, 3);
        assertEquals(0, upcoming.size()); // submitted, so excluded
    }

    @Test
    public void testIteration5_upcomingExcludesOverdueTasks() {
        Task t1 = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 1)); // already past

        service.addTask(t1);

        List<Task> upcoming = service.getUpcomingTasks(STUDENT_ID, TODAY, 7);
        assertEquals(0, upcoming.size()); // overdue, not upcoming
    }

    // ========== TDD ITERATION 6: Notification Generation ==========

    @Test
    public void testIteration6_overdueNotificationGenerated() {
        // Red: No generateNotifications() method
        // Green: Add method that builds notification strings
        Task t1 = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 5)); // 2 days overdue

        service.addTask(t1);

        List<String> notifications = service.generateNotifications(STUDENT_ID, TODAY, 3);
        assertEquals(1, notifications.size());
        assertTrue(notifications.get(0).contains("OVERDUE"));
        assertTrue(notifications.get(0).contains("5.1P"));
        assertTrue(notifications.get(0).contains("2 day(s) ago"));
    }

    @Test
    public void testIteration6_upcomingReminderNotification() {
        Task t1 = new Task("T1", "6.1P", STUDENT_ID, LocalDate.of(2026, 5, 9)); // 2 days away

        service.addTask(t1);

        List<String> notifications = service.generateNotifications(STUDENT_ID, TODAY, 3);
        assertEquals(1, notifications.size());
        assertTrue(notifications.get(0).contains("REMINDER"));
        assertTrue(notifications.get(0).contains("2 day(s)"));
    }

    @Test
    public void testIteration6_urgentNotificationOnDueDate() {
        Task t1 = new Task("T1", "7.1P", STUDENT_ID, LocalDate.of(2026, 5, 7)); // due today

        service.addTask(t1);

        List<String> notifications = service.generateNotifications(STUDENT_ID, TODAY, 3);
        assertEquals(1, notifications.size());
        assertTrue(notifications.get(0).contains("URGENT"));
        assertTrue(notifications.get(0).contains("due TODAY"));
    }

    @Test
    public void testIteration6_noNotificationsWhenAllSubmitted() {
        Task t1 = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 5));
        t1.setStatus(Task.Status.SUBMITTED);
        Task t2 = new Task("T2", "6.1P", STUDENT_ID, LocalDate.of(2026, 5, 9));
        t2.setStatus(Task.Status.SUBMITTED);

        service.addTask(t1);
        service.addTask(t2);

        List<String> notifications = service.generateNotifications(STUDENT_ID, TODAY, 7);
        assertEquals(0, notifications.size());
    }

    @Test
    public void testIteration6_mixedNotifications() {
        // Refactor: ensure overdue + upcoming both appear correctly
        Task t1 = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 3)); // 4 days overdue
        Task t2 = new Task("T2", "6.1P", STUDENT_ID, LocalDate.of(2026, 5, 9)); // 2 days away
        Task t3 = new Task("T3", "7.1P", STUDENT_ID, LocalDate.of(2026, 5, 7)); // due today
        Task t4 = new Task("T4", "8.1P", STUDENT_ID, LocalDate.of(2026, 6, 1)); // far away

        service.addTask(t1);
        service.addTask(t2);
        service.addTask(t3);
        service.addTask(t4);

        List<String> notifications = service.generateNotifications(STUDENT_ID, TODAY, 3);
        // Should have: 1 overdue (T1), 1 reminder (T2), 1 urgent (T3) = 3 total
        // T4 is too far away to trigger a reminder
        assertEquals(3, notifications.size());
    }

    // ========== TDD ITERATION 7: Update Overdue Statuses ==========

    @Test
    public void testIteration7_updateOverdueStatuses() {
        // Red: No updateOverdueStatuses() method
        // Green: Add method that sets OVERDUE status on past-deadline tasks
        Task t1 = new Task("T1", "5.1P", STUDENT_ID, LocalDate.of(2026, 5, 1));
        Task t2 = new Task("T2", "6.1P", STUDENT_ID, LocalDate.of(2026, 5, 20));

        service.addTask(t1);
        service.addTask(t2);

        service.updateOverdueStatuses(STUDENT_ID, TODAY);

        assertEquals(Task.Status.OVERDUE, t1.getStatus());
        assertEquals(Task.Status.NOT_STARTED, t2.getStatus());
    }
}