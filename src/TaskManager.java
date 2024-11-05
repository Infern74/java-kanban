import task.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private int nextId;

    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Subtask> subtasks;


    public TaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        nextId = 1;
    }

    private int getNextId() {
        return nextId++;
    }

    // Создание
    public Task addTask(Task task) {
        task.setId(getNextId());
        tasks.put(task.getId(),task);
        return task;
    }

    public Epic addEpic(Epic epic) {
        epic.setId(getNextId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Subtask addSubtask(Subtask subtask) {
        if (!epics.containsKey(subtask.getEpicId())) {
            return null;
        }
        subtask.setId(getNextId());
        Epic epic = epics.get(subtask.getEpicId());
        epic.addSubtask(subtask);
        subtasks.put(subtask.getId(), subtask);
        updateEpicsStatus(epic);
        return subtask;
    }

    // Обновление
    public Task updateTask(Task task){
        int taskId = task.getId();
        if (!tasks.containsKey(taskId)) {
            return null;
        }
        tasks.replace(taskId, task);
        return task;
    }

    public Epic updateEpic(Epic epic) {
        int epicId = epic.getId();
        if (!epics.containsKey(epicId)) {
            return null;
        }
        Epic oldEpic = epics.get(epicId);
        oldEpic.setName(epic.getName());
        oldEpic.setDescription(epic.getDescription());
        updateEpicsStatus(epic);
        return epic;
    }

    public Subtask updateSubtask(Subtask subtask) {
        int subtaskId = subtask.getId();
        int epicId = subtask.getEpicId();
        Subtask oldSubtask = subtasks.get(subtaskId);
        if (!subtasks.containsKey(subtaskId) || oldSubtask.getEpicId() != epicId) {
            return null;
        }
        Epic epic = epics.get(epicId);
        epic.removeSubtask(oldSubtask);
        subtasks.replace(subtaskId,subtask);
        epic.addSubtask(subtask);
        updateEpicsStatus(epic);
        return subtask;
    }

    // Получение списков
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Subtask> getEpicSubtasks(int id) {
        return epics.get(id).getSubtaskList();
    }

    // Получение по идентификатору
    public Task getTaskByID(int id) {
        return tasks.get(id);
    }

    public Epic getEpicByID(int id) {
        return epics.get(id);
    }

    public Subtask getSubtaskByID(int id) {
        return subtasks.get(id);
    }

    // Удаление по идентификатору
    public void removeTaskByID(int id) {
        tasks.remove(id);
    }

    public void removeEpicByID(int id) {
        if(!epics.containsKey(id)) {
            return;
        }
        ArrayList<Subtask> subtaskList = epics.get(id).getSubtaskList();
        for (Subtask subtask : subtaskList) {
            subtasks.remove(subtask.getId());
        }
        epics.remove(id);
    }

    public void removeSubtaskByID(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask == null) {
            return;
        }
        int epicId = subtask.getEpicId();
        subtasks.remove(id);
        Epic epic = epics.get(epicId);
        epic.removeSubtask(subtask);
    }

    // Удаление всех задач
    public void deleteTasks() {
        tasks.clear();
    }

    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtasks();
            updateEpicsStatus(epic);
        }
    }

    // Изменения статуса
    private void updateEpicsStatus(Epic epic) {
        int countDoneTask = 0;
        int countNewTask = 0;
        ArrayList<Subtask> subtasksList = epic.getSubtaskList();
        for (Subtask subtask : subtasksList) {
            if (subtask.getStatus() == TaskStatus.DONE) {
                countDoneTask++;
            }
            if (subtask.getStatus() == TaskStatus.NEW) {
                countNewTask++;
            }
        }
        if (countNewTask == subtasksList.size()) {
            epic.setStatus(TaskStatus.NEW);
        } else if (countDoneTask == subtasksList.size()) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}

