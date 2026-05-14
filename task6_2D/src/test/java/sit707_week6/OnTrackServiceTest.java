package sit707_week6;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Right-BICEP test suite for OnTrackService.
 * 
 * @author Aswin Soman (s225287418)
 */
public class OnTrackServiceTest {

    private OnTrackService service;
    private LocalDateTime now;
    private LocalDateTime pastDate;
    private LocalDateTime futureDate;

    @Before
    public void setUp() {
        service = new OnTrackService();
        now = LocalDateTime.of(2026, 5, 13, 10, 0);
        pastDate = LocalDateTime.of(2026, 4, 1, 10, 0);
        futureDate = LocalDateTime.of(2026, 6, 30, 23, 59);
    }

    @Test
    public void testStudentIdentity() {
        String studentId = "s225287418";
        Assert.assertNotNull("Student ID is null", studentId);
    }

    @Test
    public void testStudentName() {
        String studentName = "Aswin Soman";
        Assert.assertNotNull("Student name is null", studentName);
    }

    // Function 1: registerStudent


    @Test
    public void testRegisterStudent_Right_ValidRegistration() {
        OnTrackService.Student student = service.registerStudent("s123", "Alice Smith", "alice@deakin.edu.au");
        Assert.assertEquals("s123", student.getStudentId());
        Assert.assertEquals("Alice Smith", student.getName());
        Assert.assertEquals("alice@deakin.edu.au", student.getEmail());
    }

