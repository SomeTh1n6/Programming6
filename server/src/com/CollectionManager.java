package com;

import com.commands.*;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.utility.Request;
import com.worker.*;

import java.io.*;
import java.util.*;

public class CollectionManager {
    /** Файл с данными*/
    private File jsonFile;

    /** Тут хранятся все данные о работниках */
    public LinkedList<Worker> workers = new LinkedList<>();

    /** Список доступных команд (ключ - команда, значение - информация о ней) */
    public static HashMap<String, String> manual;

    /** Дата инициализации коллекции */
    //private final Date initDate;

    /** Парсинг JSON в Коллекцию/класс и обратно */
    private final Gson gson;

    /** Дата инициализации коллекции */
    private final Date initDate;

    String data;

    {
        manual = new HashMap<>();
        gson = new Gson();
        manual.put("help","вывести справку по доступным командам");
        manual.put("info","вывести в стандартный поток вывода информацию о " +
                "коллекции (тип, дата инициализации, количество элементов и т.д.)");
        manual.put("show","вывести в стандартный поток вывода все элементы коллекции " +
                "в строковом представлении");
        manual.put("add","добавить новый элемент в коллекцию");
        manual.put("update id","обновить значение элемента коллекции, id " +
                "которого равен заданному");
        manual.put("remove_by_id id","удалить элемент из коллекции по его id");
        manual.put("execute_script file_name","считать и исполнить скрипт из указанного файла. Указать абсолютный путь к файлу " +
                "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
        manual.put("exit","завершить сессию");
        manual.put("head","вывести первый элемент коллекции");
        manual.put("remove_head","вывести первый элемент коллекции и удалить его");
        manual.put("history","вывести последние 6 команд (без их аргументов)");
        manual.put("average_of_salary","вывести среднее значение поля salary для всех элементов коллекции");
        manual.put("filter_starts_with_name name","вывести элементы, значение поля name которых " +
                "начинается с заданной подстроки");
        manual.put("print_ascending","вывести элементы коллекции в порядке возрастания (сортировка по заданным параметрам)");
    }

    public CollectionManager(String collectionPath) throws FileNotFoundException {
        data = collectionPath;
        try {
            if (collectionPath == null) throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Путь до json файла нужно передать через переменную окружения collectionPath.");
            System.exit(1);
        }
        File file = new File(collectionPath);
        try {
            if (file.exists()) this.jsonFile = new File(collectionPath);
            else throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Файл по указанному пути не существует.");
            System.exit(1);
        }
        this.initDate = new Date();
        this.load();
    }

    public void load() throws JsonSyntaxException, FileNotFoundException, SecurityException {
        Load load = new Load();
        load.execute(jsonFile,workers,gson);
    }

    /** Выводит на печать информацию о доступных командах
     */
    public String help() {
        Help help = new Help();
        return(help.execute(manual));
    }

    public String addES(Scanner scanner){
        return new AddExecuteScript().execute(workers,scanner);
    }

    public String updateES(int id, Scanner scanner){
        return new UpdateExecuteScript().execute(workers,id,scanner);
    }
    /** Выводит на печать все элементы коллекции
     */
    public String show(){
        Show show = new Show(workers);
        return (show.execute());
    }

    public String executeScript(CollectionManager manager, String script) throws IOException {
        return new ExecuteScript().execute(manager, script);
    }

    public String printAscending(String condition){
        return new PrintAscending().execute(workers,condition);
    }

    public String history(Queue<String> history){
        History history1 = new History();
        return (history1.execute(history));
    }

    public String removeById(int id){
        RemoveById removeById = new RemoveById();
        return removeById.execute(workers,id);
    }

    public String filterStartsWithName(String name){
        return new FilterStartsWithName().execute(workers, name);
    }

    public String update(Worker worker , int id){
        Update update = new Update();
        return update.execute(workers,worker,id);
    }

    /** Проверка строки, введенной пользователем, является ли она командой
     * @param userCommand - строка на проверку
     * @return значение boolean
     */

    public boolean checkCommand(String userCommand) {
        for (Map.Entry<String, String> pair : manual.entrySet()) {
            String[] finalUserCommand = pair.getKey().trim().split(" ", 2);
            if (userCommand.equals(finalUserCommand[0])) {
                return true;
            }
        }
        return false;
    }

    /** Очищает коллекцию
     */
    public String clear(){
        Clear clear = new Clear();
        clear.execute(workers);
        return ("Коллекция успешно очищена");
    }

    /** Выводит на печать первый элемент в коллекции
     */
    public String  head() {
        Head head = new Head();
        return (head.execute(workers));
    }

    /** Удаляет первый элемент в коллекции, если она не пуста
     */
    public String remove_head(){
        RemoveHead removeHead = new RemoveHead();
        return (removeHead.execute(workers));
    }

    public String add(Worker worker){
        Add add = new Add();
        add.execute(workers,worker);
        return "Элемент успешно добавлен";
    }

    /** Подсчет среднего значения по зарплате среди всех работников. 2 знака после запятой
     * **/
    public String average_of_salary(){
        AverageOfSalary averageOfSalary = new AverageOfSalary();
        return (averageOfSalary.execute(workers));
    }

    public void save() throws FileNotFoundException {
        Save save = new Save();
        save.execute(gson,workers,data);
    }

    /** Возвращает строку с информацией об объекте
     **/
    @Override
    public String toString() {
        return "Тип коллекции: " + workers.getClass() +
                "\nДата инициализации: " + initDate +
                "\nКоличество элементов: " + workers.size();
    }
}
