package me.reidj.takiwadai.exception;

import me.reidj.takiwadai.App;
import me.reidj.takiwadai.util.StringUtil;

public class SymbolIsIncorrect implements Exception {

    @Override
    public boolean check(String[] strings) {
        return StringUtil.isRegularExpressionCheck(StringUtil.RUSSIAN_SYMBOL_REGEX, strings[0])
                || StringUtil.isRegularExpressionCheck(StringUtil.RUSSIAN_SYMBOL_REGEX, strings[1])
                || StringUtil.isRegularExpressionCheck(StringUtil.RUSSIAN_SYMBOL_REGEX, strings[2]);
    }

    @Override
    public void alert() {
        App.getApp().getRegistrationScene().errorAlert("Пожалуйста, проверьте правильность введённых данных",
                "Имя, фамилия и отчество могут содержать только русские символы!");
    }
}
