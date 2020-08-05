package ru.whitebite;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class Store {
    public static volatile boolean DEBUG = false;
    public static long MAX_LINES;
    public static volatile AtomicLong BIGGROUP = new AtomicLong(0);
    public static final String InFILENAME = "lng.csv";
    public static final String OutFILENAME = "text.txt";
    protected static volatile AtomicLong INVALID_LINES = new AtomicLong(0);
    protected static final Map<GroupKey, Group> groups = new ConcurrentHashMap<>();
    protected static final Set<Group> setGroups = ConcurrentHashMap.newKeySet();
    public static long timeWork = 20L;
    public static Instant start = Instant.now();


    private Store() {
    }
}
