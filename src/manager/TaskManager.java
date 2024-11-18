package manager;

import tasks.*;

import java.util.ArrayList;
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
    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    ArrayList<Subtask> getSubtasks();

    ArrayList<Subtask> getEpicSubtasks(int id);

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

    // updateEpicsStatus
    void updateEpicsStatus(Epic epic);

}
