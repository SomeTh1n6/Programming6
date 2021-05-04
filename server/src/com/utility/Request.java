package com.utility;

import com.worker.Worker;

import java.io.Serializable;

public class Request implements Serializable {

    private String command;
    private String argument;
    private Worker worker;
    private String script;

    public Request(String command, String argument){
        this.command = command;
        this.argument = argument;
    }

    public Request(String command, String argument ,String script){
        this.command = command;
        this.argument = argument;
        this.script = script;
    }

    public Request(String command){
        this.command = command;
    }

    public Request(String command,Worker worker){
        this.command = command;
        this.worker = worker;
    }

    public Request(String command,String argument,Worker worker){
        this.command = command;
        this.argument = argument;
        this.worker = worker;
    }

    public String getCommand(){
        return this.command;
    }

    public String getArgument(){
        return this.argument;
    }


    public Worker getWorker(){
        return this.worker;
    }

    public String getScript() {
        return this.script;
    }

    @Override
    public String toString(){
        return ("Запрос : " +
                "\n   команда - " + getCommand() +
                " \n   аргумент команды - " + getArgument() +
                " \n   скрипт - " + getScript() +
                " \n   сотрудник : " + getWorker());
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request that = (Request) o;
        return command.equals(that.command);
    }

}