    @Test
    public void testRegisterStudent_Boundary_WhitespaceTrimming() {
        OnTrackService.Student student = service.registerStudent("  s456  ", "  Bob  ", "bob@deakin.edu.au");
        Assert.assertEquals("s456", student.getStudentId());
        Assert.assertEquals("Bob", student.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterStudent_Error_NullStudentId() {
        service.registerStudent(null, "Alice", "alice@deakin.edu.au");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterStudent_Error_EmptyName() {
        service.registerStudent("s123", "", "alice@deakin.edu.au");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterStudent_Error_InvalidEmail() {
        service.registerStudent("s123", "Alice", "alice-deakin.edu.au");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterStudent_Error_DuplicateId() {
        service.registerStudent("s123", "Alice", "alice@deakin.edu.au");
        service.registerStudent("s123", "Bob", "bob@deakin.edu.au");
    }

    // Function 2: getTaskInbox

    @Test
    public void testGetTaskInbox_Right_SortedByDueDate() {
        service.registerStudent("s100", "Charlie", "charlie@deakin.edu.au");
        LocalDateTime early = LocalDateTime.of(2026, 3, 1, 0, 0);
        LocalDateTime mid = LocalDateTime.of(2026, 5, 1, 0, 0);
        LocalDateTime late = LocalDateTime.of(2026, 7, 1, 0, 0);

        service.addTask("s100", new OnTrackService.Task("t3", "Task C", OnTrackService.TaskGrade.DISTINCTION, "s100", late));
        service.addTask("s100", new OnTrackService.Task("t1", "Task A", OnTrackService.TaskGrade.PASS, "s100", early));
        service.addTask("s100", new OnTrackService.Task("t2", "Task B", OnTrackService.TaskGrade.CREDIT, "s100", mid));

        List<OnTrackService.Task> inbox = service.getTaskInbox("s100");
        Assert.assertEquals(3, inbox.size());
        Assert.assertEquals("t1", inbox.get(0).getTaskId());
        Assert.assertEquals("t2", inbox.get(1).getTaskId());
        Assert.assertEquals("t3", inbox.get(2).getTaskId());
    }

    @Test
    public void testGetTaskInbox_Boundary_EmptyInbox() {
        service.registerStudent("s200", "Dana", "dana@deakin.edu.au");
        List<OnTrackService.Task> inbox = service.getTaskInbox("s200");
        Assert.assertNotNull(inbox);
        Assert.assertTrue(inbox.isEmpty());
    }

    @Test
    public void testGetTaskInbox_Boundary_SingleTask() {
        service.registerStudent("s201", "Eve", "eve@deakin.edu.au");
        service.addTask("s201", new OnTrackService.Task("t1", "Solo Task", OnTrackService.TaskGrade.PASS, "s201", futureDate));
        List<OnTrackService.Task> inbox = service.getTaskInbox("s201");
        Assert.assertEquals(1, inbox.size());
        Assert.assertEquals("t1", inbox.get(0).getTaskId());
    }

    @Test
    public void testGetTaskInbox_Boundary_NullDueDatesSortLast() {
        service.registerStudent("s202", "Frank", "frank@deakin.edu.au");
        service.addTask("s202", new OnTrackService.Task("t1", "No Due", OnTrackService.TaskGrade.PASS, "s202", null));
        service.addTask("s202", new OnTrackService.Task("t2", "Has Due", OnTrackService.TaskGrade.CREDIT, "s202", futureDate));
        List<OnTrackService.Task> inbox = service.getTaskInbox("s202");
        Assert.assertEquals("t2", inbox.get(0).getTaskId());
        Assert.assertEquals("t1", inbox.get(1).getTaskId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTaskInbox_Error_StudentNotFound() {
        service.getTaskInbox("nonexistent");
    }

    @Test
    public void testGetTaskInbox_CrossCheck_CountMatchesAdded() {
        service.registerStudent("s203", "Grace", "grace@deakin.edu.au");
        int count = 5;
        for (int i = 0; i < count; i++) {
            service.addTask("s203", new OnTrackService.Task("t" + i, "Task " + i, OnTrackService.TaskGrade.PASS, "s203",
                futureDate.plusDays(i)));
        }
        Assert.assertEquals(count, service.getTaskInbox("s203").size());
    }

    // Function 3: submitTask

    @Test
    public void testSubmitTask_Right_FromNotStarted() {
        service.registerStudent("s300", "Hank", "hank@deakin.edu.au");
        service.addTask("s300", new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s300", futureDate));

        OnTrackService.SubmissionResult result = service.submitTask("s300", "t1", now);
        Assert.assertTrue(result.isSuccess());
        Assert.assertEquals("Task submitted successfully", result.getMessage());
    }

    @Test
    public void testSubmitTask_Right_FromFixAndResubmit() {
        service.registerStudent("s301", "Ivy", "ivy@deakin.edu.au");
        OnTrackService.Task task = new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.CREDIT, "s301", futureDate);
        task.setStatus(OnTrackService.TaskStatus.FIX_AND_RESUBMIT);
        service.addTask("s301", task);

        OnTrackService.SubmissionResult result = service.submitTask("s301", "t1", now);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testSubmitTask_Right_LateSubmission() {
        service.registerStudent("s302", "Jack", "jack@deakin.edu.au");
        service.addTask("s302", new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s302", pastDate));

        OnTrackService.SubmissionResult result = service.submitTask("s302", "t1", now);
        Assert.assertTrue(result.isSuccess());
        Assert.assertTrue(result.getMessage().contains("late"));
    }

    @Test
    public void testSubmitTask_Boundary_AlreadyComplete() {
        service.registerStudent("s303", "Kate", "kate@deakin.edu.au");
        OnTrackService.Task task = new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s303", futureDate);
        task.setStatus(OnTrackService.TaskStatus.COMPLETE);
        service.addTask("s303", task);

        OnTrackService.SubmissionResult result = service.submitTask("s303", "t1", now);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testSubmitTask_Boundary_AlreadySubmitted() {
        service.registerStudent("s304", "Leo", "leo@deakin.edu.au");
        OnTrackService.Task task = new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s304", futureDate);
        task.setStatus(OnTrackService.TaskStatus.SUBMITTED);
        service.addTask("s304", task);

        OnTrackService.SubmissionResult result = service.submitTask("s304", "t1", now);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testSubmitTask_Error_StudentNotFound() {
        OnTrackService.SubmissionResult result = service.submitTask("ghost", "t1", now);
        Assert.assertFalse(result.isSuccess());
        Assert.assertTrue(result.getMessage().contains("Student not found"));
    }

    @Test
    public void testSubmitTask_Error_TaskNotFound() {
        service.registerStudent("s305", "Mia", "mia@deakin.edu.au");
        OnTrackService.SubmissionResult result = service.submitTask("s305", "nonexistent", now);
        Assert.assertFalse(result.isSuccess());
        Assert.assertTrue(result.getMessage().contains("Task not found"));
    }

    @Test
    public void testSubmitTask_Inverse_StatusChangesToSubmitted() {
        service.registerStudent("s306", "Noah", "noah@deakin.edu.au");
        service.addTask("s306", new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s306", futureDate));
        service.submitTask("s306", "t1", now);

        OnTrackService.Task task = service.getTaskInbox("s306").get(0);
        Assert.assertEquals(OnTrackService.TaskStatus.SUBMITTED, task.getStatus());
    }

    @Test
    public void testSubmitTask_Inverse_SubmissionDateSet() {
        service.registerStudent("s307", "Olivia", "olivia@deakin.edu.au");
        service.addTask("s307", new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s307", futureDate));
        service.submitTask("s307", "t1", now);

        OnTrackService.Task task = service.getTaskInbox("s307").get(0);
        Assert.assertEquals(now, task.getSubmissionDate());
    }

    // Function 4: getTaskFeedback / sendChatMessage

    @Test
    public void testChatMessage_Right_SendAndRetrieve() {
        service.registerStudent("s400", "Paul", "paul@deakin.edu.au");
        service.addTask("s400", new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s400", futureDate));

        service.sendChatMessage("s400", "t1", "Paul", "Here is my submission", now);
        List<OnTrackService.ChatMessage> messages = service.getTaskFeedback("s400", "t1");

        Assert.assertEquals(1, messages.size());
        Assert.assertEquals("Paul", messages.get(0).getSender());
        Assert.assertEquals("Here is my submission", messages.get(0).getMessage());
    }

    @Test
    public void testChatMessage_Right_MultipleMessagesOrdered() {
        service.registerStudent("s401", "Quinn", "quinn@deakin.edu.au");
        service.addTask("s401", new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s401", futureDate));

        LocalDateTime t1 = now;
        LocalDateTime t2 = now.plusHours(1);
        LocalDateTime t3 = now.plusHours(2);

        service.sendChatMessage("s401", "t1", "Quinn", "Submitted my work", t1);
        service.sendChatMessage("s401", "t1", "Tutor", "Please fix section 2", t2);
        service.sendChatMessage("s401", "t1", "Quinn", "Fixed and resubmitted", t3);

        List<OnTrackService.ChatMessage> messages = service.getTaskFeedback("s401", "t1");
        Assert.assertEquals(3, messages.size());
        Assert.assertEquals("Quinn", messages.get(0).getSender());
        Assert.assertEquals("Tutor", messages.get(1).getSender());
        Assert.assertEquals("Quinn", messages.get(2).getSender());
    }

    @Test
    public void testChatMessage_Boundary_EmptyChat() {
        service.registerStudent("s402", "Rose", "rose@deakin.edu.au");
        service.addTask("s402", new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s402", futureDate));

        List<OnTrackService.ChatMessage> messages = service.getTaskFeedback("s402", "t1");
        Assert.assertNotNull(messages);
        Assert.assertTrue(messages.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChatMessage_Error_EmptyMessage() {
        service.registerStudent("s403", "Sam", "sam@deakin.edu.au");
        service.addTask("s403", new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s403", futureDate));
        service.sendChatMessage("s403", "t1", "Sam", "", now);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChatMessage_Error_NullSender() {
        service.registerStudent("s404", "Tara", "tara@deakin.edu.au");
        service.addTask("s404", new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s404", futureDate));
        service.sendChatMessage("s404", "t1", null, "Hello", now);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChatMessage_Error_TaskNotFound() {
        service.registerStudent("s405", "Uma", "uma@deakin.edu.au");
        service.getTaskFeedback("s405", "nonexistent");
    }

    @Test
    public void testChatMessage_CrossCheck_CountMatchesSends() {
        service.registerStudent("s406", "Vera", "vera@deakin.edu.au");
        service.addTask("s406", new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s406", futureDate));

        int sendCount = 7;
        for (int i = 0; i < sendCount; i++) {
            service.sendChatMessage("s406", "t1", "Vera", "Message " + i, now.plusMinutes(i));
        }

        Assert.assertEquals(sendCount, service.getTaskFeedback("s406", "t1").size());
    }

    // Function 5: updateTaskStatus

    @Test
    public void testUpdateStatus_Right_SubmittedToDiscuss() {
        service.registerStudent("s500", "Will", "will@deakin.edu.au");
        OnTrackService.Task task = new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s500", futureDate);
        task.setStatus(OnTrackService.TaskStatus.SUBMITTED);
        service.addTask("s500", task);

        boolean result = service.updateTaskStatus("s500", "t1", OnTrackService.TaskStatus.DISCUSS);
        Assert.assertTrue(result);
    }

    @Test
    public void testUpdateStatus_Right_SubmittedToComplete() {
        service.registerStudent("s501", "Xena", "xena@deakin.edu.au");
        OnTrackService.Task task = new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s501", futureDate);
        task.setStatus(OnTrackService.TaskStatus.SUBMITTED);
        service.addTask("s501", task);

        boolean result = service.updateTaskStatus("s501", "t1", OnTrackService.TaskStatus.COMPLETE);
        Assert.assertTrue(result);
    }

    @Test
    public void testUpdateStatus_Right_DiscussToComplete() {
        service.registerStudent("s502", "Yuki", "yuki@deakin.edu.au");
        OnTrackService.Task task = new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s502", futureDate);
        task.setStatus(OnTrackService.TaskStatus.DISCUSS);
        service.addTask("s502", task);

        boolean result = service.updateTaskStatus("s502", "t1", OnTrackService.TaskStatus.COMPLETE);
        Assert.assertTrue(result);
    }

    @Test
    public void testUpdateStatus_Boundary_InvalidTransition_NotStartedToComplete() {
        service.registerStudent("s503", "Zara", "zara@deakin.edu.au");
        service.addTask("s503", new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s503", futureDate));

        boolean result = service.updateTaskStatus("s503", "t1", OnTrackService.TaskStatus.COMPLETE);
        Assert.assertFalse(result);
    }

    @Test
    public void testUpdateStatus_Boundary_InvalidTransition_CompleteToSubmitted() {
        service.registerStudent("s504", "Amy", "amy@deakin.edu.au");
        OnTrackService.Task task = new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s504", futureDate);
        task.setStatus(OnTrackService.TaskStatus.COMPLETE);
        service.addTask("s504", task);

        boolean result = service.updateTaskStatus("s504", "t1", OnTrackService.TaskStatus.SUBMITTED);
        Assert.assertFalse(result);
    }

    @Test
    public void testUpdateStatus_Boundary_SubmittedToFixAndResubmit() {
        service.registerStudent("s505", "Ben", "ben@deakin.edu.au");
        OnTrackService.Task task = new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s505", futureDate);
        task.setStatus(OnTrackService.TaskStatus.SUBMITTED);
        service.addTask("s505", task);

        boolean result = service.updateTaskStatus("s505", "t1", OnTrackService.TaskStatus.FIX_AND_RESUBMIT);
        Assert.assertTrue(result);
    }

    @Test
    public void testUpdateStatus_Inverse_StatusReflectsNewState() {
        service.registerStudent("s506", "Cara", "cara@deakin.edu.au");
        OnTrackService.Task task = new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s506", futureDate);
        task.setStatus(OnTrackService.TaskStatus.SUBMITTED);
        service.addTask("s506", task);

        service.updateTaskStatus("s506", "t1", OnTrackService.TaskStatus.DISCUSS);
        OnTrackService.Task updated = service.getTaskInbox("s506").get(0);
        Assert.assertEquals(OnTrackService.TaskStatus.DISCUSS, updated.getStatus());
    }

    @Test
    public void testUpdateStatus_Inverse_StatusUnchangedOnInvalidTransition() {
        service.registerStudent("s507", "Dan", "dan@deakin.edu.au");
        service.addTask("s507", new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s507", futureDate));

        service.updateTaskStatus("s507", "t1", OnTrackService.TaskStatus.COMPLETE);
        OnTrackService.Task unchanged = service.getTaskInbox("s507").get(0);
        Assert.assertEquals(OnTrackService.TaskStatus.NOT_STARTED, unchanged.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateStatus_Error_StudentNotFound() {
        service.updateTaskStatus("ghost", "t1", OnTrackService.TaskStatus.COMPLETE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateStatus_Error_TaskNotFound() {
        service.registerStudent("s508", "Eva", "eva@deakin.edu.au");
        service.updateTaskStatus("s508", "nonexistent", OnTrackService.TaskStatus.COMPLETE);
    }
    
    // Function 6: getProgressReport

    @Test
    public void testProgress_Right_MixedStatuses() {
        service.registerStudent("s600", "Fiona", "fiona@deakin.edu.au");

        OnTrackService.Task t1 = new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s600", pastDate);
        t1.setStatus(OnTrackService.TaskStatus.COMPLETE);
        OnTrackService.Task t2 = new OnTrackService.Task("t2", "Task 2", OnTrackService.TaskGrade.CREDIT, "s600", pastDate);
        t2.setStatus(OnTrackService.TaskStatus.SUBMITTED);
        OnTrackService.Task t3 = new OnTrackService.Task("t3", "Task 3", OnTrackService.TaskGrade.DISTINCTION, "s600", futureDate);
        t3.setStatus(OnTrackService.TaskStatus.NOT_STARTED);
        OnTrackService.Task t4 = new OnTrackService.Task("t4", "Task 4", OnTrackService.TaskGrade.PASS, "s600", pastDate);
        t4.setStatus(OnTrackService.TaskStatus.FIX_AND_RESUBMIT);

        service.addTask("s600", t1);
        service.addTask("s600", t2);
        service.addTask("s600", t3);
        service.addTask("s600", t4);

        OnTrackService.ProgressReport report = service.getProgressReport("s600", now);
        Assert.assertEquals(4, report.getTotalTasks());
        Assert.assertEquals(1, report.getCompletedTasks());
        Assert.assertEquals(1, report.getSubmittedTasks());
        Assert.assertEquals(2, report.getOverdueTaskCount());
        Assert.assertEquals(25.0, report.getCompletionPercentage(), 0.01);
    }

    @Test
    public void testProgress_Boundary_NoTasks() {
        service.registerStudent("s601", "George", "george@deakin.edu.au");
        OnTrackService.ProgressReport report = service.getProgressReport("s601", now);
        Assert.assertEquals(0, report.getTotalTasks());
        Assert.assertEquals(0, report.getCompletedTasks());
        Assert.assertEquals(0.0, report.getCompletionPercentage(), 0.01);
    }

    @Test
    public void testProgress_Boundary_AllComplete() {
        service.registerStudent("s602", "Helen", "helen@deakin.edu.au");
        for (int i = 0; i < 3; i++) {
            OnTrackService.Task t = new OnTrackService.Task("t" + i, "Task " + i, OnTrackService.TaskGrade.PASS, "s602", futureDate);
            t.setStatus(OnTrackService.TaskStatus.COMPLETE);
            service.addTask("s602", t);
        }
        OnTrackService.ProgressReport report = service.getProgressReport("s602", now);
        Assert.assertEquals(100.0, report.getCompletionPercentage(), 0.01);
        Assert.assertEquals(0, report.getOverdueTaskCount());
    }

    @Test
    public void testProgress_Boundary_SingleOverdueTask() {
        service.registerStudent("s603", "Ivan", "ivan@deakin.edu.au");
        service.addTask("s603", new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s603", pastDate));
        OnTrackService.ProgressReport report = service.getProgressReport("s603", now);
        Assert.assertEquals(1, report.getOverdueTaskCount());
        Assert.assertEquals(0.0, report.getCompletionPercentage(), 0.01);
    }

    @Test
    public void testProgress_CrossCheck_CompletedPlusRemainingEqualsTotal() {
        service.registerStudent("s604", "Jade", "jade@deakin.edu.au");
        OnTrackService.Task t1 = new OnTrackService.Task("t1", "Task 1", OnTrackService.TaskGrade.PASS, "s604", futureDate);
        t1.setStatus(OnTrackService.TaskStatus.COMPLETE);
        OnTrackService.Task t2 = new OnTrackService.Task("t2", "Task 2", OnTrackService.TaskGrade.CREDIT, "s604", futureDate);
        t2.setStatus(OnTrackService.TaskStatus.SUBMITTED);
        OnTrackService.Task t3 = new OnTrackService.Task("t3", "Task 3", OnTrackService.TaskGrade.DISTINCTION, "s604", futureDate);

        service.addTask("s604", t1);
        service.addTask("s604", t2);
        service.addTask("s604", t3);

        OnTrackService.ProgressReport report = service.getProgressReport("s604", now);
        int remaining = report.getTotalTasks() - report.getCompletedTasks();
        Assert.assertEquals(2, remaining);
        Assert.assertEquals(3, report.getTotalTasks());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProgress_Error_StudentNotFound() {
        service.getProgressReport("ghost", now);
    }

    @Test
    public void testProgress_Performance_ManyTasks() {
        service.registerStudent("s605", "Karl", "karl@deakin.edu.au");
        for (int i = 0; i < 100; i++) {
            OnTrackService.Task t = new OnTrackService.Task("t" + i, "Task " + i, OnTrackService.TaskGrade.PASS, "s605", futureDate);
            if (i < 50) t.setStatus(OnTrackService.TaskStatus.COMPLETE);
            service.addTask("s605", t);
        }

        long start = System.currentTimeMillis();
        OnTrackService.ProgressReport report = service.getProgressReport("s605", now);
        long elapsed = System.currentTimeMillis() - start;

        Assert.assertEquals(100, report.getTotalTasks());
        Assert.assertEquals(50, report.getCompletedTasks());
        Assert.assertEquals(50.0, report.getCompletionPercentage(), 0.01);
        Assert.assertTrue("Progress report should compute in under 1 second", elapsed < 1000);
    }

    @Test
    public void testGetTaskInbox_Performance_ManyTasks() {
        service.registerStudent("s606", "Luna", "luna@deakin.edu.au");
        for (int i = 100; i > 0; i--) {
            service.addTask("s606", new OnTrackService.Task("t" + i, "Task " + i, OnTrackService.TaskGrade.PASS, "s606",
                futureDate.plusDays(i)));
        }

        long start = System.currentTimeMillis();
        List<OnTrackService.Task> inbox = service.getTaskInbox("s606");
        long elapsed = System.currentTimeMillis() - start;

        Assert.assertEquals(100, inbox.size());
        Assert.assertTrue("Inbox sort should complete in under 1 second", elapsed < 1000);
        Assert.assertTrue(inbox.get(0).getDueDate().isBefore(inbox.get(99).getDueDate()));
    }

    // Integration / End-to-End Workflow

    @Test
    public void testFullWorkflow_Right_EndToEnd() {
        service.registerStudent("s700", "Morgan", "morgan@deakin.edu.au");
        service.addTask("s700", new OnTrackService.Task("t1", "3.1P BVA Testing", OnTrackService.TaskGrade.PASS, "s700", futureDate));

        Assert.assertEquals(1, service.getTaskInbox("s700").size());

        OnTrackService.SubmissionResult submit1 = service.submitTask("s700", "t1", now);
        Assert.assertTrue(submit1.isSuccess());

        service.sendChatMessage("s700", "t1", "Tutor", "Please add more boundary tests", now.plusHours(24));
        service.updateTaskStatus("s700", "t1", OnTrackService.TaskStatus.FIX_AND_RESUBMIT);

        List<OnTrackService.ChatMessage> feedback = service.getTaskFeedback("s700", "t1");
        Assert.assertEquals(1, feedback.size());
        Assert.assertEquals("Tutor", feedback.get(0).getSender());

        OnTrackService.SubmissionResult submit2 = service.submitTask("s700", "t1", now.plusHours(48));
        Assert.assertTrue(submit2.isSuccess());

        boolean completed = service.updateTaskStatus("s700", "t1", OnTrackService.TaskStatus.COMPLETE);
        Assert.assertTrue(completed);

        OnTrackService.ProgressReport report = service.getProgressReport("s700", now);
        Assert.assertEquals(1, report.getCompletedTasks());
        Assert.assertEquals(100.0, report.getCompletionPercentage(), 0.01);
    }
}