package com.commands;

import com.worker.Worker;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class Update {

    private boolean flag = false;
    private Worker workerDelete = null;
    LocalDateTime localDateTime;
    public String execute(LinkedList<Worker> workers,Worker worker ,int id){
        for (Worker w: workers) {
            if (w.getId().equals(id)) {
                localDateTime = w.getCreationDate();
                workerDelete = w;
                flag = true;
                break;
            }
        }

        if (!flag){
            return "По заданному id работника не найдено";
        }

        workers.remove(workerDelete); // удаление работника

        worker.setId(id);
        worker.setCreationDate(localDateTime);

        workers.add(worker);
        workers.sort(Worker::compareTo);
        return "Элемент успешно изменен";
    }
}
