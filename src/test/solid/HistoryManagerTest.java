package solid;

import manager.*;
import tasks.*;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class HistoryManagerTest {

    @Test
    void newHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "Менеджер не проинициализирован");
    }

    @Test
    void checkMaxSizeOfRequestHistory() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task("Task1", "description", TaskStatus.NEW);
        final int sizeFromRequestHistoryShouldBe = 10;
        final int sizeForCheckRequestSize = 10;
        for (int i = 0; i <= sizeForCheckRequestSize + 20; i++) {
            historyManager.add(task);
        }
        List<Task> exampleOfRequestHistoryList = historyManager.getHistory();

        assertEquals(sizeFromRequestHistoryShouldBe, exampleOfRequestHistoryList.size(), "Ограничение листа "
                + "не работает");
    }

    @Test
    void add() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task("Task1", "description", TaskStatus.NEW);
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

}