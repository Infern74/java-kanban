package solid;

import manager.*;
import tasks.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TaskManagerTest {

    private TaskManager manager;

    @BeforeEach
    void beforeEach() {

        manager = Managers.getDefault();

    }

    @Test
    public void testEqualsTask() {

        Task task1 = new Task("Task1", "description", TaskStatus.NEW);
        Task task2 = new Task("Task2", "description", TaskStatus.NEW);
        assertNotEquals(task1, task2);

    }

    @Test
    void testEqualsSubtask() {

        Epic epic1 = new Epic("Epic1", "description");
        manager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask1", "description", TaskStatus.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Subtask2", "description", TaskStatus.NEW, epic1.getId());

        assertNotEquals(subtask1, subtask2);

    }

    @Test
    void testEqualsEpic() {

        Epic epic1 = new Epic("Epic1", "description");
        Epic epic2 = new Epic("Epic2", "description");

        assertNotEquals(epic1, epic2);

    }

    @Test
    void testTasksEqualityById() {

        Task testTask = new Task("Task1", "description", TaskStatus.NEW);
        manager.addTask(testTask);
        assertEquals(testTask, manager.getTaskByID(testTask.getId()));

    }

    @Test
    void testSubtaskEqualityById() {

        Epic epic1 = new Epic("Epic1", "description");
        manager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask1", "description", TaskStatus.NEW, epic1.getId());

        manager.addSubtask(subtask1);
        assertEquals(subtask1, manager.getSubtaskByID(subtask1.getId()));

    }

    @Test
    void testEpicEqualityById() {

        Epic testEpic = new Epic("Epic1", "description");
        manager.addEpic(testEpic);
        assertEquals(testEpic, manager.getEpicByID(testEpic.getId()));

    }

    @Test
    void checkForIdConflicts() {

        Task testTask1 = new Task("Task1", "description", TaskStatus.NEW);
        Task testTask2 = new Task("Task1", "description", TaskStatus.NEW);

        manager.addTask(testTask1);
        manager.addTask(testTask2);

        assertNotEquals(manager.getTaskByID(testTask1.getId()), manager.getTaskByID(testTask2.getId()));

    }

    @Test
    void checkHistoryManagerSavesTaskVersions() {

        Task checkTask = new Task("Task1", "description", TaskStatus.NEW);
        manager.addTask(checkTask);
        manager.getTaskByID(checkTask.getId());
        Task testTask = new Task("Task1", "description", checkTask.getId(), TaskStatus.IN_PROGRESS);
        manager.updateTask(testTask);
        manager.getTaskByID(checkTask.getId());
        assertEquals(checkTask, manager.getHistory().getFirst());

    }

    @Test
    void addNewTask() {

        Task task = new Task("Task1", "description", TaskStatus.NEW);
        manager.addTask(task);

        final int taskId = task.getId();
        final Task savedTask = manager.getTaskByID(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = manager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void addNewSubtask() {
        TaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Epic1", "description");
        taskManager.addEpic(epic);

        final int epicId = epic.getId();
        final Epic savedEpic = taskManager.getEpicByID(epicId);

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        final List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "Задачи не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.getFirst(), "Задачи не совпадают.");

    }

    @Test
    public void deleteAllTasks() {

        manager.deleteTasks();
        List<Task> tasks = manager.getTasks();
        assertEquals(0, tasks.size());

    }

    @Test
    public void deleteAllEpics() {

        manager.deleteEpics();
        List<Epic> tasks = manager.getEpics();
        assertEquals(0, tasks.size());

    }

    @Test
    public void deleteAllSubtask() {

        manager.deleteSubtasks();
        List<Subtask> tasks = manager.getSubtasks();
        assertEquals(0, tasks.size());

    }
}