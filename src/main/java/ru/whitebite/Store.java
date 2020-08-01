package ru.whitebite;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Stream;

public class Store {
    public static long MAX_LINES;
    public static final String InFILENAME = "lng.csv";
    public static final String OutFILENAME = "text.txt";
    protected static final Map<GroupKey, Group> groups = new ConcurrentHashMap<>();
    protected static final Set<Group> setGroups = ConcurrentHashMap.newKeySet();
    public static long timeWork = 20L;
    public static long INVALID_LINES;
    public static Instant start = Instant.now();

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

    private Store() {
    }
}
