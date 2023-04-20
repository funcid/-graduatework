package me.reidj.takiwadai.exception;

import me.reidj.takiwadai.App;

public class PasswordShort implements Exception {

    @Override
    public boolean check(String[] strings) {
        return strings[4].length() <= 6;
    }

    @Override
    public void alert() {
        App.getApp().getRegistrationScene().errorAlert("Пожалуйста, проверьте правильность введённого пароля!",
                "Ваш пароль должен быть длиннее 6 символов!");
    }
}
