package ru.whitebite;


import java.util.*;

public class Store {

    static Map<GroupKey, Group> groups = new HashMap<>();
    static Set<Group> setGroups = new HashSet<>();

    public static ArrayList<GroupKey> ParseLineToSet(String line) {
        ArrayList<GroupKey> criterions = new ArrayList<>();
        String[] subStr;
        String delimeter = ";"; // Разделитель
        subStr = line.split(delimeter); // Разделения строки str с помощью метода split()

        //TODO make stream
        for (int i = 0; i < 3; i++) {
            criterions.add(new GroupKey(i, subStr[i]));
        }
//        criterions.stream().filter(it -> critChecker(criterions, it)).forEach(it -> it.add(line,criterions));
        //      criterions.addAll(Arrays.asList(subStr));
        return criterions;
    }



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

    public static void add(String line) {
        ArrayList<GroupKey> criterions = ParseLineToSet(line);
        Group nGroup;
        if (groups.get(criterions.get(0)) != null) {
            nGroup = groups.get(criterions.get(0));
            groups.get(criterions.get(0)).add(line, criterions);
            groups.put(criterions.get(1), nGroup);
            groups.put(criterions.get(2), nGroup);
        } else {
            nGroup = groups.get(criterions.get(1));
            if (groups.get(criterions.get(1)) != null) {
                nGroup = groups.get(criterions.get(1));
                groups.put(criterions.get(0), nGroup);
                groups.put(criterions.get(2), nGroup);
                groups.get(criterions.get(1)).add(line, criterions);
            } else {
                if (groups.get(criterions.get(2)) != null) {
                    nGroup = groups.get(criterions.get(2));
                    groups.put(criterions.get(0), nGroup);
                    groups.put(criterions.get(1), nGroup);
                    groups.get(criterions.get(2)).add(line, criterions);
                } else {
                    Group newGroup = new Group();
                    newGroup.add(line, criterions);
                    groups.put(criterions.get(0), newGroup);
                    groups.put(criterions.get(1), newGroup);
                    groups.put(criterions.get(2), newGroup);
                    setGroups.add(newGroup);
                }
            }
        }
    }


    public static void main(String[] args) {
        String line = "\"A1\";\"B1\";\"C1\"";
        String line2 = "\"A2\";\"B2\";\"C1\"";
        String line3 = "\"A3\";\"B2\";\"C3\"";
        String line4 = "\"A4\";\"B4\";\"C4\"";

        add(line);
        add(line2);

        //printSet();
        ArrayList<GroupKey> criterions = ParseLineToSet(line3);
        GroupKey GK = criterions.get(1);
        Group nGroup = groups.get(GK);
        add(line3);
        add(line4);
        printSet();
    }


}
