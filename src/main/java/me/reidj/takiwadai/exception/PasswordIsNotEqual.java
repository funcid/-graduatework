package me.reidj.takiwadai.exception;

import me.reidj.takiwadai.App;

public class PasswordIsNotEqual implements Exception {

    @Override
    public boolean check(String[] strings) {
        return !strings[4].equals(strings[5]);
    }

    @Override
    public void alert() {
        App.getApp().getRegistrationScene().errorAlert("Пожалуйста, проверьте правильность введённого пароля!",
                "Ваши пароли не совпадают!");
    }
}
