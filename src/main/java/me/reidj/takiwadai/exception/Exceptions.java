package me.reidj.takiwadai.exception;

public class Exceptions {

    public static EmailIsIncorrect emailIsIncorrect;
    public static FieldIsEmpty fieldIsEmpty;
    public static PasswordIsNotEqual passwordIsNotEqual;
    public static PasswordShort passwordShort;
    public static SymbolIsIncorrect symbolIsIncorrect;

    public void init() {
        emailIsIncorrect = new EmailIsIncorrect();
        fieldIsEmpty = new FieldIsEmpty();
        passwordIsNotEqual = new PasswordIsNotEqual();
        passwordShort = new PasswordShort();
        symbolIsIncorrect = new SymbolIsIncorrect();
    }
}
