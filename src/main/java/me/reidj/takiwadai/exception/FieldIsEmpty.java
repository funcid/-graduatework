package me.reidj.takiwadai.exception;

import me.reidj.takiwadai.App;

import java.util.Arrays;

public class FieldIsEmpty {

    public boolean check(String... strings) {
        return Arrays.stream(strings).anyMatch(s -> s == null || s.isEmpty());
    }

    public void alert() {
        App.getApp().getRegistrationScene().errorAlert("Пожалуйста, заполните все поля",
                "Поля не могут быть пустыми!");
    }
}
