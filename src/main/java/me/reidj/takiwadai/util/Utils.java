package me.reidj.takiwadai.util;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String RUSSIAN_SYMBOL_REGEX = "^[а-яА-ЯёЁ]+$";

    public static final String RESOURCES = "src/main/resources/";

    public static boolean isRegularExpressionCheck(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return !matcher.matches();
    }

    public static boolean isNumber(String numbers) {
        return numbers.chars().allMatch(Character::isDigit);
    }

    public static void childrenRemove(ObservableList<Node> children) {
        children.removeAll();
    }

    public static Properties loadPropertyFile(String path) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
