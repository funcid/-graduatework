package me.reidj.takiwadai.user;

public record User(String uuid, String name, String surname, String secondName, String email, String password, RoleType roleType) {
}
