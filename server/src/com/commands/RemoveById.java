package com.commands;

import com.worker.Worker;

import java.util.LinkedList;

public class RemoveById {
    /**Удаление по id
     * @param workers коллекция с которой проводится работа
     * @param id id по которому производится удаление
     * */
    public String execute(LinkedList<Worker> workers, int id){
        boolean flag = workers.stream().anyMatch(worker -> worker.getId().equals(id));
        if (!flag)
            return "По заданому id сотрудника не найдено";
        else {
            workers.removeIf(p -> p.getId().equals(id));
            return "Сотрудник с id " + id + " успешно удален";
        }
    }
}
