package ru.whitebite;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class Store {
    public static Double MAX_LINES = 50000D;
    public static final String FILENAME = "text.txt";
    protected static final Map<GroupKey, Group> groups = new ConcurrentHashMap<>();
    protected static final Set<Group> setGroups = new CopyOnWriteArraySet<>();

    public static void printSet() {
        int i = 0;
        for (Group group : setGroups) {
            System.out.println("Group: " + i);
            for (GroupKey groupKey : group.criterions1) {
                System.out.println(groupKey.column + " " + groupKey.key);
            }
            for (GroupKey groupKey : group.criterions2) {
                System.out.println(groupKey.column + " " + groupKey.key);
            }
            for (GroupKey groupKey : group.criterions3) {
                System.out.println(groupKey.column + " " + groupKey.key);
            }
            System.out.println(group.string);
            i++;
        }
    }
    private Store(){}
}
