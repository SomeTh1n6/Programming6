package com.commands;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.worker.Worker;

import java.io.*;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class Load {
    /** Загружает информацию из файла в коллекцию
     * @exception JsonSyntaxException ошибка синтаксиса , считываемого файла
     * @exception FileNotFoundException файл не найден
     * @exception SecurityException ошибка прав доступа
     * @param jsonFile файл из которого производится загрузка
     * @param workers коллекция куда мы загружаем информацию из файла
     * @param gson парсер
     * */
    public void execute(File jsonFile, LinkedList<Worker> workers, Gson gson) throws JsonSyntaxException, FileNotFoundException, SecurityException {

        // Добавление строк
        StringBuilder result = new StringBuilder();

        // Задается, что мы десериализируем из JSON
        Type collectionType = new TypeToken<LinkedList<Worker>>(){}.getType();

        try {
            if (!jsonFile.exists()) throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Файла по указанному пути не существует.");
        }
        try {
            if (!jsonFile.canRead() || !jsonFile.canWrite()) throw new SecurityException();
        } catch (SecurityException ex) {
            System.out.println("Файл защищён от чтения и/или записи. Для работы программы нужны оба разрешения.");
            System.exit(1);
        }
        try {
            if (jsonFile.length() == 0) throw new JsonSyntaxException("");
        } catch (JsonSyntaxException ex) {
            System.out.println("Файл пуст.");
            System.exit(1);
        }
        try {
            // создаем объект FileReader для объекта File
            FileReader fr = new FileReader(jsonFile);

            // создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();
            while (line != null) {
                result.append(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл, по заданному пути, не найден");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            LinkedList<Worker> addedWorker = gson.fromJson(result.toString(), collectionType);
            for (Worker s: addedWorker) {
                if (!workers.contains(s)){
                    try {
                        if (!s.getId().equals(null) && !s.getName().equals(null) && !s.getCreationDate().equals(null) && !s.getSalary().equals(null) &&
                                !s.getOrganizationAdress().equals(null) && !s.getOrganizationAnnualTurnover().equals(null) && !s.getX().equals(null)
                                && !s.getY().equals(null) && !s.getStatus().equals(null) && !s.getPosition().equals(null) && !s.getAdress().equals(null)){
                            workers.add(s);
                        }
                    }catch (NullPointerException e){
                    }
                }
            }
        }catch (JsonSyntaxException e){
            System.out.println("Синтанкис, считываемого файла неверен");
            System.exit(1);
        }
    }
}
