package api.handlers;

import api.BaseHttpHandler;
import api.GsonFactory;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.InMemoryTaskManager;
import tasks.Task;

import java.io.IOException;
import java.util.List;

public class TasksHandler extends BaseHttpHandler implements HttpHandler {
    private final InMemoryTaskManager taskManager;
    private final Gson gson;

    public TasksHandler(InMemoryTaskManager taskManager) {
        this.taskManager = taskManager;
        this.gson = GsonFactory.getGson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET":
                    String query = exchange.getRequestURI().getQuery();
                    if (query != null && query.contains("id=")) {
                        handleGetTaskById(exchange);
                    } else {
                        handleGetTasks(exchange);
                    }
                    break;
                case "POST":
                    handlePostTask(exchange);
                    break;
                case "DELETE":
                    handleDeleteTask(exchange);
                    break;
                default:
                    sendText(exchange, "Метод не поддерживается.", 405);
            }
        } catch (Exception e) {
            sendInternalError(exchange);
        }
    }

    private void handleGetTasks(HttpExchange exchange) throws IOException {
        List<Task> tasks = taskManager.getTasks();
        String response = gson.toJson(tasks);
        sendText(exchange, response, 200);
    }

    private void handlePostTask(HttpExchange exchange) throws IOException {
        try {
            Task task = readRequestBody(exchange, Task.class);

            if (task.getStartTime() != null && taskManager.isTaskOverlapping(task, task.getId())) {
                sendHasInteractions(exchange); // Отправляем 406, если задача пересекается
                return;
            }

            if (task.getId() == 0) {
                taskManager.addTask(task);
                String response = gson.toJson(task);
                sendText(exchange, response, 201);
            } else {
                taskManager.updateTask(task);
                String response = gson.toJson(task);
                sendText(exchange, response, 201);
            }
        } catch (Exception e) {
            sendInternalError(exchange);
        }
    }

    private void handleDeleteTask(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null) {
            taskManager.deleteTasks();
            sendText(exchange, "Все задачи удалены.", 200);
        } else {
            try {
                int id = Integer.parseInt(query.split("=")[1]);
                taskManager.removeTaskByID(id);
                sendText(exchange, "Задача удалена.", 200);
            } catch (NumberFormatException e) {
                sendNotFound(exchange);
            }
        }
    }

    private void handleGetTaskById(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null) {
            sendNotFound(exchange);
            return;
        }

        try {
            int id = Integer.parseInt(query.split("=")[1]);
            Task task = taskManager.getTaskByID(id);
            if (task != null) {
                String response = gson.toJson(task);
                sendText(exchange, response, 200);
            } else {
                sendNotFound(exchange);
            }
        } catch (NumberFormatException e) {
            sendNotFound(exchange);
        }
    }
}