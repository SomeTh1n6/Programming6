package com.commands;

import com.CollectionManager;
import com.worker.Worker;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class Clear {

    /** Очищает коллекцию
     * @param workers коллекция, оторая очищается
     */
    public void execute(LinkedList<Worker> workers) {
        workers.clear();
    }
}

