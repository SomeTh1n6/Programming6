package com.commands;

import com.worker.Worker;
import com.worker.properties.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeSet;

public class AddExecuteScript {

    IsNumber checkNumber = new IsNumber();
    RandomLocalDateTime randomLocalDateTime = new RandomLocalDateTime();
    Status status = null;

    /** Добавление нового сотрудника в коллекцию
     * @param workers коллекция, куда мы добавляем работника
     * */
    public String execute(LinkedList<Worker> workers, Scanner scanner) {

        String line = null;
        String name = "NoName";
        Integer id = null;
        int x = 0;
        float y = 0;
        double salary = 1;
        Position position = null;

        // Выдаем уникальный id
        TreeSet<Integer> usedId = new TreeSet<>();
        if (!workers.isEmpty()) {
            for (Worker worker :
                    workers) {
                usedId.add(worker.getId());
            }
            if (usedId.size() == 2147483647) {
                return "Невозможно добавить нового работника, чтобы продолжить добаление , требуется удалить кого то из сотрудников\n" +
                        "Превышено максимальное количество работников , находящихся в базе";
            }
            int originalSize = usedId.size();
            for (int i = 0; i < 2147483647; i++) {
                usedId.add(i + 1);
                if (usedId.size() > originalSize) {
                    id = i + 1;
                    break;
                }
            }
        } else id = 1;

        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            String[] finalUserCommand = line.trim().split(" ");
            while (line.trim().equals("") || finalUserCommand.length > 1) {
                if(scanner.hasNextLine())
                    line = scanner.nextLine();
                else {
                    System.out.println("Непредвиденная ошибка");
                    System.exit(1);
                }
            }
            name = line;
        } else {
            System.out.println("Неверное имя");
            System.exit(1);
        }

        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            // проверка на число int
            while (!checkNumber.isNumberInteger(line)) {
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                }
                else {
                    System.out.println("Неверное значение координаты");
                    System.exit(1);
                }
            }
            x = Integer.parseInt(line);
        } else {
            System.out.println("Неверное имя");
            System.exit(1);
        }

        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            // проверка на число float
            while (!checkNumber.isNumberFloat(line)) {
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                } else {
                    System.out.println("Неверное значение координаты");
                    System.exit(1);
                }
            }
            y = Float.parseFloat(line);
        } else {
            System.out.println("Неверное значение координаты");
            System.exit(1);
        }

        Coordinates coordinates = new Coordinates(x, y);

        LocalDateTime localDateTime = randomLocalDateTime.randomLocalDateTime();

        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            while (!checkNumber.isNumberDouble(line) || Double.parseDouble(line) <= 0) {
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                } else {
                    System.out.println("Неверное значение зарплаты");
                    System.exit(1);
                }

            }
            salary = Double.parseDouble(line);
            System.out.println(salary);
        } else {
            System.out.println("Неверное значение зарплаты");
            System.exit(1);
        }
        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            switch (line.trim()) {
                case "1":
                    position = Position.ENGINEER;
                    break;
                case "2":
                    position = Position.HEAD_OF_DIVISION;
                    break;
                case "3":
                    position = Position.LEAD_DEVELOPER;
                    break;
                case "4":
                    position = Position.BAKER;
                    break;
                default:
                    position = Position.UNDEFINED;
            }
            System.out.println(position.getPosition());
        } else {
            System.out.println("Неверное значение");
            System.exit(1);
        }

        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            switch (line.trim()) {
                case "1":
                    status = Status.FIRED;
                    break;
                case "2":
                    status = Status.HIRED;
                    break;
                case "3":
                    status = Status.RECOMMENDED_FOR_PROMOTION;
                    break;
                case "4":
                    status = Status.REGULAR;
                    break;
                case "5":
                    status = Status.PROBATION;
                    break;
                default:
                    status = Status.UNDEFINED;
                    break;
            }
        } else {
            System.out.println("Неверное значение");
            System.exit(1);
        }
        Organization organization;
        OrganizationType organizationType = null;
        Address address = new Address("");
        double annualTurnover = 0;
        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            while (!checkNumber.isNumberDouble(line) || Double.parseDouble(line) <= 0) {
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                } else {
                    System.out.println("Неверное значение");
                    System.exit(1);
                }
            }
            annualTurnover = Double.parseDouble(line);
        } else {
            System.out.println("Неверное значение");
            System.exit(1);
        }

        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            switch (line.trim()) {
                case "1":
                    organizationType = OrganizationType.COMMERCIAL;
                    break;
                case "2":
                    organizationType = OrganizationType.PRIVATE_LIMITED_COMPANY;
                    break;
                case "3":
                    organizationType = OrganizationType.OPEN_JOINT_STOCK_COMPANY;
                    break;
                default:
                    organizationType = OrganizationType.UNDEFINED;
                    break;
            }
        }
        else {
            System.out.println("Неверное значение");
            System.exit(1);
        }

        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            while (line.trim().equals("")){
                if (scanner.hasNextLine()) {
                    System.out.println("Введите корректные данные");
                    line = scanner.nextLine();
                }
                else {
                    System.out.println("Неверное значение");
                    System.exit(1);
                }
            }
            address.setStreet(line);
        }
        else{
            System.out.println("Неверное значение");
            System.exit(1);
        }
        organization = new Organization(annualTurnover,organizationType,address);

        Worker worker = new Worker(id,name,coordinates,localDateTime,salary,position,status,organization);
        workers.add(worker); // добавляем в коллекцию только что созданный id
        workers.sort(Worker::compareTo);
        return "Успешно!";
    }
}
