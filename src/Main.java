import manager.*;
import tasks.*;

public class Main {

    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();

        manager.addTask(new Task("Задача №1", "---", TaskStatus.DONE)); //5/ *отметка для новых строк из 5 спринта* Добавим для отображения статистики простых Задач
        manager.addTask(new Task("Задача №2", "---", TaskStatus.NEW));
        manager.addTask(new Task("Задача №3", "---", TaskStatus.NEW));
        manager.addTask(new Task("Задача №4", "---", TaskStatus.DONE));

        Epic epic1 = new Epic("Эпик №1", "!!!");
        manager.addEpic(epic1);

        Subtask subtask11 = new Subtask("Эпик1 Подзадача1", "!----!", TaskStatus.DONE, epic1.getId());
        manager.addSubtask(subtask11);
        Subtask subtask12 = new Subtask("Эпик1 Подзадача2", "!----!", TaskStatus.IN_PROGRESS, epic1.getId());
        manager.addSubtask(subtask12);
        Subtask subtask13 = new Subtask("Эпик1 Подзадача3", "!----!", TaskStatus.NEW, epic1.getId());
        manager.addSubtask(subtask13);
        Subtask subtask12New = new Subtask("Эпик1 Подзадача2 изменена", "!----!", subtask12.getId(), TaskStatus.NEW, epic1.getId());
        manager.updateSubtask(subtask12New);

        Epic epic2 = new Epic("Эпик №2", "!!!");
        manager.addEpic(epic2);

        System.out.println("Все Эпики :");
        System.out.println(manager.getEpics());
        System.out.println("Все Задачи :");
        System.out.println(manager.getTasks());
        System.out.println("Все Подзадачи :");
        System.out.println(manager.getSubtasks());

        System.out.println("История :");
        System.out.println(manager.getHistory());

        System.out.println("Запрос задачи : " + manager.getTaskByID(1));
        System.out.println("Запрос задачи : " + manager.getEpicByID(epic1.getId()));
        System.out.println("Запрос задачи : " + manager.getSubtaskByID(subtask13.getId()));

        System.out.println("История :");
        System.out.println(manager.getHistory());

        manager.deleteEpics();
        manager.deleteTasks();
        manager.deleteSubtasks();

        System.out.println("Итого Эпиков : " + manager.getEpics());
        System.out.println("Итого Задач : " + manager.getTasks());
        System.out.println("Итого Подзадач : " + manager.getSubtasks());

    }
}
