package sit707_week6;

import java.util.*;
import java.time.LocalDateTime;

/**
 * Simulated OnTrack platform functions.
 * 
 * @author Aswin Soman (s225287418)
 */
public class OnTrackService {

    public enum TaskStatus {
        NOT_STARTED, WORKING_ON_IT, SUBMITTED, DISCUSS, DEMONSTRATE,
        FIX_AND_RESUBMIT, REDO, COMPLETE, FAIL
    }

    public enum TaskGrade {
        PASS, CREDIT, DISTINCTION, HIGH_DISTINCTION
    }

    public static class Student {
        private String studentId;
        private String name;
        private String email;

        public Student(String studentId, String name, String email) {
            if (studentId == null || studentId.trim().isEmpty())
                throw new IllegalArgumentException("Student ID cannot be null or empty");
            if (name == null || name.trim().isEmpty())
                throw new IllegalArgumentException("Student name cannot be null or empty");
            if (email == null || !email.contains("@"))
                throw new IllegalArgumentException("Invalid email address");
            this.studentId = studentId.trim();
            this.name = name.trim();
            this.email = email.trim();
        }

        public String getStudentId() { return studentId; }
        public String getName() { return name; }
        public String getEmail() { return email; }
    }

    public static class Task {
        private String taskId;
        private String title;
        private TaskGrade grade;
        private TaskStatus status;
        private String studentId;
        private LocalDateTime submissionDate;
        private LocalDateTime dueDate;
        private List<ChatMessage> chatMessages;

        public Task(String taskId, String title, TaskGrade grade, String studentId, LocalDateTime dueDate) {
            if (taskId == null || taskId.trim().isEmpty())
                throw new IllegalArgumentException("Task ID cannot be null or empty");
            if (title == null || title.trim().isEmpty())
                throw new IllegalArgumentException("Task title cannot be null or empty");
            this.taskId = taskId;
            this.title = title;
            this.grade = grade;
            this.status = TaskStatus.NOT_STARTED;
            this.studentId = studentId;
            this.dueDate = dueDate;
            this.submissionDate = null;
            this.chatMessages = new ArrayList<>();
        }

        public String getTaskId() { return taskId; }
        public String getTitle() { return title; }
        public TaskGrade getGrade() { return grade; }
        public TaskStatus getStatus() { return status; }
        public void setStatus(TaskStatus status) { this.status = status; }
        public String getStudentId() { return studentId; }
        public LocalDateTime getSubmissionDate() { return submissionDate; }
        public void setSubmissionDate(LocalDateTime date) { this.submissionDate = date; }
        public LocalDateTime getDueDate() { return dueDate; }
        public List<ChatMessage> getChatMessages() { return Collections.unmodifiableList(chatMessages); }
        public void addChatMessage(ChatMessage msg) { chatMessages.add(msg); }
    }

    public static class ChatMessage {
        private String sender;
        private String message;
        private LocalDateTime timestamp;

        public ChatMessage(String sender, String message, LocalDateTime timestamp) {
            if (sender == null || sender.trim().isEmpty())
                throw new IllegalArgumentException("Sender cannot be null or empty");
            if (message == null || message.trim().isEmpty())
                throw new IllegalArgumentException("Message cannot be null or empty");
            this.sender = sender;
            this.message = message;
            this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
        }

        public String getSender() { return sender; }
        public String getMessage() { return message; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }

    public static class SubmissionResult {
        private boolean success;
        private String message;
        private String taskId;

        public SubmissionResult(boolean success, String message, String taskId) {
            this.success = success;
            this.message = message;
            this.taskId = taskId;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public String getTaskId() { return taskId; }
    }

    public static class ProgressReport {
        private String studentId;
        private int totalTasks;
        private int completedTasks;
        private int submittedTasks;
        private int overdueTaskCount;
        private double completionPercentage;

        public ProgressReport(String studentId, int totalTasks, int completedTasks,
                              int submittedTasks, int overdueTaskCount) {
            this.studentId = studentId;
            this.totalTasks = totalTasks;
            this.completedTasks = completedTasks;
            this.submittedTasks = submittedTasks;
            this.overdueTaskCount = overdueTaskCount;
            this.completionPercentage = totalTasks > 0
                ? Math.round((completedTasks * 100.0 / totalTasks) * 10.0) / 10.0 : 0.0;
        }

        public String getStudentId() { return studentId; }
        public int getTotalTasks() { return totalTasks; }
        public int getCompletedTasks() { return completedTasks; }
        public int getSubmittedTasks() { return submittedTasks; }
        public int getOverdueTaskCount() { return overdueTaskCount; }
        public double getCompletionPercentage() { return completionPercentage; }
    }

    // ── Storage ──

    private Map<String, Student> students = new HashMap<>();
    private Map<String, List<Task>> studentTasks = new HashMap<>();

    // ── Function 1: Register Student ──

    public Student registerStudent(String studentId, String name, String email) {
        Student student = new Student(studentId, name, email);
        if (students.containsKey(student.getStudentId())) {
            throw new IllegalArgumentException("Student ID already registered: " + studentId);
        }
        students.put(student.getStudentId(), student);
        studentTasks.put(student.getStudentId(), new ArrayList<>());
        return student;
    }

    // ── Function 2: Task Inbox ──

    public List<Task> getTaskInbox(String studentId) {
        if (!students.containsKey(studentId)) {
            throw new IllegalArgumentException("Student not found: " + studentId);
        }
        List<Task> tasks = studentTasks.get(studentId);
        List<Task> sorted = new ArrayList<>(tasks);
        sorted.sort((a, b) -> {
            if (a.getDueDate() == null && b.getDueDate() == null) return 0;
            if (a.getDueDate() == null) return 1;
            if (b.getDueDate() == null) return -1;
            return a.getDueDate().compareTo(b.getDueDate());
        });
        return sorted;
    }

