package org.wr.concurrent;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TaskQueue {

    public static enum Mode {
        SKIP, FREEZE
    }

    private Mode mode = Mode.FREEZE;
    private Map<String, TaskWithDependencies> polled = new ConcurrentHashMap<>();
    private Map<String, TaskWithDependencies> available = new ConcurrentHashMap<>();
    private Map<String, TaskWithDependencies> waiting = new ConcurrentHashMap<>();

    protected static Function<Map<String, TaskWithDependencies>, Function<String, TaskWithDependencies>>
            removeFromMap = map -> map::remove;
    protected static Function<Map<String, TaskWithDependencies>, Consumer<TaskWithDependencies>>
            putToMap = map -> t -> map.put(t.getName(), t);

    protected static Predicate<Set<TaskWithDependencies>> tasksCompleted =
            t -> !(t.stream().filter(pt -> !TaskWithDependencies.Status.COMPLETE.equals(pt.getStatus())).findAny().isPresent());

    protected static Predicate<TaskWithDependencies> availableTask = t -> t.getToWait().isEmpty() || tasksCompleted.test(t.getToWait());


    public TaskQueue(Map<String, TaskWithDependencies> tasks) {
        waiting.putAll(tasks);
        scanForAvailable();
    }

    private void scanForAvailable() {
        List<String> noParentKeys = getAvailableTasksKey();
        transferFromTo(noParentKeys, waiting, available);
    }

    public TaskWithDependencies poll() {
        scanForAvailable();
        if(available.keySet().isEmpty()) {
            return null;
        }
        String availableKey = available.keySet().iterator().next();
        TaskWithDependencies task = available.get(availableKey);
        transferFromTo(availableKey, available, polled);
        return task;
    }

    public void add(TaskWithDependencies taskWithDependencies) {
        waiting.put(taskWithDependencies.getName(), taskWithDependencies);
        scanForAvailable();
    }

    public List<TaskWithDependencies> getAll() {
        List<TaskWithDependencies> tasks = new LinkedList<>();
        tasks.addAll(available.values());
        tasks.addAll(waiting.values());
        tasks.addAll(polled.values());
        return tasks;
    }

    public List<TaskWithDependencies> getAvailable() {
        return new LinkedList<>(available.values());
    }

    public boolean isEmpty() {
        return waiting.isEmpty() && available.isEmpty();
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }


    private static void transferFromTo(List<String> keys,
                                       Map<String, TaskWithDependencies> out,
                                       Map<String, TaskWithDependencies> in) {
        keys
                .stream()
                .map(removeFromMap.apply(out))
                .forEach(putToMap.apply(in));
    }

    private static void transferFromTo(String key,
                                       Map<String, TaskWithDependencies> out,
                                       Map<String, TaskWithDependencies> in) {
        transferFromTo(Collections.singletonList(key), out, in);
    }

    private List<String> getAvailableTasksKey() {
        return waiting
                .values()
                .stream()
                .filter(availableTask)
                .map(TaskWithDependencies::getName)
                .collect(Collectors.toList());
    }



}

