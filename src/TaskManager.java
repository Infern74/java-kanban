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
        ArrayList<Subtask> oldEpicSubtaskList = oldEpic.getSubtaskList();
        if (!oldEpicSubtaskList.isEmpty()) {
            for (Subtask subtask : oldEpicSubtaskList) {
                subtasks.remove(subtask.getId());
            }
        }
        epics.replace(epicId,epic);
        updateEpicsStatus(epic);
        return epic;
    }

    public Subtask updateSubtask(Subtask subtask) {
        int subtaskId = subtask.getId();
        if (!subtasks.containsKey(subtaskId)) {
            return null;
        }
        int epicId = subtask.getEpicId();
        Subtask oldSubtask = subtasks.get(subtaskId);
        subtasks.replace(subtaskId,subtask);
        Epic epic = epics.get(epicId);
        ArrayList<Subtask> subtaskList = epic.getSubtaskList();
        subtaskList.remove(oldSubtask);
        subtaskList.add(subtask);
        epic.setSubtaskList(subtaskList);
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

    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        return epic.getSubtaskList();
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
        ArrayList<Subtask> subtaskList = epics.get(id).getSubtaskList();
        epics.remove(id);
        for (Subtask subtask : subtaskList) {
            subtasks.remove(subtask.getId());
        }
    }

    public void removeSubtaskByID(int id) {
        Subtask subtask = subtasks.get(id);
        int epicId = subtask.getEpicId();
        subtasks.remove(id);
        Epic epic = epics.get(epicId);
        ArrayList<Subtask> subtasksList = epic.getSubtaskList();
        subtasksList.remove(subtask);
        epic.setSubtaskList(subtasksList);
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
            epic.setStatus(TaskStatus.NEW);
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
        if (countDoneTask == subtasksList.size()) {
            epic.setStatus(TaskStatus.DONE);
        } else if (countNewTask == subtasksList.size()) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}

