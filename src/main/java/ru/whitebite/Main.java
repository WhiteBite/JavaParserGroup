package ru.whitebite;


import lombok.SneakyThrows;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;

public class Main {
    private static final ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    @SneakyThrows
    public static void main(String[] args) {
        Instant start = Instant.now();
        Instant end;
        double i = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("lng.csv"))) {
            String line = reader.readLine();
            while (line != null && i < Store.MAX_LINES) {
                es.execute(new LineProcessingTask(line, i));
                line = reader.readLine(); //For the next iteration
                i++;
            }
            end = Instant.now();
            System.out.println("End Reading:  " + Duration.between(start, end));
        }

        // Wait for all the tasks to be finished
        es.shutdown();
        if (!es.awaitTermination(5, TimeUnit.MINUTES)) {
            es.shutdownNow();
        }
        System.out.println("SetGroup size: " + Store.setGroups.size());
        givenWritingStringToFile_whenUsingFileOutputStream_thenCorrect();
        end = Instant.now();
        System.out.println("End Workd: " + Duration.between(start, end));

    }

    @SneakyThrows
    public static void givenWritingStringToFile_whenUsingFileOutputStream_thenCorrect() {
        String index;
        int bigGroup = 0;
        try (FileOutputStream outputStream = new FileOutputStream(Store.FILENAME)) {
            int i = 0;
            for (Group group : Store.setGroups) {
                int grSize = group.string.size();
                index = "Group " + i + " [" + grSize + "]\n";
                if (grSize > 1)
                    bigGroup++;
                outputStream.write(index.getBytes());
                for (String str : group.string) {
                    outputStream.write(str.getBytes());
                    outputStream.write('\n');
                }
                i++;
            }
        }
        System.out.println("bigGroup: " + bigGroup);
    }
}