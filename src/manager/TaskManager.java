package manager;

import tasks.*;

import java.util.List;

public interface TaskManager {
    // Create
    Task addTask(Task task);

    Epic addEpic(Epic epic);

    Subtask addSubtask(Subtask subtask);

    // Update
    Task updateTask(Task task);

    Epic updateEpic(Epic epic);

    Subtask updateSubtask(Subtask subtask);

    // Get List
    List<Task> getTasks();

    List<Epic> getEpics();

    List<Subtask> getSubtasks();

    List<Subtask> getEpicSubtasks(int id);

    // Get by ID
    Task getTaskByID(int id);

    Epic getEpicByID(int id);

    Subtask getSubtaskByID(int id);

    // Remove by ID
    void removeTaskByID(int id);

    void removeEpicByID(int id);

    void removeSubtaskByID(int id);

    // Delete All
    void deleteTasks();

    void deleteEpics();

    void deleteSubtasks();

    // History
    List<Task> getHistory();

    List<Task> getPrioritizedTasks();

    boolean isTaskOverlapping(Task task, int ignoreTaskId);
}
