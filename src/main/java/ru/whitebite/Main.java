package ru.whitebite;


import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class Main {
    static ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());



    static String fileName = "text.txt";


    public static void main(String[] args) throws IOException, InterruptedException {
        Instant start = Instant.now();
        // Submit each line as a processing task
        int i = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("lng.csv"))) {
            String line = reader.readLine();
            while (line != null && i < 50000) {
                System.out.println(i + ") " + line);
                es.execute(new LineProcessingTask(line));
                line = reader.readLine(); //For the next iteration
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Wait for all the tasks to be finished
        try {
            es.awaitTermination(30l, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.println("All tasks did not complete in the allocated time");
            return;
        }

//        es.shutdown();
//        es.awaitTermination(20L, TimeUnit.MINUTES);
        givenWritingStringToFile_whenUsingFileOutputStream_thenCorrect();
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end));
    }

    public static void givenWritingStringToFile_whenUsingFileOutputStream_thenCorrect()
            throws IOException {
        FileOutputStream outputStream = new FileOutputStream(fileName);
        // byte[] strToBytes = str.getBytes();
        int i = 0;
        String index;
        for (Group group : Store.setGroups) {
            index = "Group " + i + " [" + group.string.size() + "]\n";
            outputStream.write(index.getBytes());
            for (String str : group.string) {
                //   str += "\n";
                outputStream.write(str.getBytes());
                outputStream.write('\n');
            }
            i++;
        }

        outputStream.close();
    }

 /*   @Test
    public void givenWritingToFile_whenUsingDataOutputStream_thenCorrect()
            throws IOException {
        String value = "Hello";
        FileOutputStream fos = new FileOutputStream(fileName);
        DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
        outStream.writeUTF(value);
        outStream.close();

        // verify the results
        String result;
        FileInputStream fis = new FileInputStream(fileName);
        DataInputStream reader = new DataInputStream(fis);
        result = reader.readUTF();
        reader.close();

        assertEquals(value, result);
    }*/
}
