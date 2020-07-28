package ru.whitebite;

import java.util.ArrayList;
import java.util.Arrays;

public class LineProcessingTask implements Runnable {

    public static boolean verifyLine(String line) {
        return line.matches("\"[0-9]*\";\"[0-9]*\";\"[0-9]*\"");
    }

    public static boolean critChecker(ArrayList<String> criterions, Group group) {
        String crit = criterions.get(0);
        if (!crit.equals("\"\"") && (group.criterions1.contains(crit)))
            return true;
        crit = criterions.get(1);
        if (!crit.equals("\"\"") && (group.criterions2.contains(crit)))
            return true;
        crit = criterions.get(2);
        if (!crit.equals("\"\"") && (group.criterions3.contains(crit))) {
            System.out.printf(crit);
            return true;
        }
        return false;
    }

    public static ArrayList<String> ParseLineToSet(String line) {
        ArrayList<String> criterions = new ArrayList<>();
        String[] subStr;
        String delimeter = ";"; // Разделитель
        subStr = line.split(delimeter); // Разделения строки str с помощью метода split()
        // Вывод результата на экран
        // for (int i = 0; i < subStr.length; i++) {
        //    System.out.println(subStr[i]);
        // }
        criterions.addAll(Arrays.asList(subStr));
        return criterions;
    }

    public static void groupAdder(String line) {
        ArrayList<String> criterions = ParseLineToSet(line);
        for (Group group : Main.groups) {
            if (critChecker(criterions, group)) {
//                System.out.println("add line: (" + lineC1 + ") "+ line);
                //       lineC1++;
                group.add(line, criterions);
                return;
            }
        }


        Group newGroup = new Group();
        newGroup.add(line, criterions);
        Main.groups.add(newGroup);
    }

    String s;

    public LineProcessingTask(String line) {
        s = line;
    }

    @Override
    public void run() {
        if (verifyLine(s)) {
            groupAdder(s);
        }
    }
}