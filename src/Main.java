import task.*;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task hometask = new Task("Cделать дз", "По истории и информатике",TaskStatus.NEW);
        taskManager.addTask(hometask);

        Task readBook = new Task("Прочитать книгу", "Java epam", TaskStatus.NEW);
        taskManager.addTask(readBook);

        Task newReadBook = new Task("Прочитать книгу", "Java epam", readBook.getId(),
                TaskStatus.IN_PROGRESS);
        taskManager.updateTask(newReadBook);

        Epic project = new Epic("Проект","2 семестр");
        taskManager.addEpic(project);
        Subtask projectSubtask1 = new Subtask("План", "Составить этапы", TaskStatus.IN_PROGRESS,
                project.getId());
        Subtask projectSubtask2 = new Subtask("Разделить обязаности", "Подобрать необходимых людей",
                TaskStatus.NEW, project.getId());
        taskManager.addSubtask(projectSubtask1);
        taskManager.addSubtask(projectSubtask2);

        Epic cleaning = new Epic("Уборка","Навести полную чистоту");
        taskManager.addEpic(cleaning);
        Subtask cleaningSubtask1 = new Subtask("Помыть полы", "В каждой комнате",TaskStatus.DONE, cleaning.getId());
        taskManager.addSubtask(cleaningSubtask1);

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());

        taskManager.removeTaskByID(1);
        taskManager.removeEpicByID(6);
    }
}
