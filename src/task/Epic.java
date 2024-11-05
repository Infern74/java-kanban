package task;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Subtask> subtaskList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, int id, TaskStatus status) {
        super(name, description, id, status);
    }

    public void addSubtask(Subtask subtask) {
        subtaskList.add(subtask);
    }

    public  ArrayList<Subtask> getSubtaskList() {
        return subtaskList;     // Не совсем понял пункт по поводу доступа к этой переменной
    }

    public void removeSubtask(Subtask subtask) {
        subtaskList.remove(subtask);
    }

    public void clearSubtasks() {
        subtaskList.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", subtaskList.size=" + subtaskList.size() +
                ", status=" + getStatus() +
                "}";
    }
}
