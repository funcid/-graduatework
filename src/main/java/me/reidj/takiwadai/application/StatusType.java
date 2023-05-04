package me.reidj.takiwadai.application;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatusType {
    SEND("Отправлено"),
    IN_WORK("В работе"),
    CORRECTED("Выполнено"),
    DENIED("Отказ")
    ;

    private final String title;

    public String getTitle() {
        return title;
    }
}