    public void addTask(String studentId, Task task) {
        if (!students.containsKey(studentId)) {
            throw new IllegalArgumentException("Student not found: " + studentId);
        }
        studentTasks.get(studentId).add(task);
    }

    // ── Function 3: Submit Task ──

    public SubmissionResult submitTask(String studentId, String taskId, LocalDateTime submissionTime) {
        if (!students.containsKey(studentId)) {
            return new SubmissionResult(false, "Student not found", taskId);
        }
        Task task = findTask(studentId, taskId);
        if (task == null) {
            return new SubmissionResult(false, "Task not found", taskId);
        }
        Set<TaskStatus> submittableStates = EnumSet.of(
            TaskStatus.NOT_STARTED, TaskStatus.WORKING_ON_IT,
            TaskStatus.FIX_AND_RESUBMIT, TaskStatus.REDO
        );
        if (!submittableStates.contains(task.getStatus())) {
            return new SubmissionResult(false,
                "Task cannot be submitted in current state: " + task.getStatus(), taskId);
        }
        boolean isLate = task.getDueDate() != null && submissionTime.isAfter(task.getDueDate());
        task.setStatus(TaskStatus.SUBMITTED);
        task.setSubmissionDate(submissionTime);
        String message = isLate ? "Task submitted (late submission)" : "Task submitted successfully";
        return new SubmissionResult(true, message, taskId);
    }

    // ── Function 4: View Task Feedback / Chat ──

    public List<ChatMessage> getTaskFeedback(String studentId, String taskId) {
        if (!students.containsKey(studentId)) {
            throw new IllegalArgumentException("Student not found: " + studentId);
        }
        Task task = findTask(studentId, taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task not found: " + taskId);
        }
        return task.getChatMessages();
    }

    public ChatMessage sendChatMessage(String studentId, String taskId,
                                        String sender, String message, LocalDateTime timestamp) {
        if (!students.containsKey(studentId)) {
            throw new IllegalArgumentException("Student not found: " + studentId);
        }
        Task task = findTask(studentId, taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task not found: " + taskId);
        }
        ChatMessage msg = new ChatMessage(sender, message, timestamp);
        task.addChatMessage(msg);
        return msg;
    }

    // ── Function 5: Update Task Status (Tutor Action) ──

    public boolean updateTaskStatus(String studentId, String taskId, TaskStatus newStatus) {
        if (!students.containsKey(studentId)) {
            throw new IllegalArgumentException("Student not found: " + studentId);
        }
        Task task = findTask(studentId, taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task not found: " + taskId);
        }
        TaskStatus current = task.getStatus();
        Map<TaskStatus, Set<TaskStatus>> validTransitions = new HashMap<>();
        validTransitions.put(TaskStatus.SUBMITTED, EnumSet.of(
            TaskStatus.DISCUSS, TaskStatus.DEMONSTRATE, TaskStatus.FIX_AND_RESUBMIT,
            TaskStatus.REDO, TaskStatus.COMPLETE, TaskStatus.FAIL));
        validTransitions.put(TaskStatus.DISCUSS, EnumSet.of(
            TaskStatus.DEMONSTRATE, TaskStatus.COMPLETE));
        validTransitions.put(TaskStatus.DEMONSTRATE, EnumSet.of(
            TaskStatus.COMPLETE, TaskStatus.FIX_AND_RESUBMIT));
        validTransitions.put(TaskStatus.FIX_AND_RESUBMIT, EnumSet.of(
            TaskStatus.SUBMITTED, TaskStatus.WORKING_ON_IT));
        validTransitions.put(TaskStatus.REDO, EnumSet.of(
            TaskStatus.SUBMITTED, TaskStatus.WORKING_ON_IT));
        validTransitions.put(TaskStatus.NOT_STARTED, EnumSet.of(
            TaskStatus.WORKING_ON_IT));
        validTransitions.put(TaskStatus.WORKING_ON_IT, EnumSet.of(
            TaskStatus.SUBMITTED));
        Set<TaskStatus> allowed = validTransitions.getOrDefault(current, Collections.emptySet());
        if (!allowed.contains(newStatus)) {
            return false;
        }
        task.setStatus(newStatus);
        return true;
    }

    // ── Function 6: Student Progress Report ──

    public ProgressReport getProgressReport(String studentId, LocalDateTime currentTime) {
        if (!students.containsKey(studentId)) {
            throw new IllegalArgumentException("Student not found: " + studentId);
        }
        List<Task> tasks = studentTasks.get(studentId);
        int total = tasks.size();
        int completed = 0;
        int submitted = 0;
        int overdue = 0;
        for (Task task : tasks) {
            if (task.getStatus() == TaskStatus.COMPLETE) completed++;
            if (task.getStatus() == TaskStatus.SUBMITTED ||
                task.getStatus() == TaskStatus.DISCUSS ||
                task.getStatus() == TaskStatus.DEMONSTRATE) submitted++;
            if (task.getStatus() != TaskStatus.COMPLETE &&
                task.getDueDate() != null &&
                currentTime.isAfter(task.getDueDate())) overdue++;
        }
        return new ProgressReport(studentId, total, completed, submitted, overdue);
    }

    private Task findTask(String studentId, String taskId) {
        List<Task> tasks = studentTasks.get(studentId);
        if (tasks == null) return null;
        for (Task t : tasks) {
            if (t.getTaskId().equals(taskId)) return t;
        }
        return null;
    }
}