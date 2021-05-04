package com.commands;

import com.worker.Worker;
import com.worker.properties.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Scanner;

public class UpdateExecuteScript {
    IsNumber checkNumber = new IsNumber();
    /** Изменение данных работника по заданному id
     * Удаляем по id и создаем новый объект с тем же id и LocalDateTime,
     * но уже с новыми данными, которые вводятся вручную
     * @param id id , по которому будут вноситься изменения
     * */
    public String  execute(LinkedList<Worker> workers,int id, Scanner scanner){

        Worker workerDelete = null;
        LocalDateTime localDateTime = null;
        String line;
        boolean flag = false;
        String name = null;
        Position position = null;
        Status status = null;
        int x = 0;
        float y = 0;
        double salary = 0;

        // Нахождение работника , которого надо удалить
        for (Worker worker: workers) {
            if (worker.getId().equals(id)) {
                localDateTime = worker.getCreationDate();
                workerDelete = worker;
                flag = true;
                break;
            }
        }

        if (!flag){
            return "С заданным id не найдено работника";
        }

        workers.remove(workerDelete); // удаление работника

        return new AddExecuteScript().execute(workers,scanner);
    }


}
