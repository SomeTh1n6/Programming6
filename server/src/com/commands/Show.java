package com.commands;

import com.worker.Worker;

import java.util.LinkedList;
import java.util.stream.Stream;

public class Show {
    private LinkedList<Worker> workers;
    private String information = "";
    public Show(LinkedList<Worker> workers){
        this.workers = workers;
    }

    public Show(LinkedList<Worker> workers, String information){
        this.workers = workers;
        this.information = information;
    }

    /** Выводит на печать все элементы коллекции
     */
    public String execute(){
        StringBuffer sb = new StringBuffer();
        if (!information.equals("")){
            sb.append(information+"\n");
        }

        if (!workers.isEmpty()){
            workers.forEach(worker -> sb.append(worker.toString()));
        }
        else{
            sb.append("Коллекция пуста");
        }
        return sb.toString();
    }
}
