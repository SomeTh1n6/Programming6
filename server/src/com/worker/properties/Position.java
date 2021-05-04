package com.worker.properties;

import java.io.Serializable;

public enum Position implements Serializable {
    ENGINEER("Инженер"),
    HEAD_OF_DIVISION("Начальник отдела"),
    LEAD_DEVELOPER("Ведущий разработчик"),
    BAKER("Пекарь"),
    UNDEFINED("-");

    private String position;

    Position(String position) {
        this.position = position;
    }

    /**@return должность, занимаемая в организации
     * */
    public String getPosition(){
        return position;
    }
}
