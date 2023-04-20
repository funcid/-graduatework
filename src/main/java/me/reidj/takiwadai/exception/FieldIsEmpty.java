package me.reidj.takiwadai.exception;

import me.reidj.takiwadai.App;

public class FieldIsEmpty implements Exception {

    @Override
    public boolean check(String[] strings) {
        return strings[0].isEmpty() || strings[1].isEmpty() || strings[2].isEmpty() || strings[3].isEmpty() || strings[4].isEmpty() || strings[5].isEmpty();
    }

    @Override
    public void alert() {
        App.getApp().getRegistrationScene().errorAlert("Пожалуйста, заполните все поля",
                "Поля не могут быть пустыми!");
    }
}
