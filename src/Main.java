import manager.*;
import tasks.*;

public static void main(String[] args) {

    TaskManager manager = Managers.getDefault();

    Task task1 = new Task("Task 1", "Описание задачи 1", TaskStatus.NEW);
    Task task2 = new Task("Task 2", "Описание задачи 2", TaskStatus.NEW);
    manager.addTask(task1);
    manager.addTask(task2);

    Epic epic1 = new Epic("Epic 1", "Описание эпика 1");
    manager.addEpic(epic1);

    Subtask subtask1 = new Subtask("Subtask 1", "Описание подзадачи 1", TaskStatus.NEW, epic1.getId());
    Subtask subtask2 = new Subtask("Subtask 2", "Описание подзадачи 2", TaskStatus.NEW, epic1.getId());
    Subtask subtask3 = new Subtask("Subtask 3", "Описание подзадачи 3", TaskStatus.NEW, epic1.getId());
    manager.addSubtask(subtask1);
    manager.addSubtask(subtask2);
    manager.addSubtask(subtask3);

    Epic epic2 = new Epic("Epic 2", "Описание эпика 2");
    manager.addEpic(epic2);

    System.out.println("Запрос task1, epic1, subtask1, task2, epic2:");
    manager.getTaskByID(task1.getId());
    manager.getEpicByID(epic1.getId());
    manager.getSubtaskByID(subtask1.getId());
    manager.getTaskByID(task2.getId());
    manager.getEpicByID(epic2.getId());
    System.out.println(manager.getHistory());

    System.out.println("Запрос subtask2, task1, epic1:");
    manager.getSubtaskByID(subtask2.getId());
    manager.getTaskByID(task1.getId());
    manager.getEpicByID(epic1.getId());
    System.out.println(manager.getHistory());

    System.out.println("Запрос subtask3, epic2, task2:");
    manager.getSubtaskByID(subtask3.getId());
    manager.getEpicByID(epic2.getId());
    manager.getTaskByID(task2.getId());
    System.out.println(manager.getHistory());

    System.out.println("Удаляем task1:");
    manager.removeTaskByID(task1.getId());
    System.out.println(manager.getHistory());

    System.out.println("Удаляем epic1:");
    manager.removeEpicByID(epic1.getId());
    System.out.println(manager.getHistory());
}
