package com;

import com.utility.Request;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CommandHandler {

    public String run(CollectionManager manager, Request userMessage) throws FileNotFoundException {
        String response;
        try {
            switch (userMessage.getCommand().trim()){
                case "":
                    response = "";
                    break;
                case "help":
                    response = manager.help();
                    break;
                case "show":
                    response = manager.show();
                    break;
                case "clear":
                    response = manager.clear();
                    break;
                case "info":
                    response = (manager.toString());
                    break;
                case "average_of_salary":
                    response =  (manager.average_of_salary());
                    break;
                case "head":
                    response = (manager.head());
                    break;
                case "remove_head":
                    response = (manager.remove_head());
                    break;
                case "update":
                    response = manager.update(userMessage.getWorker(), Integer.parseInt(userMessage.getArgument()));
                    break;
                case "add":
                    response = (manager.add(userMessage.getWorker()));
                    break;
                case "history":
                    response = "История команд:\n";
                    break;
                case "remove_by_id":
                    response = manager.removeById(Integer.parseInt(userMessage.getArgument()));
                    break;
                case "filter_starts_with_name":
                    response = manager.filterStartsWithName(userMessage.getArgument());
                    break;
                case "print_ascending":
                    response = manager.printAscending(userMessage.getArgument());
                    break;
                case "execute_script":
                    response = manager.executeScript(manager, userMessage.getScript());
                    System.out.println(response);
                    break;
                default:
                    response = "Неопознанная команда, введите \"help\" для справки по командам";
                    break;
            }
        }catch (ArrayIndexOutOfBoundsException | NullPointerException | IOException e){
            response = "Отсутствует аргумент";
        }

        return response;
    }
}
