package me.reidj.takiwadai.exception;

import me.reidj.takiwadai.App;
import me.reidj.takiwadai.util.Utils;

public class EmailIsIncorrect {

    public boolean check(String email) {
        return Utils.isRegularExpressionCheck(Utils.EMAIL_REGEX, email);
    }

    public void alert() {
        App.getApp().getRegistrationScene().errorAlert("Пожалуйста, проверьте правильность введённой почты",
                "Ваш формат почты неверен!");
    }
}
