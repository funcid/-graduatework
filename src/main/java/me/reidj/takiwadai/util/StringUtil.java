package me.reidj.takiwadai.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isOnlyRussianSymbols(String input) {
        String regex = "^[а-яА-ЯёЁ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return !matcher.matches();
    }
}
