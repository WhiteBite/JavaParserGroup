package ru.whitebite;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Group {
    List<String> criterions1 = new CopyOnWriteArrayList<>();
    List<String> criterions2 =new CopyOnWriteArrayList<>();
    List<String> criterions3 = new CopyOnWriteArrayList<>();
    List<String> string = Collections.synchronizedList(new LinkedList<>());  /*new CopyOnWriteArrayList<>();*/

    void add(String str, ArrayList<String> critArray) {
        // todo check
        string.add(str);
        criterions1.add(critArray.get(0));
        criterions2.add(critArray.get(1));
        criterions3.add(critArray.get(2));
    }

}
