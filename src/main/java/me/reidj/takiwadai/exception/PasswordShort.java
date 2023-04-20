package me.reidj.takiwadai.exception;

import me.reidj.takiwadai.App;

public class PasswordShort {

    public boolean check(String password) {
        return password.length() <= 6;
    }

    public void alert() {
        App.getApp().getRegistrationScene().errorAlert("Пожалуйста, проверьте правильность введённого пароля!",
                "Ваш пароль должен быть длиннее 6 символов!");
    }
}
