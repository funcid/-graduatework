package me.reidj.takiwadai.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    public static List<RoleType> getAchievementListFromString(String string) {
        List<RoleType> achievements = new ArrayList<>();
        Stream.of(string.split(", "))
                .map(RoleType::valueOf)
                .forEach(achievements::add);
        return achievements;
    }

    public static String getStringFromAchievementList(List<RoleType> list){
        StringBuilder stringBuilder = new StringBuilder();
        list.stream()
                .map(RoleType::name)
                .forEach(string -> stringBuilder.append(string).append(", "));
        return stringBuilder.toString();
    }
}
