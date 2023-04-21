package me.reidj.takiwadai.user;

public record User(int id, String name, String surname, String secondName, String email, String password, RoleType roleType) {
}
