package com.commands;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.worker.Worker;
import java.io.*;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class Save {
    /**Сохранение в файл
     * @param gson парсер
     * @param workers коллекция, откуда берется информация для записи в файл
     * @exception FileNotFoundException файл по заданному пути не найден
     * */
    public void execute(Gson gson, LinkedList<Worker> workers,String data) throws FileNotFoundException {
        Type collectionType = new TypeToken<LinkedList<Worker>>(){}.getType();
        String workerString = gson.toJson(workers,collectionType); // преобразуем в строку-JSON коллекцию


        try {
            FileWriter fstream1 = new FileWriter(data);// конструктор с одним параметром - для перезаписи
            BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
            out1.write(""); // очищаем, перезаписав поверх пустую строку
            out1.close(); // закрываем
        } catch (Exception e) {
            System.err.println("Ошибка в очистке файла: " + e.getMessage());
        }

        PrintWriter writer = new PrintWriter(new File("H:\\Pgogramming_labs\\2_semestr\\Lab6_new\\Lab6\\server\\src\\com\\data\\data.json"));

        writer.write(workerString);
        writer.flush();
        writer.close();

        System.out.println("Данные успешно сохранены");
    }

}