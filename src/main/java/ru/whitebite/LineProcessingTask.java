package ru.whitebite;

import java.util.ArrayList;
import java.util.Arrays;

public class LineProcessingTask implements Runnable {

    public static boolean verifyLine(String line) {
        return line.matches("\"[0-9]*\";\"[0-9]*\";\"[0-9]*\"");
    }



    public static void groupAdder(String line) {
        Store.add(line);
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