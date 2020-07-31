package ru.whitebite;


import java.util.*;

public class Group {
    public final Set<GroupKey> criterions1 = new HashSet<>();
    public final Set<GroupKey> criterions2 = new HashSet<>();
    public final Set<GroupKey> criterions3 = new HashSet<>();
    public final List<String> string = Collections.synchronizedList(new LinkedList<>());


    void add(String str, List<GroupKey> critArray) {
        string.add(str);
        criterions1.add(critArray.get(0));
        criterions2.add(critArray.get(1));
        criterions3.add(critArray.get(2));
    }

}
