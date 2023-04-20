package me.reidj.takiwadai.exception;

import me.reidj.takiwadai.App;
import me.reidj.takiwadai.util.StringUtil;

import java.util.Arrays;

public class SymbolIsIncorrect {

    public boolean check(String... strings) {
        return Arrays.stream(strings).anyMatch(s -> StringUtil.isRegularExpressionCheck(StringUtil.RUSSIAN_SYMBOL_REGEX, s));
    }

    public void alert() {
        App.getApp().getRegistrationScene().errorAlert("Пожалуйста, проверьте правильность введённых данных",
                "Имя, фамилия и отчество могут содержать только русские символы!");
    }
}
