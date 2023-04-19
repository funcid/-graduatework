package me.reidj.takiwadai.user;

public class User {
    private String uuid;
    private String name;
    private String surname;
    private String secondName;
    private String email;
    private String password;
    private RoleType roleType;

    public User(String uuid, String name, String surname, String secondName, String email, String password, RoleType roleType) {
        this.uuid = uuid;
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
        this.roleType = roleType;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
}
