package com.commands;

import com.worker.Worker;

import java.util.LinkedList;

public class Head {
    /** Выводит на печать первый элемент в коллекции
     * @param workers коллекция, с которой ведется работа
     */
    public String execute(LinkedList<Worker> workers) {
        if (workers.isEmpty())
            return ("Ошибка. Коллекция пуста");
        else
            return ("Первый элемент - " + workers.getFirst());
    }
}
