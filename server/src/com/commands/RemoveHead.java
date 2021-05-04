package com.commands;

import com.worker.Worker;

import java.util.LinkedList;

public class RemoveHead {
    /** Удаляет первый элемент в коллекции, если она не пуста
     * @param workers коллекция, с которой ведется работа
     */
    public String execute(LinkedList<Worker> workers){
        if (!workers.isEmpty()){
            workers.remove(workers.getFirst());
            return ("Сотрудник успешно удален");
        }
        else
            return ("Коллекция пуста!");
    }
}
