package manager;

import tasks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private int nextId;

    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, Subtask> subtasks;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        nextId = 1;
        historyManager = Managers.getDefaultHistory();
    }

    private int getNextId() {
        return nextId++;
    }

    // create
    @Override
    public Task addTask(Task task) {
        task.setId(getNextId());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Epic addEpic(Epic epic) {
        epic.setId(getNextId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
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

    // update
    @Override
    public Task updateTask(Task task) {
        int taskId = task.getId();
        if (!tasks.containsKey(taskId)) {
            return null;
        }
        tasks.replace(taskId, task);
        return task;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        int epicId = epic.getId();
        if (!epics.containsKey(epicId)) {
            return null;
        }
        Epic oldEpic = epics.get(epicId);
        oldEpic.setName(epic.getName());
        oldEpic.setDescription(epic.getDescription());
        return epic;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        int subtaskId = subtask.getId();
        int epicId = subtask.getEpicId();
        Subtask oldSubtask = subtasks.get(subtaskId);
        if (!subtasks.containsKey(subtaskId) || oldSubtask.getEpicId() != epicId) {
            return null;
        }
        Epic epic = epics.get(epicId);
        epic.removeSubtask(oldSubtask);
        subtasks.replace(subtaskId, subtask);
        epic.addSubtask(subtask);
        updateEpicsStatus(epic);
        return subtask;
    }

    // getList
    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            return null;
        }
        return epic.getSubtaskList();
    }

    // getById
    @Override
    public Task getTaskByID(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpicByID(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getSubtaskByID(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    // removeById
    @Override
    public void removeTaskByID(int id) {
        historyManager.remove(id);
        tasks.remove(id);
    }

    @Override
    public void removeEpicByID(int id) {
        if (!epics.containsKey(id)) {
            return;
        }
        ArrayList<Subtask> subtaskList = epics.get(id).getSubtaskList();
        for (Subtask subtask : subtaskList) {
            subtasks.remove(subtask.getId());
            historyManager.remove(subtask.getId());
        }
        historyManager.remove(id);
        epics.remove(id);
    }

    @Override
    public void removeSubtaskByID(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask == null) {
            return;
        }
        int epicId = subtask.getEpicId();
        subtasks.remove(id);
        historyManager.remove(id);
        Epic epic = epics.get(epicId);
        epic.removeSubtask(subtask);
        updateEpicsStatus(epic);
    }

    // deleteAll
    @Override
    public void deleteTasks() {
        for (Integer taskId : tasks.keySet()) {
            historyManager.remove(taskId);
        }
        tasks.clear();
    }

    @Override
    public void deleteEpics() {
        for (Integer epicId : epics.keySet()) {
            Epic epic = epics.get(epicId);
            for (Subtask subtask : epic.getSubtaskList()) {
                historyManager.remove(subtask.getId());
            }
            historyManager.remove(epicId);
        }
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        for (Integer subtaskId : subtasks.keySet()) {
            historyManager.remove(subtaskId);
        }
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtasks();
            updateEpicsStatus(epic);
        }
    }

    // updateStatus
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

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}

