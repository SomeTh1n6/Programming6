package com.commands;

import com.CollectionManager;
import com.worker.Worker;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.logging.Logger;

public class Add {
    private Integer id;
    public static final Logger logger = Logger.getLogger(Add.class.getName());
    /***
     * Добавление готового Worker в коллекцию, генерация Id и LocalDateTime
     * @param workers непосредственно коллекция , куда добавляем
     * @param worker что добавляем
     */
    public void execute(LinkedList<Worker> workers, Worker worker){
        TreeSet<Integer> usedId = new TreeSet<>();
        if (!workers.isEmpty()) {
            for (Worker worker1 :
                    workers) {
                usedId.add(worker1.getId());
            }
            if (usedId.size() == 2147483647) {
                System.out.println("Невозможно добавить нового работника, чтобы продолжить добаление , требуется удалить кого то из сотрудников\n" +
                        "Превышено максимальное количество работников , находящихся в базе");
                System.exit(1);
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

        worker.setId(id);
        worker.setCreationDate(new RandomLocalDateTime().randomLocalDateTime());

        workers.add(worker);
        workers.sort(Worker::compareTo);
    }
}
