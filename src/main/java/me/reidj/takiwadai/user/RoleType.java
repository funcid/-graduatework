package me.reidj.takiwadai.user;

public enum RoleType {
    USER(1),
    ADMIN(2)
    ;

    private final int priority;

    RoleType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
