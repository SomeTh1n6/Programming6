package com.commands;

import java.util.*;

public class Help {
    public String execute(HashMap<String,String> manual) {
        StringBuffer sb = new StringBuffer();
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_PURPLE = "\u001B[35m";
        final String ANSI_RESET = "\u001B[0m";
        manual.forEach((key, value) -> sb.append(ANSI_GREEN).append(key).append(" - ")
                .append(ANSI_PURPLE).append(value).append("\n").append(ANSI_RESET));
        return sb.toString();
    }
}
