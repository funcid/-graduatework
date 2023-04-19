package me.reidj.takiwadai.exception;

import me.reidj.takiwadai.util.StringUtil;

import java.util.function.Predicate;

public enum ExceptionType {
    FIELD_IS_EMPTY(strings -> strings[0].isEmpty(), "Пожалуйста, заполните все поля", "Поля не могут быть пустыми!"),
    SYMBOL_IS_INCORRECT(strings -> StringUtil.isRegularExpressionCheck(StringUtil.RUSSIAN_SYMBOL_REGEX, strings[0]), "Пожалуйста, проверьте правильность введённых данных",
            "Имя, фамилия и отчество могут содержать только русские символы!"),
    EMAIL_IS_INCORRECT(strings -> StringUtil.isRegularExpressionCheck(StringUtil.EMAIL_REGEX, strings[0]), "Пожалуйста, проверьте правильность введённой почты",
            "Ваш формат почты неверен!"),
    PASSWORD_IS_NOT_EQUAL(strings -> !strings[0].equals(strings[1]), "Пожалуйста, проверьте правильность введённого пароля!",
            "Ваши пароли не совпадают!"),
    PASSWORD_SHORT(strings -> strings[0].length() <= 6, "Пожалуйста, проверьте правильность введённого пароля!",
            "Ваш пароль должен быть длиннее 6 символов!"),
    ;

    private final Predicate<String[]> predicate;
    private final String contextText;
    private final String headerText;

    ExceptionType(Predicate<String[]> predicate, String contextText, String headerText) {
        this.predicate = predicate;
        this.contextText = contextText;
        this.headerText = headerText;
    }

    public Predicate<String[]> getPredicate() {
        return predicate;
    }

    public String getContextText() {
        return contextText;
    }

    public String getHeaderText() {
        return headerText;
    }
}
