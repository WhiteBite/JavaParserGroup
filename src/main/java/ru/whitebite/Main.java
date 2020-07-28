package ru.whitebite;


import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class Main {
    static int lineC1 = 0;
    static ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    static List<Group> groups = new CopyOnWriteArrayList<>();
    static String fileName = "text.txt";

//    public static boolean verifyLine(String line) {
//        return line.matches("\"[0-9]*\";\"[0-9]*\";\"[0-9]*\"");
//    }
//
//    public static boolean critChecker(ArrayList<String> criterions, Group group) {
//        String crit = criterions.get(0);
//        if (!crit.equals("\"\"") && (group.criterions1.contains(crit)))
//            return true;
//        crit = criterions.get(1);
//        if (!crit.equals("\"\"") && (group.criterions2.contains(crit)))
//            return true;
//        crit = criterions.get(2);
//        if (!crit.equals("\"\"") && (group.criterions3.contains(crit))) {
//            System.out.printf(crit);
//            return true;
//        }
//        return false;
//    }
//
//    public static ArrayList<String> ParseLineToSet(String line) {
//        ArrayList<String> criterions = new ArrayList<>();
//        String[] subStr;
//        String delimeter = ";"; // Разделитель
//        subStr = line.split(delimeter); // Разделения строки str с помощью метода split()
//        // Вывод результата на экран
//        // for (int i = 0; i < subStr.length; i++) {
//        //    System.out.println(subStr[i]);
//        // }
//        criterions.addAll(Arrays.asList(subStr));
//        return criterions;
//    }
//
//    public static void groupAdder(String line) {
//        ArrayList<String> criterions = ParseLineToSet(line);
//        for (Group group : groups) {
//            if (critChecker(criterions, group)) {
////                System.out.println("add line: (" + lineC1 + ") "+ line);
//                lineC1++;
//                group.add(line, criterions);
//                return;
//            }
//        }
//
//
//        Group newGroup = new Group();
//        newGroup.add(line, criterions);
//        groups.add(newGroup);
//    }
//    private void invoke(String line ) {
//        CompletableFuture.runAsync(() -> {
//            try {
//                System.out.println("V1: (" + line + ") ");
//                if (verifyLine(line)) {
//                    groupAdder(line);
//                } else {
//                    System.out.println("Wrong line: " + line);
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        },es);
//    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Instant start = Instant.now();
        // Submit each line as a processing task
        int i = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("lng.csv"))) {
            String line = reader.readLine();
            while (line != null && i < 150000) {
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
        for (Group group : new CopyOnWriteArrayList<>(groups)) {
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
