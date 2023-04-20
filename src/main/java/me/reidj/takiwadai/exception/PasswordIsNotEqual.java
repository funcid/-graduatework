package me.reidj.takiwadai.exception;

import me.reidj.takiwadai.App;

public class PasswordIsNotEqual {

    public boolean check(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }

    public void alert() {
        App.getApp().getRegistrationScene().errorAlert("Пожалуйста, проверьте правильность введённого пароля!",
                "Ваши пароли не совпадают!");
    }
}
