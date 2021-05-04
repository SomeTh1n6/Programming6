package com.commands;

import com.worker.Worker;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterStartsWithName {
    /** Выводит на печать только те элементы коллекци в которых поле name начинается с заданной подстроки
     * Игнорирует верхний и нижний регистр
     * Если ничего не найдено, выводится сообщение об этом
     * @param name параметр по которому проводится поиск
     * @param workers коллекция, с которой ведется работа
     */
    public String execute(LinkedList<Worker> workers,String name){
        boolean flag = false;
        StringBuffer sb = new StringBuffer();
        for (Worker worker: workers) {
            Pattern pattern = Pattern.compile("^" + name,Pattern.CASE_INSENSITIVE); // почему то не игнорируется регистр у русского алфавита. С правильным регистром поиск работает
            Matcher matcher = pattern.matcher(worker.getName());
            if (matcher.find()){
                sb.append(worker.toString()).append("\n");
                flag = true;
            }
        }
        if (!flag)
            sb.append("Ничего не найдено");
        return sb.toString();
    }
}
