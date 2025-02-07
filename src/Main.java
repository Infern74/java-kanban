import manager.*;
import tasks.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        Main.args = args;

        File file = File.createTempFile("tasks", ".csv");
        System.out.println("Файл для тестов создан: " + file.getAbsolutePath());

        FileBackedTaskManager manager = new FileBackedTaskManager(file);

        Task task1 = new Task("Task 1", "Описание задачи 1", TaskStatus.NEW);
        manager.addTask(task1);

        Epic epic1 = new Epic("Epic 1", "Описание эпика 1");
        manager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask 1", "Описание подзадачи 1", TaskStatus.NEW, epic1.getId());
        manager.addSubtask(subtask1);

        manager.save();
        System.out.println("Задачи сохранены в файл.");

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(file);
        System.out.println("Задачи загружены из файла.");

        System.out.println("\nПроверка загруженных задач:");

        ArrayList<Task> loadedTasks = loadedManager.getTasks();
        for (Task task : loadedTasks) {
            System.out.println("Задача: " + task.getName() + " (ID: " + task.getId() + ")");
            System.out.println("Описание: " + task.getDescription());
            System.out.println("Статус: " + task.getStatus());
        }

        ArrayList<Epic> loadedEpics = loadedManager.getEpics();
        for (Epic epic : loadedEpics) {
            System.out.println("\nЭпик: " + epic.getName() + " (ID: " + epic.getId() + ")");
            System.out.println("Описание: " + epic.getDescription());
            System.out.println("Статус: " + epic.getStatus());

        }

        ArrayList<Subtask> loadedSubtasks = loadedManager.getSubtasks();
        for (Subtask subtask : loadedSubtasks) {
            System.out.println("\nПодзадача 1: " + subtask.getName() + " (ID: " + subtask.getId() + ")");
            System.out.println("Описание: " + subtask.getDescription());
            System.out.println("Статус: " + subtask.getStatus());
            System.out.println("ID эпика: " + subtask.getEpicId());
        }

        System.out.println("\nПодзадачи эпика 1:");
        for (Epic epic : loadedEpics) {
            for (Subtask subtask : loadedManager.getEpicSubtasks(epic.getId())) {
                System.out.println("- " + subtask.getName() + " (ID: " + subtask.getId() + ")");
            }
        }

        FileBackedTaskManager loadedManager2 = FileBackedTaskManager.loadFromFile(file);
        System.out.println("---------------------------------------------");

        ArrayList<Task> loadedTasks2 = loadedManager2.getTasks();
        for (Task task : loadedTasks2) {
            System.out.println("Задача: " + task.getName() + " (ID: " + task.getId() + ")");
            System.out.println("Описание: " + task.getDescription());
            System.out.println("Статус: " + task.getStatus());
        }

        ArrayList<Epic> loadedEpics2 = loadedManager2.getEpics();
        for (Epic epic : loadedEpics2) {
            System.out.println("\nЭпик: " + epic.getName() + " (ID: " + epic.getId() + ")");
            System.out.println("Описание: " + epic.getDescription());
            System.out.println("Статус: " + epic.getStatus());

        }

        ArrayList<Subtask> loadedSubtasks2 = loadedManager2.getSubtasks();
        for (Subtask subtask : loadedSubtasks2) {
            System.out.println("\nПодзадача 1: " + subtask.getName() + " (ID: " + subtask.getId() + ")");
            System.out.println("Описание: " + subtask.getDescription());
            System.out.println("Статус: " + subtask.getStatus());
            System.out.println("ID эпика: " + subtask.getEpicId());
        }

        System.out.println("\nПодзадачи эпика 1:");
        for (Epic epic : loadedEpics2) {
            for (Subtask subtask : loadedManager2.getEpicSubtasks(epic.getId())) {
                System.out.println("- " + subtask.getName() + " (ID: " + subtask.getId() + ")");
            }
        }

    }
    public static String[] args;
}


