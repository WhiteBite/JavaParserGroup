package ru.whitebite;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Group implements Comparable {
    public final Set<GroupKey> criterions1 = ConcurrentHashMap.newKeySet();
    public final Set<GroupKey> criterions2 = ConcurrentHashMap.newKeySet();
    public final Set<GroupKey> criterions3 = ConcurrentHashMap.newKeySet();
    public final List<String> strings = new CopyOnWriteArrayList<>();


    void add(String str, List<GroupKey> critArray) {
        strings.add(str);
        criterions1.add(critArray.get(0));
        criterions2.add(critArray.get(1));
        criterions3.add(critArray.get(2));
        if (strings.size() == 2) {
            Store.BIGGROUP.incrementAndGet();
        }
    }

    @Override
    public int compareTo(Object o) {
        return ((Group) o).strings.size() - this.strings.size();
    }
}
