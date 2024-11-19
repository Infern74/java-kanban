package manager;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> history = new ArrayList<>(10);

    // Добавление нового просмотра задачи в историю
    @Override
    public void add(Task task) {
        if (history.size() == 10) {
            history.removeFirst();
        }
        history.add(task);

    }

    // Получение истории просмотров
    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }

}