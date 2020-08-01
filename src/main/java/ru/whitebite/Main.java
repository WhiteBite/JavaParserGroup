package ru.whitebite;


import lombok.SneakyThrows;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class Main {
    private static final ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void setStringSize() throws IOException {
        Path path = Paths.get(Store.InFILENAME);
        try (Stream<String> stream = Files.lines(path)) {
            Store.MAX_LINES = stream.count();
        }

      //  Store.MAX_LINES = 400000;
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
            System.out.println("End Reading:  " + Duration.between(Store.start, end));
        }

        // Wait for all the tasks to be finished
        es.shutdown();
        if (!es.awaitTermination(Store.timeWork, TimeUnit.MINUTES)) {
            System.out.println("Time is up");
            es.shutdownNow();
        }
        System.out.println("SetGroup size: " + Store.setGroups.size());
        writeToFile();
        end = Instant.now();
        System.out.println("End Work: " + Duration.between(Store.start, end));
    }

    @SneakyThrows
    public static void main(String[] args) {

        work();
    }

    @SneakyThrows
    public static void writeToFile() {
        String index;
        int bigGroup = 0;
        try (FileOutputStream outputStream = new FileOutputStream(Store.OutFILENAME)) {
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
        System.out.println("InvalidLines: " + Store.INVALID_LINES);
    }
}