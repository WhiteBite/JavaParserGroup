package ru.whitebite;


import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Group {
    Set<GroupKey> criterions1 = new HashSet<>();
    Set<GroupKey> criterions2 = new HashSet<>();
    Set<GroupKey> criterions3 = new HashSet<>();
    List<String> string = Collections.synchronizedList(new LinkedList<>());  /*new CopyOnWriteArrayList<>();*/


    void add(String str, ArrayList<GroupKey> critArray) {
        string.add(str);
        criterions1.add(critArray.get(0));
        criterions2.add(critArray.get(1));
        criterions3.add(critArray.get(2));
    }

}
