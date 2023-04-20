package me.reidj.takiwadai.exception;

import me.reidj.takiwadai.App;
import me.reidj.takiwadai.util.StringUtil;

public class EmailIsIncorrect implements Exception {

    @Override
    public boolean check(String[] strings) {
        return StringUtil.isRegularExpressionCheck(StringUtil.EMAIL_REGEX, strings[3]);
    }

    @Override
    public void alert() {
        App.getApp().getRegistrationScene().errorAlert("Пожалуйста, проверьте правильность введённой почты",
                "Ваш формат почты неверен!");
    }
}
