package ru.whitebite;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static ru.whitebite.Store.MAX_LINES;

public class LineProcessingTask implements Runnable {

    private final String s;
    private final Double iteration;

    public static boolean verifyLine(String line) {
        return line.matches("\"[0-9]*\";\"[0-9]*\";\"[0-9]*\"");
    }

    public static List<GroupKey> parseLineToSet(String line) {
        ArrayList<GroupKey> criterions = new ArrayList<>();
        String[] subStr = line.split(";");
        for (int i = 0; i < 3; i++) {
            criterions.add(new GroupKey(i, subStr[i]));
        }
        return criterions;
    }

    public static void groupAdder(String line) {
        List<GroupKey> criterions = LineProcessingTask.parseLineToSet(line);
        Group nGroup;
//        Group newGroup = new Group();
//        newGroup.add(line, criterions);
//        Store.groups.put(criterions.get(0), newGroup);
//        Store.groups.put(criterions.get(1), newGroup);
//        Store.groups.put(criterions.get(2), newGroup);
//        Store.setGroups.add(newGroup);
        if (!criterions.get(0).key.equals("\"\"") && Store.groups.get(criterions.get(0)) != null) {
            nGroup = Store.groups.get(criterions.get(0));
            Store.groups.get(criterions.get(0)).add(line, criterions);
            Store.groups.put(criterions.get(1), nGroup);
            Store.groups.put(criterions.get(2), nGroup);
        } else {
            if (!criterions.get(1).key.equals("\"\"") && Store.groups.get(criterions.get(1)) != null) {
                nGroup = Store.groups.get(criterions.get(1));
                Store.groups.put(criterions.get(0), nGroup);
                Store.groups.get(criterions.get(1)).add(line, criterions);
                Store.groups.put(criterions.get(2), nGroup);
            } else {
                if (!criterions.get(2).key.equals("\"\"") && Store.groups.get(criterions.get(2)) != null) {
                    nGroup = Store.groups.get(criterions.get(2));
                    Store.groups.put(criterions.get(0), nGroup);
                    Store.groups.put(criterions.get(1), nGroup);
                    Store.groups.get(criterions.get(2)).add(line, criterions);
                } else {
                    Group newGroup = new Group();
                    newGroup.add(line, criterions);
                    Store.groups.put(criterions.get(0), newGroup);
                    Store.groups.put(criterions.get(1), newGroup);
                    Store.groups.put(criterions.get(2), newGroup);
                    Store.setGroups.add(newGroup);
                }
            }
        }
    }

    public LineProcessingTask(String line, double iteration) {
        this.s = line;
        this.iteration = iteration;
    }

    @Override
    public void run() {
        if (verifyLine(s)) {
            groupAdder(s);
            if ((iteration / MAX_LINES * 100) % 10 == 0) {
                System.out.println("Completed on " + (int) (iteration / MAX_LINES * 100) + "%  [t: " + Instant.now() + "]");
            }
        } else {
            System.out.println("Invalid line: " + s);
            Store.INVALID_LINES.incrementAndGet();
        }
    }
}