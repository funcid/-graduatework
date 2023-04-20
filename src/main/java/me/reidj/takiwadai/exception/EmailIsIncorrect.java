package me.reidj.takiwadai.exception;

import me.reidj.takiwadai.App;
import me.reidj.takiwadai.util.StringUtil;

public class EmailIsIncorrect {

    public boolean check(String email) {
        return StringUtil.isRegularExpressionCheck(StringUtil.EMAIL_REGEX, email);
    }

    public void alert() {
        App.getApp().getRegistrationScene().errorAlert("Пожалуйста, проверьте правильность введённой почты",
                "Ваш формат почты неверен!");
    }
}
