package ru.whitebite;


import lombok.SneakyThrows;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class Main {
    private static final ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void setStringSize() throws IOException {
        Path path = Paths.get(Store.InFILENAME);
        try (Stream<String> stream = Files.lines(path)) {
            Store.MAX_LINES = stream.count();
        }
    }

    @SneakyThrows
    public static void work() {
        Instant end;
        double i = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(Store.InFILENAME))) {
            String line = reader.readLine();
            setStringSize();
            while (line != null && i < Store.MAX_LINES) {
                es.execute(new LineProcessingTask(line, i));
                line = reader.readLine(); //For the next iteration
                i++;
            }
            end = Instant.now();

            if (Store.DEBUG)
                System.out.println("End Reading:  " + Duration.between(Store.start, end));
        }

        es.shutdown();
        if (!es.awaitTermination(Store.timeWork, TimeUnit.MINUTES)) {
            if (Store.DEBUG)
                System.out.println("Time is up");
            es.shutdownNow();
        }
        writeToFile();
        System.out.println("Total groups: " + Store.setGroups.size());
        System.out.println("Group With More Than 1 Element: " + Store.BIGGROUP);
        System.out.println("InvalidLines: " + Store.INVALID_LINES);
        System.out.println("Running time : " + Duration.between(Store.start, Instant.now()));
    }

    @SneakyThrows
    public static void main(String[] args) {
        work();
    }

    @SneakyThrows
    public static void writeToFile() {
        List<Group> sortedList = new ArrayList<>(Store.setGroups);
        Collections.sort(sortedList);

        if (Store.DEBUG)
            System.out.println("Start writing: " + Duration.between(Store.start, Instant.now()));
        String grNum;
        try (FileOutputStream outputStream = new FileOutputStream(Store.OutFILENAME)) {
            int i = 0;
            String countBigGroup = Store.BIGGROUP + " - Groups With More Than 1 Element \n";
            outputStream.write(countBigGroup.getBytes());
            for (Group group : sortedList) {
                int grSize = group.strings.size();
                grNum = "Group " + i + " [" + grSize + "]\n";
                outputStream.write(grNum.getBytes());
                for (String str : group.strings) {
                    outputStream.write(str.getBytes());
                    outputStream.write('\n');
                }
                i++;
            }
        }
    }
}